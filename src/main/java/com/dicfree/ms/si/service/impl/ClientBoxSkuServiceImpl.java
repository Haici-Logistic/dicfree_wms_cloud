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
import com.dicfree.ms.si.service.ClientBoxSkuService;
import com.dicfree.ms.si.web.listener.SiMessageListener;
import com.dicfree.ms.wms.common.constant.WmsEmitConstant;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSkuDto;
import com.dicfree.ms.wms.service.BoxSkuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author wiiyaya
 * @date 2023/8/27
 */
@Service("clientBoxSkuService")
public class ClientBoxSkuServiceImpl implements ClientBoxSkuService {

    @Resource
    private BoxSkuService boxSkuService;

    @Override
    public ExBoxSkuDto boxSkuAdd(ExBoxSkuDto boxSkuDto) throws BusinessException {
        return boxSkuService.boxSkuAdd(boxSkuDto);
    }

    @Override
    public void boxSkuDeliveryEdit(String boxSkuCode, String sortingTo, String deliveryTo) throws BusinessException {
        boxSkuService.boxSkuDeliveryEdit(boxSkuCode, sortingTo, deliveryTo);
    }

    @Override
    @EmitEvent(SiEmitConstant.SI_CLIENT_BOX_ARRIVAL_ORDER_SERVICE_LOCATION_EDIT)
    public void boxSkuLocationEdit(String boxSkuCode, String location) throws BusinessException {
        boxSkuService.boxSkuLocationEdit(boxSkuCode, location);
    }
}
