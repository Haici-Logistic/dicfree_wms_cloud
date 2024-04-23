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
package com.dicfree.ms.cts.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import com.dicfree.ms.cts.common.constant.CtsMessageConstant;
import com.dicfree.ms.cts.common.dto.ex.ExTransportOrderDto;
import com.dicfree.ms.cts.common.dto.ex.query.ExTransportOrderQueryDto;
import com.dicfree.ms.cts.common.enums.TransportOrderStatus;
import com.dicfree.ms.cts.repository.jpa.dao.TransportOrderDao;
import com.dicfree.ms.cts.repository.jpa.dao.querydsl.TransportOrderQry;
import com.dicfree.ms.cts.repository.jpa.entity.TransportOrder;
import com.dicfree.ms.cts.service.TransportOrderService;
import com.dicfree.ms.mct.common.dto.ex.ExMemberAddressDto;
import com.dicfree.ms.mct.service.MemberAddressService;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExTracingDto;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wiiyaya
 * @date 2023/11/28
 */
@Service("transportOrderService")
public class TransportOrderServiceImpl implements TransportOrderService {

    @Resource
    private TransportOrderDao transportOrderDao;

    @Resource
    private MemberAddressService memberAddressService;

    @Resource
    private QingFlowClient qingFlowClient;

    @Override
    public PageDto<ExTransportOrderDto> transportOrderPage(ExTransportOrderQueryDto transportOrderQueryDto, Pageable pageable) {
        Page<TransportOrder> transportOrderPage = transportOrderDao.findAll(TransportOrderQry.searchTransportOrder(transportOrderQueryDto), TransportOrderQry.searchTransportOrderOrder(pageable));

        List<ExTransportOrderDto> transportOrderDtoList = transportOrderPage.stream().map(transportOrder -> {
            ExTransportOrderDto transportOrderDto = new ExTransportOrderDto();
            //TODO replace this
            transportOrderDto = BeanUtilPlus.copyAs(transportOrder, ExTransportOrderDto.class);
            return transportOrderDto;
        }).collect(Collectors.toList());

        return new PageDto<>(transportOrderDtoList, transportOrderPage.getTotalElements());
    }

    @Override
    public List<ExTransportOrderDto> transportOrderList(Long memberId, TransportOrderStatus status) {
        return transportOrderDao.findAllByMemberIdAndStatus(memberId, status).stream()
                .map(transportOrder -> {
                    ExTransportOrderDto transportOrderDto = new ExTransportOrderDto();
                    transportOrderDto.setId(transportOrder.getId());
                    transportOrderDto.setWaybill(transportOrder.getWaybill());
                    transportOrderDto.setWeight(transportOrder.getWeight());
                    transportOrderDto.setLength(transportOrder.getLength());
                    transportOrderDto.setWidth(transportOrder.getWidth());
                    transportOrderDto.setHeight(transportOrder.getHeight());
                    transportOrderDto.setTransportMode(transportOrder.getTransportMode());
                    transportOrderDto.setTransportable(transportOrder.getTransportable());
                    transportOrderDto.setAmount(transportOrder.getAmount());
                    return transportOrderDto;
                }).toList();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void transportOrderAdd(ExTransportOrderDto transportOrderDto) throws BusinessException {
        //获取默认地址
        ExMemberAddressDto memberAddressDto = memberAddressService.memberAddressDefaultOne(transportOrderDto.getMemberId());

        TransportOrder transportOrder = new TransportOrder();
        transportOrder.setMemberId(transportOrderDto.getMemberId());
        transportOrder.setWaybill(transportOrderDto.getWaybill());
        transportOrder.setAmount(transportOrderDto.getAmount());
        transportOrder.setWeight(transportOrderDto.getWeight());
        transportOrder.setLength(transportOrderDto.getLength());
        transportOrder.setWidth(transportOrderDto.getWidth());
        transportOrder.setHeight(transportOrderDto.getHeight());
        transportOrder.setTransportable(transportOrderDto.getTransportable());
        transportOrder.setTransportMode(transportOrderDto.getTransportMode());
        transportOrder.setStatus(TransportOrderStatus.pending);
        transportOrder.setFirstInboundTime(null);
        transportOrder.setFirstDeliveryTime(null);
        transportOrder.setLastInboundTime(null);
        transportOrder.setLastDeliveryTime(null);
        transportOrder.setLastSigningTime(null);

        transportOrder.setName(memberAddressDto.getName());
        transportOrder.setPhone(memberAddressDto.getPhone());
        transportOrder.setProvince(memberAddressDto.getProvince());
        transportOrder.setCity(memberAddressDto.getCity());
        transportOrder.setAddress(memberAddressDto.getAddress());

        transportOrderDao.save(transportOrder);
    }

    @Override
    public ExTransportOrderDto transportOrderEditInit(Long transportOrderId) throws BusinessException {
        TransportOrder transportOrder = transportOrderDao.findById(transportOrderId).orElseThrow(() -> new BusinessException(CtsMessageConstant.ERROR_TRANSPORT_ORDER_NOT_FOUND));

        ExTransportOrderDto transportOrderDto = new ExTransportOrderDto();
        transportOrderDto.setId(transportOrder.getId());
        return transportOrderDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void transportOrderEdit(ExTransportOrderDto transportOrderDto) throws BusinessException {
        TransportOrder transportOrder = transportOrderDao.findByMemberIdAndWaybill(transportOrderDto.getMemberId(), transportOrderDto.getWaybill()).orElseThrow(() -> new BusinessException(CtsMessageConstant.ERROR_TRANSPORT_ORDER_NOT_FOUND));
        if (transportOrderDto.getAmount() != null) {
            transportOrder.setAmount(transportOrderDto.getAmount());
        }
        if (transportOrderDto.getTransportable() != null) {
            transportOrder.setTransportable(transportOrderDto.getTransportable());
        }
        switch (transportOrderDto.getStatus()) {
            case firstInbound -> transportOrder.setFirstInboundTime(transportOrderDto.getFirstInboundTime());
            case firstDelivery -> transportOrder.setFirstDeliveryTime(transportOrderDto.getFirstDeliveryTime());
            case lastInbound -> transportOrder.setLastInboundTime(transportOrderDto.getLastInboundTime());
            case lastDelivery -> transportOrder.setLastDeliveryTime(transportOrderDto.getLastDeliveryTime());
            case lastSigning -> transportOrder.setLastSigningTime(transportOrderDto.getLastSigningTime());
            default -> throw new BusinessException(CtsMessageConstant.ERROR_TRANSPORT_ORDER_STATUS_NOT_SUPPORT);
        }
        transportOrder.setStatus(transportOrderDto.getStatus());

        transportOrderDao.save(transportOrder);
    }

    @Override
    public ExTransportOrderDto transportOrderDetail(Long transportOrderId) throws BusinessException {
        TransportOrder transportOrder = transportOrderDao.findById(transportOrderId).orElseThrow(() -> new BusinessException(CtsMessageConstant.ERROR_TRANSPORT_ORDER_NOT_FOUND));

        ExTransportOrderDto transportOrderDto = new ExTransportOrderDto();
        //TODO replace this
        transportOrderDto = BeanUtilPlus.copyAs(transportOrder, ExTransportOrderDto.class);
        return transportOrderDto;
    }

    @Override
    public ExTransportOrderDto transportOrderInfo(Long memberId, String waybill) throws BusinessException {
        TransportOrder transportOrder = transportOrderDao.findByMemberIdAndWaybill(memberId, waybill).orElseThrow(() -> new BusinessException(CtsMessageConstant.ERROR_TRANSPORT_ORDER_NOT_FOUND));

        ExTransportOrderDto transportOrderDto = new ExTransportOrderDto();
        transportOrderDto.setId(transportOrder.getId());
        transportOrderDto.setTransportMode(transportOrder.getTransportMode());
        transportOrderDto.setAmount(transportOrder.getAmount());
        transportOrderDto.setTransportable(transportOrder.getTransportable());
        transportOrderDto.setFirstInboundTime(transportOrder.getFirstInboundTime());
        transportOrderDto.setFirstDeliveryTime(transportOrder.getFirstDeliveryTime());
        transportOrderDto.setLastInboundTime(transportOrder.getLastInboundTime());
        transportOrderDto.setLastDeliveryTime(transportOrder.getLastDeliveryTime());
        transportOrderDto.setLastSigningTime(transportOrder.getLastSigningTime());

        transportOrderDto.setName(transportOrder.getName());
        transportOrderDto.setPhone(transportOrder.getPhone());
        transportOrderDto.setProvince(transportOrder.getProvince());
        transportOrderDto.setCity(transportOrder.getCity());
        transportOrderDto.setAddress(transportOrder.getAddress());

        return transportOrderDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void transportOrderDelete(Long transportOrderId) throws BusinessException {
        TransportOrder transportOrder = transportOrderDao.findById(transportOrderId).orElseThrow(() -> new BusinessException(CtsMessageConstant.ERROR_TRANSPORT_ORDER_NOT_FOUND));
        transportOrderDao.delete(transportOrder);
    }

    @Override
    public ExTransportOrderDto transportOrderTrace(Long memberId, String waybill) throws BusinessException {
        TransportOrder transportOrder = transportOrderDao.findByMemberIdAndWaybill(memberId, waybill).orElseThrow(() -> new BusinessException(CtsMessageConstant.ERROR_TRANSPORT_ORDER_NOT_FOUND));

        List<ExTracingDto> tracingDtoList = qingFlowClient.queryWaybillTraceData(waybill);
        ExTransportOrderDto transportOrderDto = new ExTransportOrderDto();
        transportOrderDto.setWaybill(transportOrder.getWaybill());
        transportOrderDto.setTracingList(tracingDtoList);
        return transportOrderDto;
    }
}
