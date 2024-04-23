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
import cn.jzyunqi.common.feature.redis.RedisHelper;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.pda.common.dto.ex.ExSnWeightDto;
import com.dicfree.ms.wms.common.constant.WmsCache;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExProductArrivalOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductArrivalOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSkuDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import com.dicfree.ms.wms.repository.jpa.dao.ProductArrivalOrderDao;
import com.dicfree.ms.wms.repository.jpa.dao.ProductArrivalOrderItemDao;
import com.dicfree.ms.wms.repository.jpa.entity.ProductArrivalOrder;
import com.dicfree.ms.wms.repository.jpa.entity.ProductArrivalOrderItem;
import com.dicfree.ms.wms.service.DevicePrinterService;
import com.dicfree.ms.wms.service.ProductArrivalOrderService;
import com.dicfree.ms.wms.service.ProductSkuService;
import com.dicfree.ms.wms.service.ProductSnService;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
@Slf4j
@Service("productArrivalOrderService")
public class ProductArrivalOrderServiceImpl implements ProductArrivalOrderService {

    @Resource
    private ProductArrivalOrderDao productArrivalOrderDao;

    @Resource
    private ProductArrivalOrderItemDao productArrivalOrderItemDao;

    @Resource
    private RedisHelper redisHelper;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductSnService productSnService;

    @Resource
    private DevicePrinterService devicePrinterService;

    @Resource
    private QingFlowClient qingFlowClient;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public ExProductArrivalOrderDto productArrivalOrderAdd(ExProductArrivalOrderDto productArrivalOrderDto) throws BusinessException {
        if (productArrivalOrderDao.isThirdOrderNoExists(productArrivalOrderDto.getThirdOrderNo())) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_THIRD_ORDER_NO_EXISTS, productArrivalOrderDto.getThirdOrderNo());
        }

        int totalCount = productArrivalOrderDto.getProductArrivalOrderItemList().stream().mapToInt(ExProductArrivalOrderItemDto::getTotalCount).sum();

        Long seq = redisHelper.vIncrement(WmsCache.WMS_AO_CONTAINER_NO_VIRTUAL_V, RedisHelper.COMMON_KEY, 1L);
        ProductArrivalOrder productArrivalOrder = new ProductArrivalOrder();
        productArrivalOrder.setSupplier(productArrivalOrderDto.getSupplier());
        productArrivalOrder.setThirdOrderNo(productArrivalOrderDto.getThirdOrderNo());
        productArrivalOrder.setContainerNoVirtual(StringUtilPlus.leftPad(seq, 5, '0'));
        productArrivalOrder.setContainerNoReal(productArrivalOrderDto.getContainerNoReal());
        productArrivalOrder.setInboundCount(0);
        productArrivalOrder.setOnShelfCount(0);
        productArrivalOrder.setTotalCount(totalCount);
        productArrivalOrder.setInboundStatus(OrderStatus.PENDING);
        productArrivalOrder.setOnShelfStatus(OrderStatus.PENDING);
        productArrivalOrderDao.save(productArrivalOrder);

        for (ExProductArrivalOrderItemDto productArrivalOrderItemDto : productArrivalOrderDto.getProductArrivalOrderItemList()) {
            String productSkuCode = productSkuService.getProductSkuCode(productArrivalOrderItemDto.getProductCode(), productArrivalOrderDto.getSupplier());

            //创建到货单详情
            ProductArrivalOrderItem productArrivalOrderItem = new ProductArrivalOrderItem();
            productArrivalOrderItem.setProductArrivalOrderId(productArrivalOrder.getId());
            productArrivalOrderItem.setProductSkuCode(productSkuCode);
            productArrivalOrderItem.setInboundCount(0);
            productArrivalOrderItem.setOnShelfCount(0);
            productArrivalOrderItem.setTotalCount(productArrivalOrderItemDto.getTotalCount());
            productArrivalOrderItem.setInboundStatus(OrderStatus.PENDING);
            productArrivalOrderItem.setOnShelfStatus(OrderStatus.PENDING);
            productArrivalOrderItemDao.save(productArrivalOrderItem);

            //创建SN详情
            List<ExProductSnDto> snDtoList = productSnService.productSnAdd(productArrivalOrderItem.getId(), productSkuCode, productArrivalOrderItemDto.getTotalCount());

            //返回到货单sn详情
            productArrivalOrderItemDto.setProductSnList(snDtoList);
        }

        //返回到货单id
        productArrivalOrderDto.setId(productArrivalOrder.getId());
        return productArrivalOrderDto;
    }

    @Override
    public void productAarrivalOrderStatusEdit(String thirdOrderNo, String productSkuCode, OrderStatus status) throws BusinessException {
        ProductArrivalOrder productArrivalOrder = productArrivalOrderDao.findByThirdOrderNo(thirdOrderNo).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_NOT_FOUND));

        //保存电商商品入库到货单详情状态
        List<ProductArrivalOrderItem> productArrivalOrderItemList = productArrivalOrderItemDao.findAllByArrivalOrderId(productArrivalOrder.getId());
        ProductArrivalOrderItem productArrivalOrderItem = productArrivalOrderItemList.stream().filter(item -> item.getProductSkuCode().equals(productSkuCode)).findFirst().orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_ITEM_NOT_FOUND));
        productArrivalOrderItem.setInboundStatus(status);
        productArrivalOrderItemDao.save(productArrivalOrderItem);

        //计算电商商品入库到货单状态
        Map<OrderStatus, IntSummaryStatistics> groupCount = productArrivalOrderItemList.stream().collect(Collectors.groupingBy(ProductArrivalOrderItem::getInboundStatus, Collectors.summarizingInt(value -> 1)));
        productArrivalOrder.setInboundStatus(OrderStatus.determineStatus(groupCount, productArrivalOrderItemList.size()));
        productArrivalOrderDao.save(productArrivalOrder);
    }

    @Override
    public List<ExProductArrivalOrderDto> productAarrivalOrderUndoList() {
        List<ProductArrivalOrder> productArrivalOrderList = productArrivalOrderDao.findAllInboundUndo();
        return productArrivalOrderList.stream().map(productArrivalOrder -> {
            ExProductArrivalOrderDto arrivalOrderDto = new ExProductArrivalOrderDto();
            arrivalOrderDto.setId(productArrivalOrder.getId());
            arrivalOrderDto.setThirdOrderNo(productArrivalOrder.getThirdOrderNo());
            return arrivalOrderDto;
        }).toList();
    }

    @Override
    public List<ExProductArrivalOrderItemDto> productAarrivalOrderItemUndoCalcList(Long productArrivalOrderId) {
        List<ProductArrivalOrderItem> productArrivalOrderItemList = productArrivalOrderItemDao.findAllUndoByProductArrivalOrderId(productArrivalOrderId);
        return productArrivalOrderItemList.stream().map(this::toExProductArrivalOrderItemUndoDto).toList();
    }

    @Override
    public List<ExProductArrivalOrderItemDto> productAarrivalOrderItemDoneCalcList(Long productArrivalOrderId) {
        List<ProductArrivalOrderItem> productArrivalOrderItemList = productArrivalOrderItemDao.findAllDoneByProductArrivalOrderId(productArrivalOrderId);
        return productArrivalOrderItemList.stream().map(this::toExProductArrivalOrderItemDto).toList();
    }

    @Override
    public List<ExProductSnDto> productAarrivalOrderItemSnUndoList(Long productArrivalOrderId, Long productArrivalOrderItemId) throws BusinessException {
        ProductArrivalOrderItem productArrivalOrderItem = productArrivalOrderItemDao.findByIdAndProductArrivalOrderId(productArrivalOrderItemId, productArrivalOrderId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_ITEM_NOT_FOUND));
        return productSnService.productSnUnArrivalList(productArrivalOrderItem.getId());
    }

    @Override
    public void productArrivalOrderItemSnUndoPrint(Long userId, Long productArrivalOrderId, Long productArrivalOrderItemId, Integer printCount) throws BusinessException {
        ProductArrivalOrderItem productArrivalOrderItem = productArrivalOrderItemDao.findByIdAndProductArrivalOrderId(productArrivalOrderItemId, productArrivalOrderId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_ITEM_NOT_FOUND));
        List<ExProductSnDto> productSnDtoList = productSnService.productSnUnArrivalPrintList(productArrivalOrderItem.getId());
        printSn(userId, productSnDtoList, printCount);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Long productArrivalOrderSnInbound(Long productArrivalOrderId, String productCode, ExSnWeightDto snWeightDto) throws BusinessException {
        //1. 查询商品信息
        ExProductSkuDto productSkuDto = productSkuService.productSkuInfo(productCode);

        //2. 查询到货单详情信息
        ProductArrivalOrderItem productArrivalOrderItem = productArrivalOrderItemDao.findUndoBySkuCode(productSkuDto.getCode())
                .orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_ITEM_NOT_FOUND));
        //2.1 检查id是否一致
        if (productArrivalOrderId != null && !productArrivalOrderItem.getId().equals(productArrivalOrderId)) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_NOT_FOUND);
        }
        //3. 查询到货单信息
        ProductArrivalOrder productArrivalOrder = productArrivalOrderDao.findById(productArrivalOrderItem.getProductArrivalOrderId()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_NOT_FOUND));
        if (productArrivalOrder.getInboundStatus() == OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_INBOUND_DONE);
        }

        //4. 如果是本到货单的货物，则开始数量计算
        //4.1 更新SN状态
        String snCode = productSnService.productSnInbound(productArrivalOrderItem.getId(), snWeightDto.getQuality());
        //4.2 更新到货单数量和状态
        this.updateProductArrivalOrderInboundCount(productArrivalOrder);
        //4.3 更新到货单详情数量和状态
        this.updateProductArrivalOrderItemInboundCount(productArrivalOrderItem);

        //5. 通知轻流
        qingFlowClient.productSnInboundNotice(snCode, snWeightDto.getWeight(), snWeightDto.getLength(), snWeightDto.getWidth(), snWeightDto.getHeight(), snWeightDto.getQuality());

        return productArrivalOrderItem.getId();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void productArrivalOrderSnOnShelf(String productCode, String shelfNo) throws BusinessException {
        ExProductSkuDto productSkuDto = productSkuService.productSkuInfo(productCode);

        //1. 更新SN状态
        ExProductSnDto productSnDto = productSnService.productSnOnShelf(productSkuDto.getCode(), shelfNo);

        ProductArrivalOrderItem productArrivalOrderItem = productArrivalOrderItemDao.findById(productSnDto.getArrivalOrderItemId()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_ITEM_NOT_FOUND));
        ProductArrivalOrder productArrivalOrder = productArrivalOrderDao.findById(productArrivalOrderItem.getProductArrivalOrderId()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_NOT_FOUND));

        if (productArrivalOrder.getOnShelfStatus() == OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_ON_SHELF_DONE);
        }

        //2. 更新到货单数量和状态
        this.updateProductArrivalOrderOnShelfCount(productArrivalOrder);
        //3. 更新到货单详情数量和状态
        this.updateProductArrivalOrderItemOnShelfCount(productArrivalOrderItem);
        //4. 通知清流
        qingFlowClient.productSnOnShelfNotice(productSnDto.getCode(), productSnDto.getShelfNo());
    }

    @Override
    public Integer productArrivalOrderOnShelfUndoListCount() {
        List<ProductArrivalOrder> productArrivalOrderList = productArrivalOrderDao.findAllOnShelfUndo();
        return productArrivalOrderList.stream().mapToInt(productArrivalOrder -> {
            List<ProductArrivalOrderItem> productArrivalOrderItemList = productArrivalOrderItemDao.findAllDoneByProductArrivalOrderId(productArrivalOrder.getId());
            return productArrivalOrderItemList.size();
        }).sum();
    }

    @Override
    public List<ExProductArrivalOrderDto> productArrivalOrderOnShelfUndoListAll() {
        List<ProductArrivalOrder> productArrivalOrderList = productArrivalOrderDao.findAllOnShelfUndo();
        return productArrivalOrderList.stream().map(productArrivalOrder -> {
            ExProductArrivalOrderDto productArrivalOrderDto = new ExProductArrivalOrderDto();
            productArrivalOrderDto.setThirdOrderNo(productArrivalOrder.getThirdOrderNo());

            List<ProductArrivalOrderItem> productArrivalOrderItemList = productArrivalOrderItemDao.findAllDoneByProductArrivalOrderId(productArrivalOrder.getId());
            productArrivalOrderDto.setProductArrivalOrderItemList(productArrivalOrderItemList.stream().map(this::toExProductArrivalOrderItemOnShelfUndoDto).toList());
            return productArrivalOrderDto;
        }).toList();
    }

    /**
     * DB数据转DTO数据
     *
     * @param productArrivalOrderItem DB数据
     * @return DTO数据
     */
    private ExProductArrivalOrderItemDto toExProductArrivalOrderItemUndoDto(ProductArrivalOrderItem productArrivalOrderItem) {
        ExProductArrivalOrderItemDto productArrivalOrderItemDto = new ExProductArrivalOrderItemDto();
        productArrivalOrderItemDto.setId(productArrivalOrderItem.getId());
        productArrivalOrderItemDto.setProductSkuCode(productArrivalOrderItem.getProductSkuCode());
        productArrivalOrderItemDto.setInboundCount(productArrivalOrderItem.getInboundCount());
        productArrivalOrderItemDto.setTotalCount(productArrivalOrderItem.getTotalCount());
        return productArrivalOrderItemDto;
    }

    /**
     * DB数据转DTO数据
     *
     * @param productArrivalOrderItem DB数据
     * @return DTO数据
     */
    private ExProductArrivalOrderItemDto toExProductArrivalOrderItemOnShelfUndoDto(ProductArrivalOrderItem productArrivalOrderItem) {
        ExProductArrivalOrderItemDto productArrivalOrderItemDto = new ExProductArrivalOrderItemDto();
        productArrivalOrderItemDto.setId(productArrivalOrderItem.getId());
        productArrivalOrderItemDto.setProductCode(productSkuService.getProductSkuProductCode(productArrivalOrderItem.getProductSkuCode()));
        productArrivalOrderItemDto.setOnShelfCount(productArrivalOrderItem.getOnShelfCount());
        productArrivalOrderItemDto.setTotalCount(productArrivalOrderItem.getTotalCount());
        return productArrivalOrderItemDto;
    }

    /**
     * DB数据转DTO数据
     *
     * @param productArrivalOrderItem DB数据
     * @return DTO数据
     */
    private ExProductArrivalOrderItemDto toExProductArrivalOrderItemDto(ProductArrivalOrderItem productArrivalOrderItem) {
        ExProductArrivalOrderItemDto productArrivalOrderItemDto = new ExProductArrivalOrderItemDto();
        productArrivalOrderItemDto.setId(productArrivalOrderItem.getId());
        productArrivalOrderItemDto.setProductSkuCode(productArrivalOrderItem.getProductSkuCode());
        productArrivalOrderItemDto.setInboundCount(productArrivalOrderItem.getInboundCount());
        productArrivalOrderItemDto.setOnShelfCount(productArrivalOrderItem.getOnShelfCount());
        productArrivalOrderItemDto.setTotalCount(productArrivalOrderItem.getTotalCount());
        return productArrivalOrderItemDto;
    }

    /**
     * 循环打印标签
     *
     * @param userId           用户id
     * @param productSnDtoList 序列商品列表
     * @param printCount       打印数量
     */
    private void printSn(Long userId, List<ExProductSnDto> productSnDtoList, int printCount) throws BusinessException {
        String devicePrinterSn = devicePrinterService.devicePrinterSn(userId);
        int printed = 0;
        for (ExProductSnDto productSnDto : productSnDtoList) {
            try {
                printTemplate3(devicePrinterSn, productSnDto);
                printed++;
                if (printed >= printCount) {
                    break;
                }
            } catch (Exception e) {
                log.warn("======batch print productSn[{}] error, : ", productSnDto.getCode(), e);
            }
        }
    }

    /**
     * 打印模板3
     *
     * @param devicePrinterSn 打印机信息
     * @param productSnDto    序列商品信息
     */
    private void printTemplate3(String devicePrinterSn, ExProductSnDto productSnDto) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("productSn", productSnDto);

        devicePrinterService.printTemplate(devicePrinterSn, "template3.ftl", params);
    }

    /**
     * 更新到货单统计信息
     *
     * @param productArrivalOrder 到货单
     */
    private void updateProductArrivalOrderInboundCount(ProductArrivalOrder productArrivalOrder) {
        productArrivalOrder.setInboundCount(productArrivalOrder.getInboundCount() + 1);
        if (productArrivalOrder.getInboundCount().equals(productArrivalOrder.getTotalCount())) {
            productArrivalOrder.setInboundStatus(OrderStatus.DONE);
        } else {
            productArrivalOrder.setInboundStatus(OrderStatus.IN_PROCESSING);
        }
        productArrivalOrderDao.save(productArrivalOrder);
    }

    /**
     * 更新到货单详情统计信息
     *
     * @param productArrivalOrderItem 到货单详情
     */
    private void updateProductArrivalOrderItemInboundCount(ProductArrivalOrderItem productArrivalOrderItem) {
        productArrivalOrderItem.setInboundCount(productArrivalOrderItem.getInboundCount() + 1);
        if (productArrivalOrderItem.getInboundCount().equals(productArrivalOrderItem.getTotalCount())) {
            productArrivalOrderItem.setInboundStatus(OrderStatus.DONE);
        } else {
            productArrivalOrderItem.setInboundStatus(OrderStatus.IN_PROCESSING);
        }
        productArrivalOrderItemDao.save(productArrivalOrderItem);
    }

    /**
     * 更新到货单统计信息
     *
     * @param productArrivalOrder 到货单
     */
    private void updateProductArrivalOrderOnShelfCount(ProductArrivalOrder productArrivalOrder) {
        productArrivalOrder.setOnShelfCount(productArrivalOrder.getOnShelfCount() + 1);
        if (productArrivalOrder.getOnShelfCount().equals(productArrivalOrder.getTotalCount())) {
            productArrivalOrder.setOnShelfStatus(OrderStatus.DONE);
        } else {
            productArrivalOrder.setOnShelfStatus(OrderStatus.IN_PROCESSING);
        }
        productArrivalOrderDao.save(productArrivalOrder);
    }

    /**
     * 更新到货单详情统计信息
     *
     * @param productArrivalOrderItem 到货单详情
     */
    private void updateProductArrivalOrderItemOnShelfCount(ProductArrivalOrderItem productArrivalOrderItem) {
        productArrivalOrderItem.setOnShelfCount(productArrivalOrderItem.getOnShelfCount() + 1);
        if (productArrivalOrderItem.getOnShelfCount().equals(productArrivalOrderItem.getTotalCount())) {
            productArrivalOrderItem.setOnShelfStatus(OrderStatus.DONE);
        } else {
            productArrivalOrderItem.setOnShelfStatus(OrderStatus.IN_PROCESSING);
        }
        productArrivalOrderItemDao.save(productArrivalOrderItem);
    }
}
