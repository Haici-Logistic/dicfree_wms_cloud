/**
 * Copyright 2024 Wuhan Haici Technology Co., Ltd 
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dicfree.ms.wms.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSkuDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductWaveTaskDto;
import com.dicfree.ms.wms.common.enums.CollectionAreaStatus;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import com.dicfree.ms.wms.common.enums.OrderType;
import com.dicfree.ms.wms.common.enums.WaveTaskType;
import com.dicfree.ms.wms.repository.jpa.dao.CollectionAreaDao;
import com.dicfree.ms.wms.repository.jpa.dao.ProductDeliveryOrderDao;
import com.dicfree.ms.wms.repository.jpa.dao.ProductDeliveryOrderItemDao;
import com.dicfree.ms.wms.repository.jpa.dao.ProductWaveTaskDao;
import com.dicfree.ms.wms.repository.jpa.entity.CollectionArea;
import com.dicfree.ms.wms.repository.jpa.entity.ProductDeliveryOrder;
import com.dicfree.ms.wms.repository.jpa.entity.ProductDeliveryOrderItem;
import com.dicfree.ms.wms.repository.jpa.entity.ProductWaveTask;
import com.dicfree.ms.wms.service.ProductSkuService;
import com.dicfree.ms.wms.service.ProductSnService;
import com.dicfree.ms.wms.service.ProductWaveTaskService;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wiiyaya
 * @date 2023/11/06
 */
@Service("productWaveTaskService")
@Slf4j
public class ProductWaveTaskServiceImpl implements ProductWaveTaskService {

    @Resource
    private ProductWaveTaskDao productWaveTaskDao;

    @Resource
    private CollectionAreaDao collectionAreaDao;

    @Resource
    private ProductDeliveryOrderDao productDeliveryOrderDao;

    @Resource
    private ProductDeliveryOrderItemDao productDeliveryOrderItemDao;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductSnService productSnService;

    @Resource
    private QingFlowClient qingFlowClient;

    @Resource
    private DateTimeProvider dateTimeProvider;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void waveTaskGenerate(ExProductWaveTaskDto productWaveTaskDto) throws BusinessException {
        //更新订单快递信息及状态
        List<ProductDeliveryOrder> productDeliveryOrderList = new ArrayList<>();
        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));
        for (ExProductDeliveryOrderDto productDeliveryOrderDto : productWaveTaskDto.getProductDeliveryOrderList()) {
            if (productDeliveryOrderDao.isWaybillExists(productDeliveryOrderDto.getWaybill())) {
                throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_WAYBILL_EXISTS);
            }

            ProductDeliveryOrder productDeliveryOrder = productDeliveryOrderDao.findByThirdOrderNo(productDeliveryOrderDto.getThirdOrderNo()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_NOT_FOUND));
            if (productDeliveryOrder.getDispatched()) {
                throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_ALREADY_DISPATCH);
            }
            productDeliveryOrder.setDispatched(Boolean.TRUE);
            productDeliveryOrder.setDispatchTime(currTime);
            productDeliveryOrder.setCourier(productDeliveryOrderDto.getCourier());
            productDeliveryOrder.setWaybill(productDeliveryOrderDto.getWaybill());
            productDeliveryOrder.setWaybillUrl(productDeliveryOrderDto.getWaybillUrl());
            productDeliveryOrderDao.save(productDeliveryOrder);
            productDeliveryOrderList.add(productDeliveryOrder);
        }

        //创建波次任务
        List<CollectionArea> collectionAreaList = collectionAreaDao.findEmptyArea();
        if (collectionAreaList.isEmpty()) {
            throw new BusinessException(WmsMessageConstant.ERROR_COLLECTION_AREA_NO_EMPTY);
        }
        //获取第一个空闲的集货区
        CollectionArea collectionArea = collectionAreaList.get(0);
        //创建波次任务
        ProductWaveTask productWaveTask = new ProductWaveTask();
        productWaveTask.setType(productWaveTaskDto.getType());
        productWaveTask.setUniqueNo(productWaveTaskDto.getUniqueNo());
        productWaveTask.setCollectionAreaCode(collectionArea.getCode());
        productWaveTask.setOffShelfStatus(OrderStatus.PENDING);
        productWaveTask.setOffShelfCount(0);
        productWaveTask.setCollection(Boolean.FALSE);
        productWaveTask.setBasketStatus(OrderStatus.PENDING);
        productWaveTask.setBasketCount(0);
        productWaveTask.setSortingStatus(OrderStatus.PENDING);
        productWaveTask.setSortingCount(0);
        productWaveTask.setTotalOdCount(productDeliveryOrderList.size());
        productWaveTask.setTotalSnCount(productDeliveryOrderList.stream().mapToInt(ProductDeliveryOrder::getTotalCount).sum());
        productWaveTaskDao.save(productWaveTask);

        //更新订单波次任务id
        productDeliveryOrderList.forEach(productDeliveryOrder -> {
            productDeliveryOrder.setWaveTaskId(productWaveTask.getId());
            productDeliveryOrderDao.save(productDeliveryOrder);

            //更新订单详情波次任务id
            productDeliveryOrderItemDao.findAllByProductDeliveryOrderId(productDeliveryOrder.getId()).forEach(productDeliveryOrderItem -> {
                productDeliveryOrderItem.setWaveTaskId(productWaveTask.getId());
                productDeliveryOrderItemDao.save(productDeliveryOrderItem);
            });
        });

        //更新集货区状态
        collectionArea.setWaveTaskUniqueNo(productWaveTask.getUniqueNo());
        collectionArea.setStatus(CollectionAreaStatus.FULL);
        collectionAreaDao.save(collectionArea);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExProductWaveTaskDto> productWaveTaskOffShelfUndoList() {
        //查找未完成下架的波次任务
        List<ProductWaveTask> productWaveTaskList = productWaveTaskDao.findAllUnOffShelf();
        return productWaveTaskList.stream().map(this::toExProductWaveTaskOffShelfDto).collect(Collectors.toList()); //这里需要返回可编辑的列表
    }

    @Override
    @Transactional(readOnly = true)
    public ExProductWaveTaskDto productWaveTaskOffShelfDetail(Long productWaveTaskId) throws BusinessException {
        ProductWaveTask productWaveTask = productWaveTaskDao.findById(productWaveTaskId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_NOT_FOUND));
        return toExProductWaveTaskOffShelfDto(productWaveTask);
    }

    @Override
    public void productWaveTaskSnOffShelf(Long productWaveTaskId, String shelfNo, String productCode, String shelfAreaCode) throws BusinessException {
        //检查pda的区域是否匹配
        String destShelfAreaCode = StringUtilPlus.splitGetFirst(shelfNo, StringUtilPlus.HYPHEN);
        if (!StringUtilPlus.contains(shelfAreaCode, destShelfAreaCode)) {
            throw new BusinessException(WmsMessageConstant.ERROR_DEVICE_PDA_SHELF_AREA_NOT_MATCH);
        }
        //检查波次任务是否存在
        ProductWaveTask productWaveTask = productWaveTaskDao.findById(productWaveTaskId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_NOT_FOUND));

        //检查订单详情是否存在
        List<ProductDeliveryOrderItem> productDeliveryOrderItemList = productDeliveryOrderItemDao.findAllOffShelfUndoByWaveTaskId(productWaveTask.getId());
        ProductDeliveryOrderItem productDeliveryOrderItem = productDeliveryOrderItemList
                .stream()
                .filter(temp -> productSkuService.getProductSkuProductCode(temp.getProductSkuCode()).equals(productCode))
                .findFirst()
                .orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_ITEM_NOT_FOUND));

        ProductDeliveryOrder productDeliveryOrder = productDeliveryOrderDao.findById(productDeliveryOrderItem.getProductDeliveryOrderId()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_NOT_FOUND));

        //下架商品
        String snCode = productSnService.productSnOffShelf(productDeliveryOrderItem.getId(), shelfNo, productDeliveryOrderItem.getProductSkuCode());

        //更新WaveTask
        this.updateWaveTaskOffShelfCount(productWaveTask);

        //更新productDeliveryOrder
        this.updateProductDeliveryOrderOffShelfCount(productDeliveryOrder);

        //更新productDeliveryOrderItem
        this.updateProductDeliveryOrderItemOffShelfCount(productDeliveryOrderItem);

        //通知轻流
        qingFlowClient.productSnOffShelfNotice(snCode);
    }

    @Override
    public List<ExProductWaveTaskDto> productWaveTaskCollectionUndoList() {
        List<ProductWaveTask> productWaveTaskList = productWaveTaskDao.findAllUnCollection();
        return productWaveTaskList.stream().map(productWaveTask -> {
            ExProductWaveTaskDto productWaveTaskDto = new ExProductWaveTaskDto();
            productWaveTaskDto.setId(productWaveTask.getId());
            productWaveTaskDto.setUniqueNo(productWaveTask.getUniqueNo());
            productWaveTaskDto.setCollectionAreaCode(productWaveTask.getCollectionAreaCode());
            return productWaveTaskDto;
        }).toList();
    }

    @Override
    public List<ExProductWaveTaskDto> productWaveTaskCollectionOffShelfUndoList() {
        List<ProductWaveTask> productWaveTaskList = productWaveTaskDao.findAllUnOffShelf();
        return productWaveTaskList.stream().map(productWaveTask -> {
            ExProductWaveTaskDto productWaveTaskDto = new ExProductWaveTaskDto();
            productWaveTaskDto.setId(productWaveTask.getId());
            productWaveTaskDto.setUniqueNo(productWaveTask.getUniqueNo());
            productWaveTaskDto.setCollectionAreaCode(productWaveTask.getCollectionAreaCode());
            return productWaveTaskDto;
        }).toList();
    }

    @Override
    public Integer productWaveTaskCollectionOffShelfUndoCount() {
        return productWaveTaskDao.countUnOffShelf();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void productWaveTaskCollectionDone(String collectionAreaCodeOrUniqueNo) throws BusinessException {
        ProductWaveTask productWaveTask = productWaveTaskDao.findByCollectionAreaCodeOrUniqueNo(collectionAreaCodeOrUniqueNo).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_NOT_FOUND));
        if (productWaveTask.getOffShelfStatus() != OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_OFF_SHELF_NOT_DONE);
        }
        if (productWaveTask.getCollection()) {
            //throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_COLLECTION_ALREADY_DONE);
            log.warn("===productWaveTaskCollectionDone  product wave task[{}] collection already done==", productWaveTask.getUniqueNo());
            return;
        }

        productWaveTask.setCollection(Boolean.TRUE);
        //如果是Business单，则跳过后面的分篮和分拣状态
        if (productWaveTask.getType() == WaveTaskType.B) {
            //更新订单的分篮分拣状态
            List<ProductDeliveryOrder> productDeliveryOrderList = productDeliveryOrderDao.findAllByWaveTaskId(productWaveTask.getId());
            productDeliveryOrderList.forEach(productDeliveryOrder -> {
                productDeliveryOrder.setBasketNo(StringUtilPlus.HYPHEN);
                productDeliveryOrder.setSortingCount(productDeliveryOrder.getTotalCount());
                productDeliveryOrder.setSortingStatus(OrderStatus.DONE);
                productDeliveryOrderDao.save(productDeliveryOrder);
            });
            //更新订单详情的分篮分拣状态
            List<ProductDeliveryOrderItem> productDeliveryOrderItemList = productDeliveryOrderItemDao.findAllByWaveTaskId(productWaveTask.getId());
            productDeliveryOrderItemList.forEach(productDeliveryOrderItem -> {
                productDeliveryOrderItem.setSortingCount(productDeliveryOrderItem.getTotalCount());
                productDeliveryOrderItem.setSortingStatus(OrderStatus.DONE);
                productDeliveryOrderItemDao.save(productDeliveryOrderItem);
            });
            //更新SN的分拣状态
            productSnService.productSnSorting(productDeliveryOrderItemList.stream().map(ProductDeliveryOrderItem::getId).toList());

            //更新波次任务的分篮分拣状态
            productWaveTask.setBasketCount(productWaveTask.getTotalOdCount());
            productWaveTask.setBasketStatus(OrderStatus.DONE);
            productWaveTask.setSortingCount(productWaveTask.getTotalSnCount());
            productWaveTask.setSortingStatus(OrderStatus.DONE);
        }
        productWaveTaskDao.save(productWaveTask);

        //更新集货区状态
        CollectionArea collectionArea = collectionAreaDao.findByCode(productWaveTask.getCollectionAreaCode()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_AREA_NOT_FOUND));
        collectionArea.setStatus(CollectionAreaStatus.EMPTY);
        collectionArea.setWaveTaskUniqueNo(null);
        collectionAreaDao.save(collectionArea);
    }

    @Override
    public List<ExProductDeliveryOrderDto> productWaveTaskBasketInit(String productWaveTaskNo) throws BusinessException {
        ProductWaveTask productWaveTask = productWaveTaskDao.findByUniqueNo(productWaveTaskNo).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_NOT_FOUND));
        if (!productWaveTask.getCollection()) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_COLLECTION_NOT_DONE);
        }
        if (productWaveTask.getBasketStatus() == OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_BASKET_ALREADY_DONE);
        }
        List<ProductDeliveryOrder> productDeliveryOrderList = productDeliveryOrderDao.findAllByWaveTaskId(productWaveTask.getId());
        return productDeliveryOrderList.stream().map(productDeliveryOrder -> {
            ExProductDeliveryOrderDto productDeliveryOrderDto = new ExProductDeliveryOrderDto();
            productDeliveryOrderDto.setId(productDeliveryOrder.getId());
            productDeliveryOrderDto.setType(productDeliveryOrder.getType());
            productDeliveryOrderDto.setWaybill(productDeliveryOrder.getWaybill());
            productDeliveryOrderDto.setBasketNo(productDeliveryOrder.getBasketNo());
            return productDeliveryOrderDto;
        }).toList();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void productWaveTaskBasketBind(String waybill, String basketNo) throws BusinessException {
        ProductDeliveryOrder productDeliveryOrder = productDeliveryOrderDao.findByWaybill(waybill).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_NOT_FOUND));
        ProductWaveTask productWaveTask = productWaveTaskDao.findById(productDeliveryOrder.getWaveTaskId()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_NOT_FOUND));
        if (!productWaveTask.getCollection()) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_COLLECTION_NOT_DONE);
        }
        if (productWaveTask.getBasketStatus() == OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_BASKET_ALREADY_DONE);
        }
        if(StringUtilPlus.isNotBlank(productDeliveryOrder.getBasketNo())){
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_BASKET_ALREADY_BIND);
        }
        //查找WaveTask下全部的订单
        List<ProductDeliveryOrder> productDeliveryOrderList = productDeliveryOrderDao.findAllByWaveTaskId(productWaveTask.getId());
        //检查篮子是否已经被绑定
        long count = productDeliveryOrderList.stream().filter(temp -> StringUtilPlus.equals(basketNo, temp.getBasketNo())).count();
        if(count > 0){
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_BASKET_ALREADY_BIND);
        }

        productDeliveryOrder.setBasketNo(basketNo);
        productDeliveryOrderDao.save(productDeliveryOrder);

        productWaveTask.setBasketCount(productWaveTask.getBasketCount() + 1);
        if (productWaveTask.getBasketCount().equals(productWaveTask.getTotalOdCount())) {
            productWaveTask.setBasketStatus(OrderStatus.DONE);
        } else {
            productWaveTask.setBasketStatus(OrderStatus.IN_PROCESSING);
        }
        productWaveTaskDao.save(productWaveTask);
    }

    @Override
    public ExProductWaveTaskDto productWaveTaskInfoWithNull(String productWaveTaskNo) throws BusinessException {
        ProductWaveTask productWaveTask = productWaveTaskDao.findByUniqueNo(productWaveTaskNo).orElse(new ProductWaveTask());
        //如果没有找到，直接返回
        if (productWaveTask.getId() == null) {
            return new ExProductWaveTaskDto();
        }

        //如果找到了，检查状态，必须是分篮完成的
        if (productWaveTask.getBasketStatus() != OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_BASKET_NOT_DONE);
        }

        ExProductWaveTaskDto productWaveTaskDto = new ExProductWaveTaskDto();
        productWaveTaskDto.setId(productWaveTask.getId());
        productWaveTaskDto.setUniqueNo(productWaveTask.getUniqueNo());
        return productWaveTaskDto;
    }

    @Override
    public List<ExProductDeliveryOrderDto> productWaveTaskOrderUndoCalcList(Long productWaveTaskId) throws BusinessException {
        ProductWaveTask productWaveTask = productWaveTaskDao.findById(productWaveTaskId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_NOT_FOUND));
        if (productWaveTask.getBasketStatus() != OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_BASKET_NOT_DONE);
        }

        List<ProductDeliveryOrder> productDeliveryOrderList = productDeliveryOrderDao.findAllUndoByWaveTaskId(productWaveTask.getId());
        return productDeliveryOrderList.stream().map(productDeliveryOrder -> {
            ExProductDeliveryOrderDto productDeliveryOrderDto = new ExProductDeliveryOrderDto();
            productDeliveryOrderDto.setId(productDeliveryOrder.getId());
            productDeliveryOrderDto.setWaybill(productDeliveryOrder.getWaybill());
            productDeliveryOrderDto.setBasketNo(productDeliveryOrder.getBasketNo());
            productDeliveryOrderDto.setSortingCount(productDeliveryOrder.getSortingCount());
            productDeliveryOrderDto.setTotalCount(productDeliveryOrder.getTotalCount());
            return productDeliveryOrderDto;
        }).toList();
    }

    @Override
    public List<ExProductDeliveryOrderDto> productWaveTaskOrderDoneCalcList(Long productWaveTaskId) throws BusinessException {
        ProductWaveTask productWaveTask = productWaveTaskDao.findById(productWaveTaskId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_NOT_FOUND));
        if (productWaveTask.getBasketStatus() != OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_BASKET_NOT_DONE);
        }

        List<ProductDeliveryOrder> productDeliveryOrderList = productDeliveryOrderDao.findAllDoneByWaveTaskId(productWaveTask.getId());
        return productDeliveryOrderList.stream().map(productDeliveryOrder -> {
            ExProductDeliveryOrderDto productDeliveryOrderDto = new ExProductDeliveryOrderDto();
            productDeliveryOrderDto.setId(productDeliveryOrder.getId());
            productDeliveryOrderDto.setBasketNo(productDeliveryOrder.getBasketNo());
            productDeliveryOrderDto.setTotalCount(productDeliveryOrder.getTotalCount());
            return productDeliveryOrderDto;
        }).toList();
    }

    @Override
    public List<ExProductSnDto> productWaveTaskSnUndoList(Long productWaveTaskId, Long productDeliveryOrderId) throws BusinessException {
        List<Long> productDeliveryOrderItemIdList = productDeliveryOrderItemDao.findAllByProductDeliveryOrderId(productDeliveryOrderId)
                .stream()
                .map(ProductDeliveryOrderItem::getId)
                .toList();
        return productSnService.productSnUnSortingList(productDeliveryOrderItemIdList);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public String productWaveTaskSnSorting(Long productWaveTaskId, String productCode) throws BusinessException {
        ProductWaveTask productWaveTask = productWaveTaskDao.findById(productWaveTaskId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_NOT_FOUND));
        if (productWaveTask.getBasketStatus() != OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_BASKET_NOT_DONE);
        }
        if (productWaveTask.getSortingStatus() == OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_WAVE_TASK_SORTING_ALREADY_DONE);
        }
        //因为扫码的是productCode，无法确认客户，需要查找SKU
        ExProductSkuDto exProductSkuDto = productSkuService.productSkuInfo(productCode);
        //再查找到子订单
        List<Object> productDeliveryOrderItemList = productDeliveryOrderItemDao.findAllSortingUndoByWaveTaskIdAndProductSkuCode(productWaveTask.getId(), exProductSkuDto.getCode());
        if (productDeliveryOrderItemList.isEmpty()) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_ITEM_NOT_FOUND);
        }
        //按照OrderType分组
        Map<OrderType, List<Object[]>> orderTypeListMap = productDeliveryOrderItemList
                .stream()
                .map(o -> (Object[]) o)
                .collect(Collectors.groupingBy(objects -> {
                    ProductDeliveryOrder productDeliveryOrder = (ProductDeliveryOrder) objects[0];
                    return productDeliveryOrder.getType();
                }));

        //优先满足OO的订单
        List<Object[]> tempList = orderTypeListMap.get(OrderType.OO);
        if (CollectionUtilPlus.Collection.isNotEmpty(tempList)) {
            return sorting(tempList, productWaveTask);
        }
        tempList = orderTypeListMap.get(OrderType.OM);
        if (CollectionUtilPlus.Collection.isNotEmpty(tempList)) {
            return sorting(tempList, productWaveTask);
        }
        tempList = orderTypeListMap.get(OrderType.MM);
        return sorting(tempList, productWaveTask);
    }

    private String sorting(List<Object[]> tempList, ProductWaveTask productWaveTask) throws BusinessException {
        Object[] temp = tempList.get(0);
        ProductDeliveryOrder productDeliveryOrder = (ProductDeliveryOrder) temp[0];
        ProductDeliveryOrderItem productDeliveryOrderItem = (ProductDeliveryOrderItem) temp[1];

        //更新SN状态
        productSnService.productSnSorting(productDeliveryOrderItem.getId(), productDeliveryOrderItem.getProductSkuCode());

        //更新波次数量和状态
        this.updateWaveTaskOffSortingCount(productWaveTask);
        //更新订单数量和状态
        this.updateProductDeliveryOrderSortingCount(productDeliveryOrder);
        //更新订单详情数量和状态
        this.updateProductDeliveryOrderItemSortingCount(productDeliveryOrderItem);

        //返回订单篮子编号
        return productDeliveryOrder.getBasketNo();
    }

    private void updateWaveTaskOffSortingCount(ProductWaveTask productWaveTask) {
        productWaveTask.setSortingCount(productWaveTask.getSortingCount() + 1);
        if (productWaveTask.getSortingCount().equals(productWaveTask.getTotalSnCount())) {
            productWaveTask.setSortingStatus(OrderStatus.DONE);
        } else {
            productWaveTask.setSortingStatus(OrderStatus.IN_PROCESSING);
        }
        productWaveTaskDao.save(productWaveTask);
    }

    private void updateProductDeliveryOrderSortingCount(ProductDeliveryOrder productDeliveryOrder) {
        productDeliveryOrder.setSortingCount(productDeliveryOrder.getSortingCount() + 1);
        if (productDeliveryOrder.getSortingCount().equals(productDeliveryOrder.getTotalCount())) {
            productDeliveryOrder.setSortingStatus(OrderStatus.DONE);
        } else {
            productDeliveryOrder.setSortingStatus(OrderStatus.IN_PROCESSING);
        }
        productDeliveryOrderDao.save(productDeliveryOrder);
    }

    private void updateProductDeliveryOrderItemSortingCount(ProductDeliveryOrderItem productDeliveryOrderItem) {
        productDeliveryOrderItem.setSortingCount(productDeliveryOrderItem.getSortingCount() + 1);
        if (productDeliveryOrderItem.getSortingCount().equals(productDeliveryOrderItem.getTotalCount())) {
            productDeliveryOrderItem.setSortingStatus(OrderStatus.DONE);
        } else {
            productDeliveryOrderItem.setSortingStatus(OrderStatus.IN_PROCESSING);
        }
        productDeliveryOrderItemDao.save(productDeliveryOrderItem);
    }

    private void updateWaveTaskOffShelfCount(ProductWaveTask productWaveTask) {
        productWaveTask.setOffShelfCount(productWaveTask.getOffShelfCount() + 1);
        if (productWaveTask.getOffShelfCount().equals(productWaveTask.getTotalSnCount())) {
            productWaveTask.setOffShelfStatus(OrderStatus.DONE);
        } else {
            productWaveTask.setOffShelfStatus(OrderStatus.IN_PROCESSING);
        }
        productWaveTaskDao.save(productWaveTask);
    }

    private void updateProductDeliveryOrderOffShelfCount(ProductDeliveryOrder productDeliveryOrder) {
        productDeliveryOrder.setOffShelfCount(productDeliveryOrder.getOffShelfCount() + 1);
        if (productDeliveryOrder.getOffShelfCount().equals(productDeliveryOrder.getTotalCount())) {
            productDeliveryOrder.setOffShelfStatus(OrderStatus.DONE);
        } else {
            productDeliveryOrder.setOffShelfStatus(OrderStatus.IN_PROCESSING);
        }
        productDeliveryOrderDao.save(productDeliveryOrder);
    }

    private void updateProductDeliveryOrderItemOffShelfCount(ProductDeliveryOrderItem productDeliveryOrderItem) {
        productDeliveryOrderItem.setOffShelfCount(productDeliveryOrderItem.getOffShelfCount() + 1);
        if (productDeliveryOrderItem.getOffShelfCount().equals(productDeliveryOrderItem.getTotalCount())) {
            productDeliveryOrderItem.setOffShelfStatus(OrderStatus.DONE);
        } else {
            productDeliveryOrderItem.setOffShelfStatus(OrderStatus.IN_PROCESSING);
        }
        productDeliveryOrderItemDao.save(productDeliveryOrderItem);
    }

    private ExProductWaveTaskDto toExProductWaveTaskOffShelfDto(ProductWaveTask productWaveTask) {
        ExProductWaveTaskDto productWaveTaskDto = new ExProductWaveTaskDto();
        productWaveTaskDto.setId(productWaveTask.getId());
        productWaveTaskDto.setCollectionAreaCode(productWaveTask.getCollectionAreaCode());
        productWaveTaskDto.setUniqueNo(productWaveTask.getUniqueNo());
        productWaveTaskDto.setTotalSnCount(productWaveTask.getTotalSnCount());

        //查找这些波次下的订单
        List<ProductDeliveryOrder> productDeliveryOrderList = productDeliveryOrderDao.findAllOffShelfUndo(productWaveTask.getId());
        productWaveTaskDto.setProductDeliveryOrderList(productDeliveryOrderList.stream().map(productDeliveryOrder -> {
            ExProductDeliveryOrderDto productDeliveryOrderDto = new ExProductDeliveryOrderDto();
            productDeliveryOrderDto.setId(productDeliveryOrder.getId());
            productDeliveryOrderDto.setThirdOrderNo(productDeliveryOrder.getThirdOrderNo());
            productDeliveryOrderDto.setOffShelfCount(productDeliveryOrder.getOffShelfCount());
            productDeliveryOrderDto.setTotalCount(productDeliveryOrder.getTotalCount());

            //获取订单详情
            List<ProductDeliveryOrderItem> productDeliveryOrderItemList = productDeliveryOrderItemDao.findAllOffShelfUndoByProductDeliveryOrderId(productDeliveryOrder.getId());
            productDeliveryOrderDto.setProductDeliveryOrderItemList(productDeliveryOrderItemList.stream().map(productDeliveryOrderItem -> {
                ExProductDeliveryOrderItemDto productDeliveryOrderItemDto = new ExProductDeliveryOrderItemDto();
                productDeliveryOrderItemDto.setId(productDeliveryOrderItem.getId());
                productDeliveryOrderItemDto.setProductSkuCode(productDeliveryOrderItem.getProductSkuCode());
                productDeliveryOrderItemDto.setOffShelfCount(productDeliveryOrderItem.getOffShelfCount());
                productDeliveryOrderItemDto.setTotalCount(productDeliveryOrderItem.getTotalCount());

                //获取详情对应的SN
                List<ExProductSnDto> productSnUndoDtoList = productSnService.productSnOffShelfUndoList(productDeliveryOrderItem.getProductSkuCode(), productDeliveryOrderItem.getTotalCount() - productDeliveryOrderItem.getOffShelfCount());
                List<ExProductSnDto> productSnDoneDtoList = productSnService.productSnOffShelfDoneList(productDeliveryOrderItem.getId());

                //合并两个列表
                List<ExProductSnDto> allProductSnUndoDtoList = new ArrayList<>();
                allProductSnUndoDtoList.addAll(productSnUndoDtoList);
                allProductSnUndoDtoList.addAll(productSnDoneDtoList);
                productDeliveryOrderItemDto.setProductSnList(allProductSnUndoDtoList);
                return productDeliveryOrderItemDto;
            }).collect(Collectors.toList())); //这里需要返回可编辑的列表
            return productDeliveryOrderDto;
        }).collect(Collectors.toList())); //这里需要返回可编辑的列表
        return productWaveTaskDto;
    }
}
