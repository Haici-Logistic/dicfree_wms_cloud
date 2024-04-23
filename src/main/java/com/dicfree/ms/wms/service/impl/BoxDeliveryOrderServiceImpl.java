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
import cn.jzyunqi.common.support.NumberGenHelper;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExBoxDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxDeliveryOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSkuDto;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import com.dicfree.ms.wms.repository.jpa.dao.CollectionTaskDao;
import com.dicfree.ms.wms.repository.jpa.dao.BoxDeliveryOrderDao;
import com.dicfree.ms.wms.repository.jpa.dao.BoxDeliveryOrderItemDao;
import com.dicfree.ms.wms.repository.jpa.entity.CollectionTask;
import com.dicfree.ms.wms.repository.jpa.entity.BoxDeliveryOrder;
import com.dicfree.ms.wms.repository.jpa.entity.BoxDeliveryOrderItem;
import com.dicfree.ms.wms.service.BoxDeliveryOrderService;
import com.dicfree.ms.wms.service.BoxSnService;
import com.dicfree.ms.wms.service.BoxSkuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
@Service("boxDeliveryOrderService")
public class BoxDeliveryOrderServiceImpl implements BoxDeliveryOrderService {

    @Resource
    private BoxDeliveryOrderDao boxDeliveryOrderDao;

    @Resource
    private CollectionTaskDao collectionTaskDao;

    @Resource
    private BoxDeliveryOrderItemDao boxDeliveryOrderItemDao;

    @Resource
    private BoxSkuService boxSkuService;

    @Resource
    private BoxSnService boxSnService;

    @Resource
    private NumberGenHelper numberGenHelper;

    @Override
    public List<ExBoxDeliveryOrderDto> boxDeliveryOrderUndoList(Long collectionTaskId) {
        List<BoxDeliveryOrder> boxDeliveryOrderPage = boxDeliveryOrderDao.findAllWaitingByCollectionTaskId(collectionTaskId);
        return boxDeliveryOrderPage.stream().map(boxDeliveryOrder -> {
            ExBoxDeliveryOrderDto boxDeliveryOrderDto = new ExBoxDeliveryOrderDto();
            boxDeliveryOrderDto.setId(boxDeliveryOrder.getId());
            boxDeliveryOrderDto.setAddressInfo(boxDeliveryOrder.getAddressInfo());
            return boxDeliveryOrderDto;
        }).toList();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public String boxDeliveryOrderAdd(ExBoxDeliveryOrderDto boxDeliveryOrderDto) throws BusinessException {
        int totalCount = boxDeliveryOrderDto.getBoxDeliveryOrderItemList().stream().mapToInt(ExBoxDeliveryOrderItemDto::getTotalCount).sum();
        String boxDeliveryOrderNo = numberGenHelper.incubateDateNumber(hexId ->
                StringUtilPlus.join(
                        "OB",
                        hexId.getTime().format(DateTimeFormatter.ofPattern("ddMMyyyy")),
                        boxDeliveryOrderDto.getSupplier(),
                        StringUtilPlus.HYPHEN,
                        hexId.getTime().format(DateTimeFormatter.ofPattern("HHmmss")),
                        StringUtilPlus.leftPad(hexId.getSequence(), 4, '0')
                ));

        BoxDeliveryOrder boxDeliveryOrder = new BoxDeliveryOrder();
        boxDeliveryOrder.setCollectionTaskId(null);
        boxDeliveryOrder.setUniqueNo(boxDeliveryOrderNo);
        boxDeliveryOrder.setSupplier(boxDeliveryOrderDto.getSupplier());
        boxDeliveryOrder.setThirdOrderNo(boxDeliveryOrderDto.getThirdOrderNo());
        boxDeliveryOrder.setDeliveryDate(boxDeliveryOrderDto.getDeliveryDate());
        boxDeliveryOrder.setAddressInfo(boxDeliveryOrderDto.getAddressInfo());
        boxDeliveryOrder.setAppointmentId(boxDeliveryOrderDto.getAppointmentId());
        boxDeliveryOrder.setSortingMember(boxDeliveryOrderDto.getSortingMember());
        boxDeliveryOrder.setOutboundCount(0);
        boxDeliveryOrder.setTotalCount(totalCount);
        boxDeliveryOrder.setOutboundStatus(OrderStatus.PENDING);
        boxDeliveryOrderDao.save(boxDeliveryOrder);

        for (ExBoxDeliveryOrderItemDto boxDeliveryOrderItemDto : boxDeliveryOrderDto.getBoxDeliveryOrderItemList()) {
            String boxSkuCode = boxSkuService.getBoxSkuCode(boxDeliveryOrderItemDto.getSupplierBoxCode(), boxDeliveryOrderDto.getSupplier());
            //创建订单详情
            BoxDeliveryOrderItem boxDeliveryOrderItem = new BoxDeliveryOrderItem();
            boxDeliveryOrderItem.setBoxDeliveryOrderId(boxDeliveryOrder.getId());
            boxDeliveryOrderItem.setBoxSkuCode(boxSkuCode);
            boxDeliveryOrderItem.setOutboundCount(0);
            boxDeliveryOrderItem.setTotalCount(boxDeliveryOrderItemDto.getTotalCount());
            boxDeliveryOrderItem.setOutboundStatus(OrderStatus.PENDING);

            //存储前检查数量是否足够出库
            checkInboundCount(boxDeliveryOrder.getThirdOrderNo(), boxSkuCode, boxDeliveryOrderItemDto.getTotalCount());

            boxDeliveryOrderItemDao.save(boxDeliveryOrderItem);
        }

        //返回订单id
        return boxDeliveryOrder.getUniqueNo();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void boxDeliveryOrderStatusEdit(String uniqueNo, String boxSkuCode, OrderStatus status) throws BusinessException {
        BoxDeliveryOrder boxDeliveryOrder = boxDeliveryOrderDao.findByUniqueNo(uniqueNo).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_DELIVERY_ORDER_NOT_FOUND));
        CollectionTask collectionTask = collectionTaskDao.findById(boxDeliveryOrder.getCollectionTaskId()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_TASK_NOT_FOUND));

        //保存整装箱出库订单详情的状态
        List<BoxDeliveryOrderItem> boxDeliveryOrderItemList = boxDeliveryOrderItemDao.findAllByBoxDeliveryOrderId(boxDeliveryOrder.getId());
        BoxDeliveryOrderItem boxDeliveryOrderItem = boxDeliveryOrderItemList.stream().filter(item -> item.getBoxSkuCode().equals(boxSkuCode)).findFirst().orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_DELIVERY_ORDER_ITEM_NOT_FOUND));
        boxDeliveryOrderItem.setOutboundStatus(status);
        boxDeliveryOrderItemDao.save(boxDeliveryOrderItem);
        //计算整装箱出库订单状态
        Map<OrderStatus, IntSummaryStatistics> itemGroupCount = boxDeliveryOrderItemList.stream().collect(Collectors.groupingBy(BoxDeliveryOrderItem::getOutboundStatus, Collectors.summarizingInt(value -> 1)));
        boxDeliveryOrder.setOutboundStatus(OrderStatus.determineStatus(itemGroupCount, boxDeliveryOrderItemList.size()));
        boxDeliveryOrderDao.saveAndFlush(boxDeliveryOrder);

        //计算集货任务状态
        List<BoxDeliveryOrder> boxDeliveryOrderList = boxDeliveryOrderDao.findAllByCollectionTaskId(boxDeliveryOrder.getCollectionTaskId());
        Map<OrderStatus, IntSummaryStatistics> orderGroupCount = boxDeliveryOrderList.stream().collect(Collectors.groupingBy(BoxDeliveryOrder::getOutboundStatus, Collectors.summarizingInt(value -> 1)));
        collectionTask.setStatus(OrderStatus.determineStatus(orderGroupCount, boxDeliveryOrderList.size()));
        collectionTaskDao.save(collectionTask);
    }

    @Override
    public List<ExBoxDeliveryOrderDto> boxDeliveryOrderDeliveryList(List<Long> boxDeliveryOrderIdList) {
        List<BoxDeliveryOrder> boxDeliveryOrderList = boxDeliveryOrderDao.findAllByIdIn(boxDeliveryOrderIdList);
        return boxDeliveryOrderList.stream().map(boxDeliveryOrder -> {
            //组装整装箱出库订单
            ExBoxDeliveryOrderDto boxDeliveryOrderDto = BeanUtilPlus.copyAs(boxDeliveryOrder, ExBoxDeliveryOrderDto.class);

            //组装整装箱出库订单详情
            List<BoxDeliveryOrderItem> boxDeliveryOrderItemList = boxDeliveryOrderItemDao.findAllByBoxDeliveryOrderId(boxDeliveryOrder.getId());
            List<ExBoxDeliveryOrderItemDto> boxDeliveryOrderItemDtoList = boxDeliveryOrderItemList.stream().map(boxDeliveryOrderItem -> {
                ExBoxDeliveryOrderItemDto boxDeliveryOrderItemDto = BeanUtilPlus.copyAs(boxDeliveryOrderItem, ExBoxDeliveryOrderItemDto.class);

                ExBoxSkuDto boxSkuDto = boxSkuService.boxSkuInfoWithNull(boxDeliveryOrderItemDto.getBoxSkuCode());
                boxDeliveryOrderItemDto.setBoxSku(boxSkuDto);
                return boxDeliveryOrderItemDto;
            }).toList();

            boxDeliveryOrderDto.setBoxDeliveryOrderItemList(boxDeliveryOrderItemDtoList);
            return boxDeliveryOrderDto;
        }).toList();
    }

    @Override
    public ExBoxDeliveryOrderDto boxDeliveryOrderInfo(String supplier, String thirdOrderNo) throws BusinessException {
        BoxDeliveryOrder boxDeliveryOrder = boxDeliveryOrderDao.findBySupplierAndThirdOrderNo(supplier, thirdOrderNo).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_DELIVERY_ORDER_NOT_FOUND));
        ExBoxDeliveryOrderDto boxDeliveryOrderDto = new ExBoxDeliveryOrderDto();
        boxDeliveryOrderDto.setStatus(boxDeliveryOrder.getOutboundStatus());
        boxDeliveryOrderDto.setBoxDeliveryOrderItemList(boxDeliveryOrderItemDao.findAllByBoxDeliveryOrderId(boxDeliveryOrder.getId()).stream().map(boxDeliveryOrderItem -> {
            ExBoxDeliveryOrderItemDto boxDeliveryOrderItemDto = new ExBoxDeliveryOrderItemDto();
            boxDeliveryOrderItemDto.setSupplierBoxCode(boxSkuService.getSupplierBoxCode(boxDeliveryOrderItem.getBoxSkuCode()));
            boxDeliveryOrderItemDto.setOutboundCount(boxDeliveryOrderItem.getOutboundCount());
            boxDeliveryOrderItemDto.setTotalCount(boxDeliveryOrderItem.getTotalCount());
            return boxDeliveryOrderItemDto;
        }).toList());
        return boxDeliveryOrderDto;
    }

    /**
     * 校验入库数量是否足够
     *
     * @param thirdOrderNo 第三方订单号
     * @param boxSkuCode   箱号编码
     * @param needOut      待出库
     */
    private void checkInboundCount(String thirdOrderNo, String boxSkuCode, Integer needOut) throws BusinessException {
        //检查库存是否足够出库
        //1. 查询在库数量
        List<ExBoxSnDto> boxSnDtoList = boxSnService.boxSnUnDeliveryList(boxSkuCode);
        //2. 查询其它订单待出库数量
        List<BoxDeliveryOrderItem> notDoneList = boxDeliveryOrderItemDao.findAllNotDone(boxSkuCode);
        //3. 计算并比较
        int wait = notDoneList.stream().mapToInt(value -> value.getTotalCount() - value.getOutboundCount()).sum();
        int remind = boxSnDtoList.size() - wait - needOut;
        if (remind < 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_BOX_DELIVERY_ORDER_ITEM_SN_NOT_ENOUGH, boxSkuCode, boxSnDtoList.size(), wait);
        }
    }
}
