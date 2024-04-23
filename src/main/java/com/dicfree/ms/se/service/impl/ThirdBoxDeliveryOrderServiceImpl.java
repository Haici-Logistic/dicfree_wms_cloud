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
package com.dicfree.ms.se.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import com.dicfree.ms.se.service.ThirdBoxDeliveryOrderService;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExBoxDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.qingflow.DeliveryAddressData;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ThirdBoxDeliveryOrderAddReq;
import com.dicfree.ms.wms.service.BoxDeliveryOrderService;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/13
 */
@Service("thirdBoxDeliveryOrderService")
public class ThirdBoxDeliveryOrderServiceImpl implements ThirdBoxDeliveryOrderService {

    @Resource
    private QingFlowClient qingFlowClient;

    @Resource
    private BoxDeliveryOrderService boxDeliveryOrderService;

    @Override
    public void boxDeliveryOrderPublicAdd(ExBoxDeliveryOrderDto boxDeliveryOrderDto) throws BusinessException {
        List<DeliveryAddressData> deliveryAddressDataList = qingFlowClient.queryPublicAddressList(boxDeliveryOrderDto.getSupplier(), null, boxDeliveryOrderDto.getDeliveryTo());
        if (CollectionUtilPlus.Collection.isEmpty(deliveryAddressDataList)) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_QUERY_ADDRESS_FAILED, boxDeliveryOrderDto.getAddressInfo());
        }
        DeliveryAddressData deliveryAddressData = deliveryAddressDataList.get(0);

        //数据转换
        ThirdBoxDeliveryOrderAddReq request = new ThirdBoxDeliveryOrderAddReq();
        request.setSupplier(boxDeliveryOrderDto.getSupplier());
        request.setThirdBoxDeliveryOrder(boxDeliveryOrderDto.getThirdOrderNo());
        request.setDeliveryDate(boxDeliveryOrderDto.getDeliveryDate());
        request.setAppointmentId(boxDeliveryOrderDto.getAppointmentId());

        request.setDeliveryTo(boxDeliveryOrderDto.getDeliveryTo());
        request.setName(deliveryAddressData.getName());
        request.setPhone1(deliveryAddressData.getPhone1());
        request.setPhone2(deliveryAddressData.getPhone2());
        request.setCountry(deliveryAddressData.getCountry());
        request.setProvince(deliveryAddressData.getProvince());
        request.setCity(deliveryAddressData.getCity());
        request.setAddress(deliveryAddressData.getAddress());

        request.setDeliveryOrderItemList(boxDeliveryOrderDto.getBoxDeliveryOrderItemList().stream().map(boxDeliveryOrderItemDto -> {
            ThirdBoxDeliveryOrderAddReq.DeliveryOrderItem deliveryOrderItem = new ThirdBoxDeliveryOrderAddReq.DeliveryOrderItem();
            deliveryOrderItem.setSupplierBoxCode(boxDeliveryOrderItemDto.getSupplierBoxCode());
            deliveryOrderItem.setPcs(boxDeliveryOrderItemDto.getTotalCount());
            return deliveryOrderItem;
        }).toList());
        qingFlowClient.thirdBoxDeliveryOrderAddNotice(request);
    }

    @Override
    public void boxDeliveryOrderPrivateAdd(ExBoxDeliveryOrderDto boxDeliveryOrderDto) throws BusinessException {
        //数据转换
        ThirdBoxDeliveryOrderAddReq request = new ThirdBoxDeliveryOrderAddReq();
        request.setSupplier(boxDeliveryOrderDto.getSupplier());
        request.setThirdBoxDeliveryOrder(boxDeliveryOrderDto.getThirdOrderNo());
        request.setDeliveryDate(boxDeliveryOrderDto.getDeliveryDate());
        request.setAppointmentId(boxDeliveryOrderDto.getAppointmentId());

        request.setName(boxDeliveryOrderDto.getName());
        request.setPhone1(boxDeliveryOrderDto.getPhone1());
        request.setPhone2(boxDeliveryOrderDto.getPhone2());
        request.setCountry(boxDeliveryOrderDto.getCountry());
        request.setProvince(boxDeliveryOrderDto.getProvince());
        request.setCity(boxDeliveryOrderDto.getCity());
        request.setAddress(boxDeliveryOrderDto.getAddress());

        request.setDeliveryOrderItemList(boxDeliveryOrderDto.getBoxDeliveryOrderItemList().stream().map(boxDeliveryOrderItemDto -> {
            ThirdBoxDeliveryOrderAddReq.DeliveryOrderItem deliveryOrderItem = new ThirdBoxDeliveryOrderAddReq.DeliveryOrderItem();
            deliveryOrderItem.setSupplierBoxCode(boxDeliveryOrderItemDto.getSupplierBoxCode());
            deliveryOrderItem.setPcs(boxDeliveryOrderItemDto.getTotalCount());
            return deliveryOrderItem;
        }).toList());
        qingFlowClient.thirdBoxDeliveryOrderAddNotice(request);
    }

    @Override
    public ExBoxDeliveryOrderDto boxDeliveryOrderInfo(String supplier, String thirdOrderNo) throws BusinessException {
        return boxDeliveryOrderService.boxDeliveryOrderInfo(supplier, thirdOrderNo);
    }
}
