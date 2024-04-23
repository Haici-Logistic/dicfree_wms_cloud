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
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.support.spring.aop.event.EmitEvent;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.constant.WmsEmitConstant;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import com.dicfree.ms.wms.common.enums.StockStatus;
import com.dicfree.ms.wms.repository.jpa.entity.CollectionTask;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import com.dicfree.ms.wms.common.constant.WmsCache;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExCollectionTaskDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxDeliveryOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import com.dicfree.ms.wms.common.dto.ex.query.ExCollectionTaskQueryDto;
import com.dicfree.ms.wms.common.enums.StocktakeDirection;
import com.dicfree.ms.wms.common.enums.StocktakeType;
import com.dicfree.ms.wms.repository.jpa.dao.CollectionTaskDao;
import com.dicfree.ms.wms.repository.jpa.dao.BoxDeliveryOrderDao;
import com.dicfree.ms.wms.repository.jpa.dao.BoxDeliveryOrderItemDao;
import com.dicfree.ms.wms.repository.jpa.dao.querydsl.CollectionTaskQry;
import com.dicfree.ms.wms.repository.jpa.entity.BoxDeliveryOrder;
import com.dicfree.ms.wms.repository.jpa.entity.BoxDeliveryOrderItem;
import com.dicfree.ms.wms.service.CollectionTaskService;
import com.dicfree.ms.wms.service.BoxSnService;
import com.dicfree.ms.wms.service.BoxSkuService;
import com.dicfree.ms.wms.service.StocktakeRecordService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
@Service("collectionTaskService")
public class CollectionTaskServiceImpl implements CollectionTaskService {

    @Resource
    private CollectionTaskDao collectionTaskDao;

    @Resource
    private BoxDeliveryOrderDao boxDeliveryOrderDao;

    @Resource
    private BoxDeliveryOrderItemDao boxDeliveryOrderItemDao;

    @Resource
    private BoxSnService boxSnService;

    @Resource
    private BoxSkuService boxSkuService;

    @Resource
    private StocktakeRecordService stocktakeRecordService;

    @Resource
    private QingFlowClient qingFlowClient;

    @Resource
    private RedisHelper redisHelper;

    @Override
    public PageDto<ExCollectionTaskDto> collectionTaskPage(ExCollectionTaskQueryDto collectionTaskQueryDto, Pageable pageable) {
        Page<CollectionTask> collectionTaskPage = collectionTaskDao.findAll(CollectionTaskQry.searchCollectionTask(collectionTaskQueryDto), CollectionTaskQry.searchCollectionTaskOrder(pageable));

        List<ExCollectionTaskDto> collectionTaskDtoList = collectionTaskPage.stream().map(collectionTask -> {
            ExCollectionTaskDto collectionTaskDto = new ExCollectionTaskDto();
            collectionTaskDto.setId(collectionTask.getId());
            collectionTaskDto.setCollectionNoVirtual(collectionTask.getCollectionNoVirtual());
            collectionTaskDto.setCollectionNoReal(collectionTask.getCollectionNoReal());
            collectionTaskDto.setDepartureDate(collectionTask.getDepartureDate());
            collectionTaskDto.setOutboundCount(collectionTask.getOutboundCount());
            collectionTaskDto.setTotalCount(collectionTask.getTotalCount());
            collectionTaskDto.setStatus(collectionTask.getStatus());
            return collectionTaskDto;
        }).collect(Collectors.toList());

        return new PageDto<>(collectionTaskDtoList, collectionTaskPage.getTotalElements());
    }

    @Override
    public ExCollectionTaskDto collectionTaskAddInit() {
        Long seq = redisHelper.vIncrement(WmsCache.WMS_CT_COLLECTION_NO_VIRTUAL_V, RedisHelper.COMMON_KEY, 1L);
        ExCollectionTaskDto collectionTaskDto = new ExCollectionTaskDto();
        collectionTaskDto.setCollectionNoVirtual(StringUtilPlus.leftPad(seq, 5, '0'));
        return collectionTaskDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    @EmitEvent(WmsEmitConstant.WMS_COLLECTION_TASK_SERVICE_TASK_ADD)
    public void collectionTaskAdd(ExCollectionTaskDto collectionTaskDto) throws BusinessException {
        List<BoxDeliveryOrder> boxDeliveryOrderList = getAndCheckBoxDeliveryOrderList(collectionTaskDto.getBoxDeliveryOrderIdList(), null);
        int totalCount = boxDeliveryOrderList.stream().mapToInt(BoxDeliveryOrder::getTotalCount).sum();

        CollectionTask collectionTask = new CollectionTask();
        collectionTask.setCollectionNoVirtual(collectionTaskDto.getCollectionNoVirtual());
        collectionTask.setCollectionNoReal(collectionTaskDto.getCollectionNoReal());
        collectionTask.setDepartureDate(collectionTaskDto.getDepartureDate());
        collectionTask.setOutboundCount(0);
        collectionTask.setTotalCount(totalCount);
        collectionTask.setStatus(OrderStatus.PENDING);
        collectionTaskDao.save(collectionTask);

        boxDeliveryOrderList.forEach(boxDeliveryOrder -> {
            boxDeliveryOrder.setCollectionTaskId(collectionTask.getId());
            List<BoxDeliveryOrderItem> boxDeliveryOrderItemList = boxDeliveryOrderItemDao.findAllByBoxDeliveryOrderId(boxDeliveryOrder.getId());
            boxDeliveryOrderItemList.forEach(boxDeliveryOrderItem -> boxDeliveryOrderItem.setCollectionTaskId(collectionTask.getId()));
            boxDeliveryOrderItemDao.saveAll(boxDeliveryOrderItemList);
        });

        boxDeliveryOrderDao.saveAll(boxDeliveryOrderList);
    }

    @Override
    public ExCollectionTaskDto collectionTaskEditInit(Long collectionTaskId) throws BusinessException {
        CollectionTask collectionTask = collectionTaskDao.findById(collectionTaskId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_TASK_NOT_FOUND));

        ExCollectionTaskDto collectionTaskDto = new ExCollectionTaskDto();
        collectionTaskDto.setId(collectionTask.getId());
        collectionTaskDto.setCollectionNoVirtual(collectionTask.getCollectionNoVirtual());
        collectionTaskDto.setCollectionNoReal(collectionTask.getCollectionNoReal());
        collectionTaskDto.setDepartureDate(collectionTask.getDepartureDate());

        List<BoxDeliveryOrder> boxDeliveryOrderList = boxDeliveryOrderDao.findAllByCollectionTaskId(collectionTask.getId());
        List<Long> boxDeliveryOrderIdList = boxDeliveryOrderList.stream().map(BoxDeliveryOrder::getId).toList();

        collectionTaskDto.setBoxDeliveryOrderIdList(boxDeliveryOrderIdList);
        return collectionTaskDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void collectionTaskEdit(ExCollectionTaskDto collectionTaskDto) throws BusinessException {
        CollectionTask collectionTask = collectionTaskDao.findById(collectionTaskDto.getId()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_TASK_NOT_FOUND));
        if (collectionTask.getStatus() != OrderStatus.PENDING) {
            throw new BusinessException(WmsMessageConstant.ERROR_COLLECTION_TASK_CANT_EDIT);
        }

        List<BoxDeliveryOrder> boxDeliveryOrderPageList = getAndCheckBoxDeliveryOrderList(collectionTaskDto.getBoxDeliveryOrderIdList(), collectionTask.getId());
        int totalCount = boxDeliveryOrderPageList.stream().mapToInt(BoxDeliveryOrder::getTotalCount).sum();

        //修改集货任务信息
        collectionTask.setCollectionNoReal(collectionTaskDto.getCollectionNoReal());
        collectionTask.setDepartureDate(collectionTaskDto.getDepartureDate());
        collectionTask.setTotalCount(totalCount);
        collectionTaskDao.save(collectionTask);

        //1. 数据库中订单清空集货id
        List<BoxDeliveryOrder> boxDeliveryOrderDbList = boxDeliveryOrderDao.findAllByCollectionTaskId(collectionTask.getId());
        boxDeliveryOrderDbList.forEach(boxDeliveryOrder -> boxDeliveryOrder.setCollectionTaskId(null));
        boxDeliveryOrderDao.saveAll(boxDeliveryOrderDbList);

        //2. 数据库中订单详情清空集货id
        List<BoxDeliveryOrderItem> boxDeliveryOrderItemDbList = boxDeliveryOrderItemDao.findAllByCollectionTaskId(collectionTask.getId());
        boxDeliveryOrderItemDbList.forEach(boxDeliveryOrderItem -> boxDeliveryOrderItem.setCollectionTaskId(null));
        boxDeliveryOrderItemDao.saveAll(boxDeliveryOrderItemDbList);

        //3. 页面中新的订单增加集货id
        boxDeliveryOrderPageList.forEach(boxDeliveryOrder -> {
            boxDeliveryOrder.setCollectionTaskId(collectionTask.getId());
            List<BoxDeliveryOrderItem> boxDeliveryOrderItemList = boxDeliveryOrderItemDao.findAllByBoxDeliveryOrderId(boxDeliveryOrder.getId());
            boxDeliveryOrderItemList.forEach(boxDeliveryOrderItem -> boxDeliveryOrderItem.setCollectionTaskId(collectionTask.getId()));
            boxDeliveryOrderItemDao.saveAll(boxDeliveryOrderItemList);
        });
        boxDeliveryOrderDao.saveAll(boxDeliveryOrderPageList);
    }

    @Override
    public ExCollectionTaskDto collectionTaskDetail(Long collectionTaskId) throws BusinessException {
        CollectionTask collectionTask = collectionTaskDao.findById(collectionTaskId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_TASK_NOT_FOUND));

        ExCollectionTaskDto collectionTaskDto = new ExCollectionTaskDto();
        collectionTaskDto.setId(collectionTask.getId());
        collectionTaskDto.setCollectionNoVirtual(collectionTask.getCollectionNoVirtual());
        collectionTaskDto.setCollectionNoReal(collectionTask.getCollectionNoReal());
        collectionTaskDto.setDepartureDate(collectionTask.getDepartureDate());

        List<BoxDeliveryOrder> boxDeliveryOrderList = boxDeliveryOrderDao.findAllByCollectionTaskId(collectionTask.getId());
        List<ExBoxDeliveryOrderDto> boxDeliveryOrderDtoList = boxDeliveryOrderList.stream().map(this::toExBoxDeliveryOrderDto).toList();

        collectionTaskDto.setBoxDeliveryOrderList(boxDeliveryOrderDtoList);
        return collectionTaskDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void collectionTaskDelete(Long collectionTaskId) throws BusinessException {
        CollectionTask collectionTask = collectionTaskDao.findById(collectionTaskId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_TASK_NOT_FOUND));
        if (collectionTask.getStatus() != OrderStatus.PENDING) {
            throw new BusinessException(WmsMessageConstant.ERROR_COLLECTION_TASK_CANT_DELETE);
        }
        collectionTaskDao.delete(collectionTask);

        List<BoxDeliveryOrder> boxDeliveryOrderList = boxDeliveryOrderDao.findAllByCollectionTaskId(collectionTask.getId());
        boxDeliveryOrderList.forEach(boxDeliveryOrder -> boxDeliveryOrder.setCollectionTaskId(null));
        boxDeliveryOrderDao.saveAll(boxDeliveryOrderList);

        List<BoxDeliveryOrderItem> boxDeliveryOrderItemList = boxDeliveryOrderItemDao.findAllByCollectionTaskId(collectionTask.getId());
        boxDeliveryOrderItemList.forEach(boxDeliveryOrder -> boxDeliveryOrder.setCollectionTaskId(null));
        boxDeliveryOrderItemDao.saveAll(boxDeliveryOrderItemList);
    }

    @Override
    public List<ExCollectionTaskDto> collectionTaskUndoList() {
        List<CollectionTask> collectionTaskList = collectionTaskDao.findUndoCollectionTask();
        return collectionTaskList.stream().map(collectionTask -> {
            ExCollectionTaskDto collectionTaskDto = new ExCollectionTaskDto();
            collectionTaskDto.setId(collectionTask.getId());
            collectionTaskDto.setCollectionNoVirtual(collectionTask.getCollectionNoVirtual());
            collectionTaskDto.setCollectionNoReal(collectionTask.getCollectionNoReal());
            return collectionTaskDto;
        }).toList();
    }

    @Override
    public List<ExBoxDeliveryOrderItemDto> collectionTaskItemList(Long collectionTaskId) {
        List<BoxDeliveryOrderItem> boxDeliveryOrderItemList = boxDeliveryOrderItemDao.findAllByCollectionTaskId(collectionTaskId);
        return boxDeliveryOrderItemList.stream().map(this::toExBoxDeliveryOrderItemSimpleDto).toList();
    }

    @Override
    public List<ExBoxDeliveryOrderItemDto> collectionTaskItemUndoCalcList(Long collectionTaskId) {
        List<BoxDeliveryOrderItem> boxDeliveryOrderItemList = boxDeliveryOrderItemDao.findAllUndoByCollectionTaskId(collectionTaskId);
        return boxDeliveryOrderItemList.stream().map(boxDeliveryOrderItem -> {
            BoxDeliveryOrder boxDeliveryOrder = boxDeliveryOrderDao.findById(boxDeliveryOrderItem.getBoxDeliveryOrderId()).orElse(new BoxDeliveryOrder());

            ExBoxDeliveryOrderDto boxDeliveryOrderDto = new ExBoxDeliveryOrderDto();
            boxDeliveryOrderDto.setId(boxDeliveryOrder.getId());
            boxDeliveryOrderDto.setAppointmentId(boxDeliveryOrder.getAppointmentId());
            boxDeliveryOrderDto.setAddressInfo(boxDeliveryOrder.getAddressInfo());
            boxDeliveryOrderDto.setSortingMember(boxDeliveryOrder.getSortingMember());

            ExBoxDeliveryOrderItemDto boxDeliveryOrderItemDto = new ExBoxDeliveryOrderItemDto();
            boxDeliveryOrderItemDto.setId(boxDeliveryOrderItem.getId());
            boxDeliveryOrderItemDto.setBoxSkuCode(boxDeliveryOrderItem.getBoxSkuCode());
            boxDeliveryOrderItemDto.setOutboundCount(boxDeliveryOrderItem.getOutboundCount());
            boxDeliveryOrderItemDto.setTotalCount(boxDeliveryOrderItem.getTotalCount());
            boxDeliveryOrderItemDto.setBoxDeliveryOrderDto(boxDeliveryOrderDto);
            return boxDeliveryOrderItemDto;
        }).toList();
    }

    @Override
    public List<ExBoxDeliveryOrderItemDto> collectionTaskItemDoneCalcList(Long collectionTaskId) {
        List<BoxDeliveryOrderItem> boxDeliveryOrderItemList = boxDeliveryOrderItemDao.findAllDoneByCollectionTaskId(collectionTaskId);
        return boxDeliveryOrderItemList.stream().map(this::toExBoxDeliveryOrderItemSimpleDto).toList();
    }

    @Override
    public List<ExBoxSnDto> collectionTaskItemBoxSnUndoList(Long collectionTaskId, Long boxDeliveryOrderId, Long boxDeliveryOrderItemId) throws BusinessException {
        BoxDeliveryOrderItem boxDeliveryOrderItem = boxDeliveryOrderItemDao.findByIdAndBoxDeliveryOrderIdAndCollectionTaskId(boxDeliveryOrderItemId, boxDeliveryOrderId, collectionTaskId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_DELIVERY_ORDER_ITEM_NOT_FOUND));
        return boxSnService.boxSnUnDeliveryList(boxDeliveryOrderItem.getBoxSkuCode());
    }

    @Override
    public List<ExBoxSnDto> collectionTaskBoxSnList(Long collectionTaskId) {
        List<BoxDeliveryOrder> boxDeliveryOrderList = boxDeliveryOrderDao.findAllByCollectionTaskId(collectionTaskId);
        if(CollectionUtilPlus.Collection.isEmpty(boxDeliveryOrderList)){
            return new ArrayList<>();
        }
        Map<Long, ExBoxDeliveryOrderDto> tempOrderMap = boxDeliveryOrderList.stream().map(boxDeliveryOrder -> {
            ExBoxDeliveryOrderDto boxDeliveryOrderDto = new ExBoxDeliveryOrderDto();
            boxDeliveryOrderDto.setId(boxDeliveryOrder.getId());
            boxDeliveryOrderDto.setThirdOrderNo(boxDeliveryOrder.getThirdOrderNo());
            boxDeliveryOrderDto.setAppointmentId(boxDeliveryOrder.getAppointmentId());
            boxDeliveryOrderDto.setAddressInfo(boxDeliveryOrder.getAddressInfo());
            boxDeliveryOrderDto.setSortingMember(boxDeliveryOrder.getSortingMember());
            return boxDeliveryOrderDto;
        }).collect(Collectors.toMap(ExBoxDeliveryOrderDto::getId, boxDeliveryOrder -> boxDeliveryOrder));

        List<BoxDeliveryOrderItem> boxDeliveryOrderItemList = boxDeliveryOrderItemDao.findAllByCollectionTaskId(collectionTaskId);
        Map<String, BoxDeliveryOrderItem> tempItemMap = boxDeliveryOrderItemList.stream().collect(Collectors.toMap(BoxDeliveryOrderItem::getBoxSkuCode, boxDeliveryOrderItem -> boxDeliveryOrderItem));
        //获取还在库的序列箱列表
        List<ExBoxSnDto> boxSnDtoUnDeliveryList = boxSnService.boxSnUnDeliveryList(tempItemMap.keySet().stream().toList());
        //获取已出库的序列箱列表
        List<ExBoxSnDto> boxSnDtoDeliveryList = boxSnService.boxSnDeliveryList(boxDeliveryOrderItemList.stream().map(BoxDeliveryOrderItem::getId).toList());

        //备注：理论上在库的SN+已出库的SN数量，肯定是大于本次出库的SN数量，否则也不应该存在这笔出库任务

        List<ExBoxSnDto> boxSnDtoList = new ArrayList<>(boxSnDtoUnDeliveryList);
        boxSnDtoList.addAll(boxSnDtoDeliveryList);
        //这里补充一些订单信息
        boxSnDtoList.forEach(boxSnDto -> {
            BoxDeliveryOrderItem boxDeliveryOrderItem = tempItemMap.get(boxSnDto.getBoxSkuCode());
            ExBoxDeliveryOrderDto boxDeliveryOrderDto = tempOrderMap.get(boxDeliveryOrderItem.getBoxDeliveryOrderId());
            boxSnDto.setBoxDeliveryOrder(boxDeliveryOrderDto);
        });
        return boxSnDtoList;
    }

    @Override
    public List<ExBoxSnDto> collectionTaskItemBoxSnList(Long collectionTaskId, Long boxDeliveryOrderId, Long boxDeliveryOrderItemId) throws BusinessException {
        BoxDeliveryOrderItem boxDeliveryOrderItem = boxDeliveryOrderItemDao.findById(boxDeliveryOrderItemId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_DELIVERY_ORDER_ITEM_NOT_FOUND));
        List<ExBoxSnDto> boxSnDtoList = boxSnService.boxSnDeliveryList(boxDeliveryOrderItem.getId());
        List<ExBoxSnDto> boxSnDtoRealList = new ArrayList<>(boxSnDtoList);
        int diff = boxDeliveryOrderItem.getTotalCount() - boxSnDtoRealList.size();
        for (int i = 0; i < diff; i++) {
            ExBoxSnDto boxSnDto = new ExBoxSnDto();
            boxSnDto.setStatus(StockStatus.INBOUND);
            boxSnDtoRealList.add(0, boxSnDto);
        }
        return boxSnDtoRealList;
    }

    @Override
    public void collectionTaskBoxSnOutbound(Long collectionTaskId, StocktakeType type, String first, String second) throws BusinessException {
        //这里需要判断扫码的数据是否在这个任务中
        //1. 根据参数查询SKU、SN
        String boxSkuCode;
        ExBoxSnDto boxSnDto = new ExBoxSnDto();
        if (type == StocktakeType.SUPPLIER_BOX_CODE) {
            boxSkuCode = boxSkuService.getBoxSkuCode(first, second);
        } else if (type == StocktakeType.BOX_SKU_CODE) {
            boxSkuCode = first;
        } else if (type == StocktakeType.BOX_SN_CODE) {
            //查找出SN对应的SKU
            boxSnDto = boxSnService.skuDetailByBoxSnCode(first);
            boxSkuCode = boxSnDto.getBoxSkuCode();
        } else {
            throw new BusinessException(WmsMessageConstant.ERROR_STOCKTAKE_TYPE_ERROR);
        }

        //2 不是本任务的货物抛错
        BoxDeliveryOrderItem boxDeliveryOrderItem = boxDeliveryOrderItemDao.findByCollectionTaskIdAndBoxSkuCode(collectionTaskId, boxSkuCode)
                .orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_TASK_ITEM_SN_OUTBOUND_NOT_MATCH));
        BoxDeliveryOrder boxDeliveryOrder = boxDeliveryOrderDao.findById(boxDeliveryOrderItem.getBoxDeliveryOrderId()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_DELIVERY_ORDER_NOT_FOUND));
        if(boxDeliveryOrder.getOutboundStatus() == OrderStatus.DONE){
            throw new BusinessException(WmsMessageConstant.ERROR_BOX_DELIVERY_ORDER_ALREADY_DONE);
        }

        //4. 如果是本任务的货物，则开始数量计算
        //4.1 更新SN状态
        String boxSnCode = boxSnService.boxSnOutbound(boxDeliveryOrderItem.getId(), boxSkuCode, boxSnDto.getCode());
        //4.2 更新任务数量和状态
        CollectionTask collectionTask = collectionTaskDao.findById(collectionTaskId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_TASK_NOT_FOUND));
        this.updateCollectionTaskCount(collectionTask);

        //4.3 更新订单数量和状态
        this.updateBoxDeliveryOrderCount(boxDeliveryOrder);
        //4.4 更新订单详情数量和状态
        this.updateBoxDeliveryOrderItemCount(boxDeliveryOrderItem);

        //4.5 记录本地日志
        stocktakeRecordService.stocktakeLog(type, StocktakeDirection.OUT, first, second);

        //4.6 通知轻流
        qingFlowClient.boxSnOutboundNotice(boxSnCode);
    }

    /**
     * 更新详情统计信息
     *
     * @param boxDeliveryOrderItem 详情
     */
    private void updateBoxDeliveryOrderItemCount(BoxDeliveryOrderItem boxDeliveryOrderItem) {
        boxDeliveryOrderItem.setOutboundCount(boxDeliveryOrderItem.getOutboundCount() + 1);
        if (boxDeliveryOrderItem.getOutboundCount().equals(boxDeliveryOrderItem.getTotalCount())) {
            boxDeliveryOrderItem.setOutboundStatus(OrderStatus.DONE);
        } else {
            boxDeliveryOrderItem.setOutboundStatus(OrderStatus.IN_PROCESSING);
        }
        boxDeliveryOrderItemDao.save(boxDeliveryOrderItem);
    }

    /**
     * 更新订单统计信息
     *
     * @param boxDeliveryOrder 订单
     */
    private void updateBoxDeliveryOrderCount(BoxDeliveryOrder boxDeliveryOrder) {
        boxDeliveryOrder.setOutboundCount(boxDeliveryOrder.getOutboundCount() + 1);
        if (boxDeliveryOrder.getOutboundCount().equals(boxDeliveryOrder.getTotalCount())) {
            boxDeliveryOrder.setOutboundStatus(OrderStatus.DONE);
        } else {
            boxDeliveryOrder.setOutboundStatus(OrderStatus.IN_PROCESSING);
        }
        boxDeliveryOrderDao.save(boxDeliveryOrder);
    }

    /**
     * 更新任务统计信息
     *
     * @param collectionTask 任务
     */
    private void updateCollectionTaskCount(CollectionTask collectionTask) {
        collectionTask.setOutboundCount(collectionTask.getOutboundCount() + 1);
        if (collectionTask.getOutboundCount().equals(collectionTask.getTotalCount())) {
            collectionTask.setStatus(OrderStatus.DONE);
        } else {
            collectionTask.setStatus(OrderStatus.IN_PROCESSING);
        }
        collectionTaskDao.save(collectionTask);
    }

    /**
     * DB数据转DTO数据
     *
     * @param boxDeliveryOrder DB数据
     * @return DTO数据
     */
    private ExBoxDeliveryOrderDto toExBoxDeliveryOrderDto(BoxDeliveryOrder boxDeliveryOrder) {
        ExBoxDeliveryOrderDto boxDeliveryOrderDto = new ExBoxDeliveryOrderDto();
        boxDeliveryOrderDto.setId(boxDeliveryOrder.getId());
        boxDeliveryOrderDto.setThirdOrderNo(boxDeliveryOrder.getThirdOrderNo());
        boxDeliveryOrderDto.setOutboundCount(boxDeliveryOrder.getOutboundCount());
        boxDeliveryOrderDto.setTotalCount(boxDeliveryOrder.getTotalCount());
        return boxDeliveryOrderDto;
    }

    /**
     * DB数据转DTO数据
     *
     * @param boxDeliveryOrderItem DB数据
     * @return DTO数据
     */
    private ExBoxDeliveryOrderItemDto toExBoxDeliveryOrderItemSimpleDto(BoxDeliveryOrderItem boxDeliveryOrderItem) {
        ExBoxDeliveryOrderItemDto boxDeliveryOrderItemDto = new ExBoxDeliveryOrderItemDto();
        boxDeliveryOrderItemDto.setId(boxDeliveryOrderItem.getId());
        boxDeliveryOrderItemDto.setBoxDeliveryOrderId(boxDeliveryOrderItem.getBoxDeliveryOrderId());
        boxDeliveryOrderItemDto.setBoxSkuCode(boxDeliveryOrderItem.getBoxSkuCode());
        boxDeliveryOrderItemDto.setOutboundCount(boxDeliveryOrderItem.getOutboundCount());
        boxDeliveryOrderItemDto.setTotalCount(boxDeliveryOrderItem.getTotalCount());
        return boxDeliveryOrderItemDto;
    }

    /**
     * 获取并检查集货任务中的整装箱出库订单西悉尼
     *
     * @param boxDeliveryOrderIdList 整装箱出库订单id列表
     * @param collectionTaskId     集货任务id
     * @return 整装箱出库订单列表
     */
    private List<BoxDeliveryOrder> getAndCheckBoxDeliveryOrderList(List<Long> boxDeliveryOrderIdList, Long collectionTaskId) throws BusinessException {
        List<BoxDeliveryOrder> boxDeliveryOrderList = boxDeliveryOrderDao.findAllById(boxDeliveryOrderIdList);
        if (boxDeliveryOrderList.isEmpty()) {
            throw new BusinessException(WmsMessageConstant.ERROR_BOX_DELIVERY_ORDER_AT_LAST_ONE);
        }
        for (BoxDeliveryOrder boxDeliveryOrder : boxDeliveryOrderList) {
            if (collectionTaskId != null) {
                if (boxDeliveryOrder.getCollectionTaskId() != null && !boxDeliveryOrder.getCollectionTaskId().equals(collectionTaskId)) {
                    throw new BusinessException(WmsMessageConstant.ERROR_BOX_DELIVERY_ORDER_IS_IN_OTHER_COLLECTION);
                }
            } else {
                if (boxDeliveryOrder.getCollectionTaskId() != null) {
                    throw new BusinessException(WmsMessageConstant.ERROR_BOX_DELIVERY_ORDER_IS_IN_OTHER_COLLECTION);
                }
            }
        }
        return boxDeliveryOrderList;
    }
}
