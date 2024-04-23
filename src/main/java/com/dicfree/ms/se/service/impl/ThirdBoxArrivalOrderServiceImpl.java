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
import com.dicfree.ms.se.service.ThirdBoxArrivalOrderService;
import com.dicfree.ms.wms.common.dto.ex.ExBoxArrivalOrderDto;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ThirdBoxArrivalOrderAddReq;
import com.dicfree.ms.wms.service.BoxArrivalOrderService;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author wiiyaya
 * @date 2023/11/13
 */
@Service("thirdBoxArrivalOrderService")
public class ThirdBoxArrivalOrderServiceImpl implements ThirdBoxArrivalOrderService {

    @Resource
    private QingFlowClient qingFlowClient;

    @Resource
    private BoxArrivalOrderService boxArrivalOrderService;

    @Override
    public void boxArrivalOrderAdd(ExBoxArrivalOrderDto boxArrivalOrderDto) throws BusinessException {
        ThirdBoxArrivalOrderAddReq request = new ThirdBoxArrivalOrderAddReq();
        request.setSupplier(boxArrivalOrderDto.getSupplier());
        request.setThirdBoxArrivalOrder(boxArrivalOrderDto.getThirdOrderNo());
        request.setContainerNo(boxArrivalOrderDto.getContainerNoReal());
        request.setArrivalOrderItemList(boxArrivalOrderDto.getBoxArrivalOrderItemList().stream().map(boxArrivalOrderItemDto -> {
            ThirdBoxArrivalOrderAddReq.ArrivalOrderItem arrivalOrderItem = new ThirdBoxArrivalOrderAddReq.ArrivalOrderItem();
            arrivalOrderItem.setSupplierBoxCode(boxArrivalOrderItemDto.getSupplierBoxCode());
            arrivalOrderItem.setPcs(boxArrivalOrderItemDto.getTotalCount());
            arrivalOrderItem.setWeight(boxArrivalOrderItemDto.getWeight());
            arrivalOrderItem.setVolume(boxArrivalOrderItemDto.getVolume());
            arrivalOrderItem.setBsku(boxArrivalOrderItemDto.getBoxSkuName());
            arrivalOrderItem.setDeliveryTo(boxArrivalOrderItemDto.getDeliveryTo());
            return arrivalOrderItem;
        }).toList());
        qingFlowClient.thirdBoxArrivalOrderAddNotice(request);
    }

    @Override
    public ExBoxArrivalOrderDto boxArrivalOrderInfo(String supplier, String thirdOrderNo) throws BusinessException {
        return boxArrivalOrderService.boxArrivalOrderInfo(supplier, thirdOrderNo);
    }

    @Override
    public void boxArrivalOrderTraceCallback(String supplier, String thirdOrderNo, String arrivalStatus) throws BusinessException {
        if ("success".equals(arrivalStatus)) {
            boxArrivalOrderService.boxArrivalOrderArrival(supplier, thirdOrderNo);
        }
    }
}
