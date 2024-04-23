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
import cn.jzyunqi.common.support.NumberGenHelper;
import cn.jzyunqi.common.support.spring.aop.event.EmitEvent;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.constant.WmsCache;
import com.dicfree.ms.wms.common.constant.WmsEmitConstant;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExBoxArrivalOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxArrivalOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSkuDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import com.dicfree.ms.wms.common.dto.ex.qingflow.BoxLocationReq;
import com.dicfree.ms.wms.common.dto.ex.query.ExBoxArrivalOrderQueryDto;
import com.dicfree.ms.wms.common.enums.LocationType;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import com.dicfree.ms.wms.common.enums.StocktakeDirection;
import com.dicfree.ms.wms.common.enums.StocktakeType;
import com.dicfree.ms.wms.repository.jpa.dao.BoxArrivalOrderDao;
import com.dicfree.ms.wms.repository.jpa.dao.BoxArrivalOrderItemDao;
import com.dicfree.ms.wms.repository.jpa.dao.querydsl.BoxArrivalOrderQry;
import com.dicfree.ms.wms.repository.jpa.entity.BoxArrivalOrder;
import com.dicfree.ms.wms.repository.jpa.entity.BoxArrivalOrderItem;
import com.dicfree.ms.wms.service.BoxArrivalOrderService;
import com.dicfree.ms.wms.service.BoxSkuService;
import com.dicfree.ms.wms.service.BoxSnService;
import com.dicfree.ms.wms.service.DevicePrinterService;
import com.dicfree.ms.wms.service.StocktakeRecordService;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
@Slf4j
@Service("boxArrivalOrderService")
public class BoxArrivalOrderServiceImpl implements BoxArrivalOrderService {

    @Resource
    private BoxArrivalOrderDao boxArrivalOrderDao;

    @Resource
    private BoxArrivalOrderItemDao boxArrivalOrderItemDao;

    @Resource
    private BoxSkuService boxSkuService;

    @Resource
    private BoxSnService boxSnService;

    @Resource
    private StocktakeRecordService stocktakeRecordService;

    @Resource
    private DevicePrinterService devicePrinterService;

    @Resource
    private QingFlowClient qingFlowClient;

    @Resource
    private RedisHelper redisHelper;

    @Resource
    private NumberGenHelper numberGenHelper;

    @Resource
    private DateTimeProvider dateTimeProvider;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void boxArrivalOrderArrival(String supplier, String thirdOrderNo) throws BusinessException {
        BoxArrivalOrder arrivalOrder = boxArrivalOrderDao.findBySupplierAndThirdOrderNo(supplier, thirdOrderNo).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_NOT_FOUND));
        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));
        arrivalOrder.setArrivingDate(currTime);
        boxArrivalOrderDao.save(arrivalOrder);
    }

    @Override
    public List<ExBoxArrivalOrderDto> boxArrivalOrderLocationUndoList() {
        List<BoxArrivalOrder> boxArrivalOrderList = boxArrivalOrderDao.findLocationUndoAll();
        return boxArrivalOrderList.stream().map(boxArrivalOrder -> {
            ExBoxArrivalOrderDto boxArrivalOrderDto = new ExBoxArrivalOrderDto();
            boxArrivalOrderDto.setId(boxArrivalOrder.getId());
            boxArrivalOrderDto.setThirdOrderNo(boxArrivalOrder.getThirdOrderNo());
            boxArrivalOrderDto.setTotalWholeCount(boxArrivalOrder.getTotalWholeCount());
            boxArrivalOrderDto.setTotalBulkCount(boxArrivalOrder.getTotalBulkCount());
            return boxArrivalOrderDto;
        }).toList();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    @EmitEvent(WmsEmitConstant.WMS_BOX_ARRIVAL_ORDER_SERVICE_LOCATION_LOCK)
    public void boxArrivalOrderLocationLock(Long boxArrivalOrderId, @NotBlank String wholeLocation, @NotBlank String bulkLocation) throws BusinessException {
        BoxArrivalOrder arrivalOrder = boxArrivalOrderDao.findById(boxArrivalOrderId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_NOT_FOUND));
        if (arrivalOrder.getLocationLock()) {
            throw new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_LOCATION_LOCKED);
        }
        String[] wholeLocationArray = StringUtilPlus.split(wholeLocation, StringUtilPlus.COMMA);
        String[] bulkLocationArray = StringUtilPlus.split(bulkLocation, StringUtilPlus.COMMA);
        if (arrivalOrder.getTotalWholeCount() != wholeLocationArray.length || arrivalOrder.getTotalBulkCount() != bulkLocationArray.length) {
            throw new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_LOCATION_COUNT_NOT_MATCH);
        }

        List<BoxLocationReq> boxLocationLockReqList = new ArrayList<>();
        List<BoxLocationReq> boxLocationAssignReqList = new ArrayList<>();
        for (String locationCode : wholeLocationArray) {
            BoxLocationReq lockRequest = new BoxLocationReq();
            lockRequest.setUniqueBoxArrivalOrderNo(arrivalOrder.getUniqueNo());
            lockRequest.setLocationType(LocationType.Whole);
            lockRequest.setLocationCode(locationCode);

            boxLocationLockReqList.add(lockRequest);
            boxLocationAssignReqList.add(lockRequest);
        }
        for (String locationCode : bulkLocationArray) {
            BoxLocationReq lockRequest = new BoxLocationReq();
            lockRequest.setUniqueBoxArrivalOrderNo(arrivalOrder.getUniqueNo());
            lockRequest.setLocationType(LocationType.Bulk);
            lockRequest.setLocationCode(locationCode);
            boxLocationLockReqList.add(lockRequest);


            List<BoxArrivalOrderItem> boxArrivalOrderItemList = boxArrivalOrderItemDao.findAllBulkByBoxArrivalOrderId(boxArrivalOrderId);
            boxLocationAssignReqList.addAll(boxArrivalOrderItemList.stream().flatMap(boxArrivalOrderItem -> Stream.of(boxArrivalOrderItem.getTotalCount()).map(count -> {
                BoxLocationReq assignRequest = new BoxLocationReq();
                assignRequest.setUniqueBoxArrivalOrderNo(arrivalOrder.getUniqueNo());
                assignRequest.setLocationType(LocationType.Bulk);
                assignRequest.setBskuCode(boxArrivalOrderItem.getBoxSkuCode());
                return assignRequest;
            })).toList());
        }

        //通知轻流
        for (BoxLocationReq boxLocationReq : boxLocationLockReqList) {
            qingFlowClient.boxLocationLockNotice(boxLocationReq);
        }

        //保存到缓存，右进左出
        for (BoxLocationReq boxLocationReq : boxLocationAssignReqList) {
            redisHelper.lRightPush(WmsCache.WMS_AO_LOCATION_ASSIGN_L, RedisHelper.COMMON_KEY, boxLocationReq);
        }
        //轻流通知成功后保存数据
        arrivalOrder.setLocationLock(Boolean.TRUE);
        boxArrivalOrderDao.save(arrivalOrder);
    }

    @Override
    public void boxArrivalOrderLocationAssign(String boxSkuCode, String locationCode){
        log.info("===boxArrivalOrderLocationAssign with boxSkuCode[{}] and locationCode[{}]===", boxSkuCode, locationCode);

        BoxLocationReq boxLocationReq = (BoxLocationReq) redisHelper.lLeftPop(WmsCache.WMS_AO_LOCATION_ASSIGN_L, RedisHelper.COMMON_KEY);
        if(boxLocationReq == null){
            log.info("===boxArrivalOrderLocationAssign finished===");
            return;
        }

        try {
            if (boxLocationReq.getLocationType() == LocationType.Whole) {
                qingFlowClient.boxWholeLocationAssignNotice(boxLocationReq.getUniqueBoxArrivalOrderNo(), boxLocationReq.getLocationCode());
            } else {
                qingFlowClient.boxBulkLocationAssignNotice(boxLocationReq.getUniqueBoxArrivalOrderNo(), boxLocationReq.getBskuCode());
            }
        } catch (Exception e) {
            log.error("===qingflow boxArrivalOrderLocationAssign[{}] locationCode failed", boxLocationReq, e);
        }
    }

    @Override
    public PageDto<ExBoxArrivalOrderDto> boxArrivalOrderPage(ExBoxArrivalOrderQueryDto boxArrivalOrderQueryDto, Pageable pageable) {
        Page<BoxArrivalOrder> boxArrivalOrderPage = boxArrivalOrderDao.findAll(BoxArrivalOrderQry.searchBoxArrivalOrder(boxArrivalOrderQueryDto), BoxArrivalOrderQry.searchBoxArrivalOrderOrder(pageable));

        List<ExBoxArrivalOrderDto> boxArrivalOrderDtoList = boxArrivalOrderPage.stream().map(boxArrivalOrder -> {
            ExBoxArrivalOrderDto boxArrivalOrderDto = new ExBoxArrivalOrderDto();
            boxArrivalOrderDto.setId(boxArrivalOrder.getId());
            boxArrivalOrderDto.setArrivingDate(boxArrivalOrder.getArrivingDate());
            boxArrivalOrderDto.setThirdOrderNo(boxArrivalOrder.getThirdOrderNo());
            boxArrivalOrderDto.setContainerNoVirtual(boxArrivalOrder.getContainerNoVirtual());
            boxArrivalOrderDto.setContainerNoReal(boxArrivalOrder.getContainerNoReal());
            boxArrivalOrderDto.setInboundCount(boxArrivalOrder.getInboundCount());
            boxArrivalOrderDto.setTotalCount(boxArrivalOrder.getTotalCount());
            return boxArrivalOrderDto;
        }).collect(Collectors.toList());

        return new PageDto<>(boxArrivalOrderDtoList, boxArrivalOrderPage.getTotalElements());
    }

    @Override
    public List<ExBoxArrivalOrderItemDto> boxArrivalOrderItemList(Long boxArrivalOrderId) {
        List<BoxArrivalOrderItem> boxArrivalOrderItemList = boxArrivalOrderItemDao.findAllByArrivalOrderId(boxArrivalOrderId);
        return boxArrivalOrderItemList.stream().map(this::toExBoxArrivalOrderItemDto).toList();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public ExBoxArrivalOrderDto boxArrivalOrderAdd(ExBoxArrivalOrderDto boxArrivalOrderDto) throws BusinessException {
        int totalCount = boxArrivalOrderDto.getBoxArrivalOrderItemList().stream().mapToInt(ExBoxArrivalOrderItemDto::getTotalCount).sum();

        Long seq = redisHelper.vIncrement(WmsCache.WMS_AO_CONTAINER_NO_VIRTUAL_V, RedisHelper.COMMON_KEY, 1L);
        BoxArrivalOrder boxArrivalOrder = new BoxArrivalOrder();
        String boxArrivalOrderNo = numberGenHelper.incubateDateNumber(hexId ->
                StringUtilPlus.join(
                        "IB",
                        hexId.getTime().format(DateTimeFormatter.ofPattern("ddMMyyyy")),
                        boxArrivalOrderDto.getSupplier(),
                        StringUtilPlus.HYPHEN,
                        hexId.getTime().format(DateTimeFormatter.ofPattern("HHmmss")),
                        StringUtilPlus.leftPad(hexId.getSequence(), 4, '0')
                ));
        boxArrivalOrder.setUniqueNo(boxArrivalOrderNo);
        boxArrivalOrder.setSupplier(boxArrivalOrderDto.getSupplier());
        boxArrivalOrder.setThirdOrderNo(boxArrivalOrderDto.getThirdOrderNo());
        boxArrivalOrder.setContainerNoVirtual(StringUtilPlus.leftPad(seq, 5, '0'));
        boxArrivalOrder.setContainerNoReal(boxArrivalOrderDto.getContainerNoReal());
        boxArrivalOrder.setArrivingDate(boxArrivalOrderDto.getArrivingDate());
        boxArrivalOrder.setInboundCount(0);
        boxArrivalOrder.setTotalCount(totalCount);
        boxArrivalOrder.setTotalWholeCount(boxArrivalOrderDto.getTotalWholeCount());
        boxArrivalOrder.setTotalBulkCount(boxArrivalOrderDto.getTotalBulkCount());
        boxArrivalOrder.setInboundStatus(OrderStatus.PENDING);
        boxArrivalOrder.setLocationLock(Boolean.FALSE);
        boxArrivalOrder.setPrinted(Boolean.FALSE);
        boxArrivalOrderDao.save(boxArrivalOrder);

        for (ExBoxArrivalOrderItemDto boxArrivalOrderItemDto : boxArrivalOrderDto.getBoxArrivalOrderItemList()) {
            String boxSkuCode = boxSkuService.getBoxSkuCode(boxArrivalOrderItemDto.getSupplierBoxCode(), boxArrivalOrderDto.getSupplier());

            //创建订单详情
            BoxArrivalOrderItem boxArrivalOrderItem = new BoxArrivalOrderItem();
            boxArrivalOrderItem.setBoxArrivalOrderId(boxArrivalOrder.getId());
            boxArrivalOrderItem.setBoxSkuCode(boxSkuCode);
            boxArrivalOrderItem.setInboundCount(0);
            boxArrivalOrderItem.setTotalCount(boxArrivalOrderItemDto.getTotalCount());
            boxArrivalOrderItem.setLocationType(boxArrivalOrderItemDto.getLocationType());
            boxArrivalOrderItem.setInboundStatus(OrderStatus.PENDING);
            boxArrivalOrderItem.setPrinted(Boolean.FALSE);
            boxArrivalOrderItemDao.save(boxArrivalOrderItem);

            //创建SN详情
            List<ExBoxSnDto> boxSnDtoList = boxSnService.boxSnAdd(boxArrivalOrderItem.getId(), boxSkuCode, boxArrivalOrderItemDto.getTotalCount(), "AOB" + boxArrivalOrderItem.getId().toString());

            //返回订单sn详情
            boxArrivalOrderItemDto.setBoxSnList(boxSnDtoList);
        }

        //返回订单id
        boxArrivalOrderDto.setId(boxArrivalOrder.getId());
        boxArrivalOrderDto.setUniqueNo(boxArrivalOrder.getUniqueNo());
        return boxArrivalOrderDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void boxArrivalOrderStatusEdit(String uniqueNo, String boxSkuCode, OrderStatus status) throws BusinessException {
        BoxArrivalOrder boxArrivalOrder = boxArrivalOrderDao.findByUniqueNo(uniqueNo).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_NOT_FOUND));

        //保存整装箱入库订单详情状态
        List<BoxArrivalOrderItem> boxArrivalOrderItemList = boxArrivalOrderItemDao.findAllByArrivalOrderId(boxArrivalOrder.getId());
        BoxArrivalOrderItem boxArrivalOrderItem = boxArrivalOrderItemList.stream().filter(item -> item.getBoxSkuCode().equals(boxSkuCode)).findFirst().orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_ITEM_NOT_FOUND));
        boxArrivalOrderItem.setInboundStatus(status);
        boxArrivalOrderItemDao.save(boxArrivalOrderItem);

        //计算整装箱入库订单状态
        Map<OrderStatus, IntSummaryStatistics> groupCount = boxArrivalOrderItemList.stream().collect(Collectors.groupingBy(BoxArrivalOrderItem::getInboundStatus, Collectors.summarizingInt(value -> 1)));
        boxArrivalOrder.setInboundStatus(OrderStatus.determineStatus(groupCount, boxArrivalOrderItemList.size()));
        boxArrivalOrderDao.save(boxArrivalOrder);
    }

    @Override
    public List<ExBoxArrivalOrderDto> boxArrivalOrderUndoList() {
        List<BoxArrivalOrder> boxArrivalOrderList = boxArrivalOrderDao.findUndoBoxArrivalOrder();
        return boxArrivalOrderList.stream().map(boxArrivalOrder -> {
            ExBoxArrivalOrderDto boxArrivalOrderDto = new ExBoxArrivalOrderDto();
            boxArrivalOrderDto.setId(boxArrivalOrder.getId());
            boxArrivalOrderDto.setThirdOrderNo(boxArrivalOrder.getThirdOrderNo());
            boxArrivalOrderDto.setPrinted(boxArrivalOrder.getPrinted());
            return boxArrivalOrderDto;
        }).toList();
    }

    @Override
    public ExBoxArrivalOrderDto boxArrivalOrderInfo(String supplier, String thirdOrderNo) throws BusinessException {
        BoxArrivalOrder boxArrivalOrder = boxArrivalOrderDao.findBySupplierAndThirdOrderNo(supplier, thirdOrderNo).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_NOT_FOUND));
        ExBoxArrivalOrderDto boxArrivalOrderDto = new ExBoxArrivalOrderDto();
        boxArrivalOrderDto.setInboundStatus(boxArrivalOrder.getInboundStatus());
        boxArrivalOrderDto.setBoxArrivalOrderItemList(boxArrivalOrderItemDao.findAllByBoxArrivalOrderId(boxArrivalOrder.getId()).stream().map(boxArrivalOrderItem -> {
            ExBoxArrivalOrderItemDto boxArrivalOrderItemDto = new ExBoxArrivalOrderItemDto();
            boxArrivalOrderItemDto.setSupplierBoxCode(boxSkuService.getSupplierBoxCode(boxArrivalOrderItem.getBoxSkuCode()));
            boxArrivalOrderItemDto.setInboundCount(boxArrivalOrderItem.getInboundCount());
            boxArrivalOrderItemDto.setTotalCount(boxArrivalOrderItem.getTotalCount());
            return boxArrivalOrderItemDto;
        }).toList());
        return boxArrivalOrderDto;
    }

    @Override
    public List<ExBoxArrivalOrderItemDto> boxArrivalOrderItemUndoCalcList(Long boxArrivalOrderId) {
        List<BoxArrivalOrderItem> boxArrivalOrderItemList = boxArrivalOrderItemDao.findAllUndoByBoxArrivalOrderId(boxArrivalOrderId);
        return boxArrivalOrderItemList.stream().map(this::toExBoxArrivalOrderItemUndoDto).toList();
    }

    @Override
    public List<ExBoxArrivalOrderItemDto> boxArrivalOrderItemDoneCalcList(Long boxArrivalOrderId) {
        List<BoxArrivalOrderItem> boxArrivalOrderItemList = boxArrivalOrderItemDao.findAllDoneByBoxArrivalOrderId(boxArrivalOrderId);
        return boxArrivalOrderItemList.stream().map(this::toExBoxArrivalOrderItemDto).toList();
    }

    @Override
    public List<ExBoxSnDto> boxArrivalOrderItemSnUndoList(Long boxArrivalOrderId, Long boxArrivalOrderItemId) throws BusinessException {
        BoxArrivalOrderItem boxArrivalOrderItem = boxArrivalOrderItemDao.findByIdAndBoxArrivalOrderId(boxArrivalOrderItemId, boxArrivalOrderId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_ITEM_NOT_FOUND));
        return boxSnService.boxSnUnArrivalList(boxArrivalOrderItem.getId());
    }

    @Override
    public void boxArrivalOrderBoxSnUndoPrint(Long userId, Long boxSnId) throws BusinessException {
        ExBoxSnDto boxSnDto = boxSnService.skuDetailById(boxSnId);
        String devicePrinterSn = devicePrinterService.devicePrinterSn(userId);
        printTemplate2(devicePrinterSn, boxSnDto);
    }

    @Override
    //@Transactional(rollbackFor = BusinessException.class) 暂时不需要事务
    public void boxArrivalOrderItemSnUndoPrint(Long userId, Long boxArrivalOrderId, Long boxArrivalOrderItemId) throws BusinessException {
        BoxArrivalOrderItem boxArrivalOrderItem = boxArrivalOrderItemDao.findByIdAndBoxArrivalOrderId(boxArrivalOrderItemId, boxArrivalOrderId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_ITEM_NOT_FOUND));
        List<ExBoxSnDto> boxSnDtoList = boxSnService.boxSnUnArrivalPrintList(boxArrivalOrderItem.getId());
        printBoxSn(userId, boxSnDtoList);

        boxArrivalOrderItem.setPrinted(Boolean.TRUE);
        boxArrivalOrderItemDao.save(boxArrivalOrderItem);
    }

    @Override
    //@Transactional(rollbackFor = BusinessException.class) 暂时不需要事务
    public void boxArrivalOrderItemSnUndoPrintAll(Long userId, Long boxArrivalOrderId) throws BusinessException {
        BoxArrivalOrder boxArrivalOrder = boxArrivalOrderDao.findById(boxArrivalOrderId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_NOT_FOUND));
        List<BoxArrivalOrderItem> boxArrivalOrderItemList = boxArrivalOrderItemDao.findAllByBoxArrivalOrderId(boxArrivalOrderId);
        List<ExBoxSnDto> boxSnDtoList = boxSnService.boxSnUnArrivalPrintList(boxArrivalOrderItemList.stream().map(BoxArrivalOrderItem::getId).toList());
        printBoxSn(userId, boxSnDtoList);

        boxArrivalOrderItemList.forEach(boxArrivalOrderItem -> boxArrivalOrderItem.setPrinted(Boolean.TRUE));
        boxArrivalOrderItemDao.saveAll(boxArrivalOrderItemList);

        boxArrivalOrder.setPrinted(Boolean.TRUE);
        boxArrivalOrderDao.save(boxArrivalOrder);
    }

    @Override
    public List<ExBoxSnDto> boxArrivalOrderBoxSnList(Long boxArrivalOrderId) {
        List<BoxArrivalOrderItem> boxArrivalOrderItemList = boxArrivalOrderItemDao.findAllByBoxArrivalOrderId(boxArrivalOrderId);
        return boxSnService.boxSnArrivalList(boxArrivalOrderItemList.stream().map(BoxArrivalOrderItem::getId).toList());
    }

    @Override
    public List<ExBoxSnDto> boxArrivalOrderItemSnList(Long boxArrivalOrderId, Long boxArrivalOrderItemId) throws BusinessException {
        BoxArrivalOrderItem boxArrivalOrderItem = boxArrivalOrderItemDao.findByIdAndBoxArrivalOrderId(boxArrivalOrderItemId, boxArrivalOrderId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_ITEM_NOT_FOUND));
        return boxSnService.boxSnArrivalList(boxArrivalOrderItem.getId());
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Long boxArrivalOrderSnInbound(Long userId, Long boxArrivalOrderId, StocktakeType type, String first, String second) throws BusinessException {
        //这里需要判断扫码的数据是否在这个订单中
        //1. 根据参数查询SKU、SN
        String boxSkuCode;
        ExBoxSnDto boxSnDto = new ExBoxSnDto();
        if (type == StocktakeType.SUPPLIER_BOX_CODE) {
            boxSkuCode = boxSkuService.getBoxSkuCode(first, second);
        } else if (type == StocktakeType.BOX_SN_CODE) {
            //查找出SN对应的SKU
            boxSnDto = boxSnService.skuDetailByBoxSnCode(first);
            boxSkuCode = boxSnDto.getBoxSkuCode();
        } else if (type == StocktakeType.SUPPLIER_BOX_SN_CODE) {
            //查找出SN对应的SKU
            boxSnDto = boxSnService.skuInfoBySupplierBoxSnCode(first);
            boxSkuCode = boxSnDto.getBoxSkuCode();
        } else {
            throw new BusinessException(WmsMessageConstant.ERROR_STOCKTAKE_TYPE_ERROR);
        }

        //3. 不是本订单的货物抛错
        BoxArrivalOrderItem boxArrivalOrderItem = boxArrivalOrderItemDao.findByBoxArrivalOrderIdAndBoxSkuCode(boxArrivalOrderId, boxSkuCode)
                .orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_ITEM_SN_INBOUND_NOT_MATCH));
        BoxArrivalOrder boxArrivalOrder = boxArrivalOrderDao.findById(boxArrivalOrderId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_NOT_FOUND));
        if (boxArrivalOrder.getInboundStatus() == OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_ALREADY_DONE);
        }
        if (!boxArrivalOrder.getLocationLock()) {
            throw new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_LOCATION_NOT_LOCK);
        }

        //4. 如果是本订单的货物，则开始数量计算
        //4.1 更新SN状态
        String boxSnCode = boxSnService.boxSnInbound(boxArrivalOrderItem.getId(), boxSnDto.getCode());
        //4.2 更新订单数量和状态
        this.updateBoxArrivalOrderCount(boxArrivalOrder);
        //4.3 更新订单详情数量和状态
        this.updateBoxArrivalOrderItemCount(boxArrivalOrderItem);

        //4.4 打印信息以及通知外部
        String devicePrinterSn = devicePrinterService.devicePrinterSn(userId);
        if (type == StocktakeType.SUPPLIER_BOX_SN_CODE) {
            printTemplate2(devicePrinterSn, boxSnDto);
        } else if (type == StocktakeType.SUPPLIER_BOX_CODE) {
            printTemplate1(devicePrinterSn, boxSkuService.boxSkuInfo(boxSkuCode));
        }

        //4.5 记录本地日志
        stocktakeRecordService.stocktakeLog(type, StocktakeDirection.IN, first, second);
        //4.6 通知轻流
        qingFlowClient.boxSnInboundNotice(boxSnCode);

        return boxArrivalOrderItem.getId();
    }

    /**
     * 更新订单统计信息
     *
     * @param boxArrivalOrder 订单
     */
    private void updateBoxArrivalOrderCount(BoxArrivalOrder boxArrivalOrder) {
        boxArrivalOrder.setInboundCount(boxArrivalOrder.getInboundCount() + 1);
        if (boxArrivalOrder.getInboundCount().equals(boxArrivalOrder.getTotalCount())) {
            boxArrivalOrder.setInboundStatus(OrderStatus.DONE);
        } else {
            boxArrivalOrder.setInboundStatus(OrderStatus.IN_PROCESSING);
        }
        boxArrivalOrderDao.save(boxArrivalOrder);
    }

    /**
     * 更新订单详情统计信息
     *
     * @param boxArrivalOrderItem 订单详情
     */
    private void updateBoxArrivalOrderItemCount(BoxArrivalOrderItem boxArrivalOrderItem) {
        boxArrivalOrderItem.setInboundCount(boxArrivalOrderItem.getInboundCount() + 1);
        if (boxArrivalOrderItem.getInboundCount().equals(boxArrivalOrderItem.getTotalCount())) {
            boxArrivalOrderItem.setInboundStatus(OrderStatus.DONE);
        } else {
            boxArrivalOrderItem.setInboundStatus(OrderStatus.IN_PROCESSING);
        }
        boxArrivalOrderItemDao.save(boxArrivalOrderItem);
    }

    /**
     * DB数据转DTO数据
     *
     * @param boxArrivalOrderItem DB数据
     * @return DTO数据
     */
    private ExBoxArrivalOrderItemDto toExBoxArrivalOrderItemDto(BoxArrivalOrderItem boxArrivalOrderItem) {
        ExBoxArrivalOrderItemDto boxArrivalOrderItemDto = new ExBoxArrivalOrderItemDto();
        boxArrivalOrderItemDto.setId(boxArrivalOrderItem.getId());
        boxArrivalOrderItemDto.setBoxSkuCode(boxArrivalOrderItem.getBoxSkuCode());
        boxArrivalOrderItemDto.setInboundCount(boxArrivalOrderItem.getInboundCount());
        boxArrivalOrderItemDto.setTotalCount(boxArrivalOrderItem.getTotalCount());
        return boxArrivalOrderItemDto;
    }


    /**
     * DB数据转DTO数据
     *
     * @param boxArrivalOrderItem DB数据
     * @return DTO数据
     */
    private ExBoxArrivalOrderItemDto toExBoxArrivalOrderItemUndoDto(BoxArrivalOrderItem boxArrivalOrderItem) {
        ExBoxSkuDto boxSkuDto = boxSkuService.boxSkuInfoWithNull(boxArrivalOrderItem.getBoxSkuCode());
        ExBoxSkuDto temp = new ExBoxSkuDto();
        temp.setLocation(boxSkuDto.getLocation());

        ExBoxArrivalOrderItemDto boxArrivalOrderItemDto = new ExBoxArrivalOrderItemDto();
        boxArrivalOrderItemDto.setId(boxArrivalOrderItem.getId());
        boxArrivalOrderItemDto.setBoxSku(temp);
        boxArrivalOrderItemDto.setBoxSkuCode(boxArrivalOrderItem.getBoxSkuCode());
        boxArrivalOrderItemDto.setInboundCount(boxArrivalOrderItem.getInboundCount());
        boxArrivalOrderItemDto.setTotalCount(boxArrivalOrderItem.getTotalCount());
        boxArrivalOrderItemDto.setPrinted(boxArrivalOrderItem.getPrinted());
        return boxArrivalOrderItemDto;
    }

    /**
     * 循环打印标签
     *
     * @param userId       用户id
     * @param boxSnDtoList 序列箱列表
     */
    private void printBoxSn(Long userId, List<ExBoxSnDto> boxSnDtoList) throws BusinessException {
        String devicePrinterSn = devicePrinterService.devicePrinterSn(userId);
        for (ExBoxSnDto boxSnDto : boxSnDtoList) {
            try {
                printTemplate2(devicePrinterSn, boxSnDto);
                Thread.sleep(300);
            } catch (Exception e) {
                log.warn("======batch print sn[{}] error, : ", boxSnDto.getCode(), e);
            }
        }
    }

    /**
     * 打印模板1
     *
     * @param devicePrinterSn 打印机SN
     * @param boxSkuDto       整装箱信息
     */
    private void printTemplate1(String devicePrinterSn, ExBoxSkuDto boxSkuDto) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("boxSkuDto", boxSkuDto);
        devicePrinterService.printTemplate(devicePrinterSn, "template1.ftl", params);
    }

    /**
     * 打印模板2
     *
     * @param devicePrinterSn 打印机SN
     * @param boxSnDto        整装箱信息
     */
    private void printTemplate2(String devicePrinterSn, ExBoxSnDto boxSnDto) throws BusinessException {
        //boxSnDto.getSupplierBoxSnCode()在倒数第3位的地方增加一个空格，用StringBuilder的方式
        StringBuilder sb = new StringBuilder(boxSnDto.getSupplierBoxSnCode());
        sb.insert(boxSnDto.getSupplierBoxSnCode().length() - 3, " ");
        boxSnDto.setSupplierBoxSnCode(sb.toString());

        Map<String, Object> params = new HashMap<>();
        params.put("boxSn", boxSnDto);

        devicePrinterService.printTemplate(devicePrinterSn, "template2.ftl", params);
    }

    /**
     * 从库位编码中提取最后的字母和数字组合
     * @param locationCode 库位编码
     * @return 最后的字母和数字组合
     */
    public static String extractLastCombination(String locationCode) {
        String regex = "([A-Za-z]+[0-9]+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(locationCode);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}
