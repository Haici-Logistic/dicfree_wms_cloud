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
import cn.jzyunqi.common.support.spring.aop.event.EmitEvent;
import com.dicfree.ms.si.common.constant.SiEmitConstant;
import com.dicfree.ms.si.service.ClientBoxArrivalOrderService;
import com.dicfree.ms.wms.common.dto.ex.ExBoxArrivalOrderDto;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import com.dicfree.ms.wms.service.BoxArrivalOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
@Service("clientBoxArrivalOrderService")
public class ClientBoxArrivalOrderServiceImpl implements ClientBoxArrivalOrderService {

    @Resource
    private BoxArrivalOrderService boxArrivalOrderService;

    @Override
    @EmitEvent(SiEmitConstant.SI_CLIENT_BOX_ARRIVAL_ORDER_SERVICE_BOX_ARRIVAL_ORDER_ADD)
    public ExBoxArrivalOrderDto boxArrivalOrderAdd(ExBoxArrivalOrderDto boxArrivalOrderDto) throws BusinessException {
        return boxArrivalOrderService.boxArrivalOrderAdd(boxArrivalOrderDto);
    }

    @Override
    public void boxArrivalOrderStatusEdit(String uniqueNo, String boxSkuCode, OrderStatus status) throws BusinessException {
        boxArrivalOrderService.boxArrivalOrderStatusEdit(uniqueNo, boxSkuCode, status);
    }
}
