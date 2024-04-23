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
import com.dicfree.ms.cts.common.dto.ex.ExTransportOrderDto;
import com.dicfree.ms.cts.common.enums.TransportMode;
import com.dicfree.ms.mct.common.constant.MctMessageConstant;
import com.dicfree.ms.mct.common.dto.ex.ExMemberDto;
import com.dicfree.ms.mct.service.MemberService;
import com.dicfree.ms.se.common.dto.TransportOrderTraceCallbackReq;
import com.dicfree.ms.se.service.ThirdTransportOrderService;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ThirdTransportOrderAddReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ThirdTransportOrderTraceReq;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author wiiyaya
 * @date 2023/11/28
 */
@Service("thirdTransportOrderService")
public class ThirdTransportOrderServiceImpl implements ThirdTransportOrderService {

    @Resource
    private QingFlowClient qingFlowClient;

    @Resource
    private MemberService memberService;

    @Override
    public void transportOrderAdd(String supplier, ExTransportOrderDto transportOrderDto) throws BusinessException {
        ExMemberDto memberDto = memberService.memberInfoByNavigationNoWithNull(transportOrderDto.getXno());
        TransportMode transportMode = TransportMode.navigation;
        if(memberDto == null){
            memberDto = memberService.memberInfoByAviationNoWithNull(transportOrderDto.getXno());
            transportMode = TransportMode.aviation;
        }
        if(memberDto == null){
            throw new BusinessException(MctMessageConstant.ERROR_MEMBER_NOT_FOUND);
        }
        if(transportOrderDto.getTransportMode() != transportMode){
            throw new BusinessException(MctMessageConstant.ERROR_TRANSPORT_ORDER_TRANSPORT_MODE_NOT_MATCH);
        }

        ThirdTransportOrderAddReq request = new ThirdTransportOrderAddReq();
        request.setSupplier(supplier);
        request.setMemberId(memberDto.getId());
        request.setProvince(transportOrderDto.getProvince());
        request.setCity(transportOrderDto.getCity());
        request.setAddress(transportOrderDto.getAddress());
        request.setWaybill(transportOrderDto.getWaybill());
        request.setPhone(transportOrderDto.getPhone());
        request.setWeight(transportOrderDto.getWeight());
        request.setLength(transportOrderDto.getLength());
        request.setWidth(transportOrderDto.getWidth());
        request.setHeight(transportOrderDto.getHeight());
        request.setTransportMode(transportMode.name());
        request.setTransportable(transportOrderDto.getTransportable());

        qingFlowClient.thirdTransportOrderAddNotice(request);
    }

    @Override
    public void transportOrderTraceCallback(String supplier, TransportOrderTraceCallbackReq callbackReq) throws BusinessException {
        ThirdTransportOrderTraceReq request = new ThirdTransportOrderTraceReq();
        request.setStatus(callbackReq.getStatus());
        request.setSupplier(supplier);
        //request.setWaybill(callbackReq.getWaybill());
        request.setLocation(callbackReq.getLocation());
        request.setTimeStamp(callbackReq.getTimeStamp());
        request.setStatusCode(callbackReq.getStatusCode());
        qingFlowClient.thirdTransportOrderTraceNotice(request);
    }
}
