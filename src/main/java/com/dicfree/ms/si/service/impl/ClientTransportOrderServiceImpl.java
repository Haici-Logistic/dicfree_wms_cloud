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
package com.dicfree.ms.si.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.ms.uaa.common.enums.AuthType;
import cn.jzyunqi.ms.uaa.service.UserAuthService;
import com.dicfree.ms.cts.common.dto.ex.ExTransportOrderDto;
import com.dicfree.ms.cts.common.enums.TransportOrderStatus;
import com.dicfree.ms.cts.service.TransportOrderService;
import com.dicfree.ms.si.service.ClientTransportOrderService;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/28
 */
@Service("clientTransportOrderService")
public class ClientTransportOrderServiceImpl implements ClientTransportOrderService {

    @Resource
    private TransportOrderService transportOrderService;

    @Resource
    private UserAuthService userAuthService;

    @Override
    public void transportOrderAdd(ExTransportOrderDto transportOrderDto) throws BusinessException {
        transportOrderService.transportOrderAdd(transportOrderDto);
    }

    @Override
    public void transportOrderEdit(ExTransportOrderDto transportOrderDto) throws BusinessException {
        transportOrderService.transportOrderEdit(transportOrderDto);
    }

    @Override
    public ExTransportOrderDto transportOrderInfo(String unionId, String waybill) throws BusinessException {
        Long memberId = userAuthService.findIdByUsername(AuthType.WX_OPEN, unionId);
        return transportOrderService.transportOrderInfo(memberId, waybill);
    }

    @Override
    public List<ExTransportOrderDto> transportOrderList(String unionId, TransportOrderStatus status) throws BusinessException {
        Long memberId = userAuthService.findIdByUsername(AuthType.WX_OPEN, unionId);
        return transportOrderService.transportOrderList(memberId, status);
    }

    @Override
    public ExTransportOrderDto transportOrderTrace(String unionId, String waybill) throws BusinessException {
        Long memberId = userAuthService.findIdByUsername(AuthType.WX_OPEN, unionId);
        return transportOrderService.transportOrderTrace(memberId, waybill);
    }
}
