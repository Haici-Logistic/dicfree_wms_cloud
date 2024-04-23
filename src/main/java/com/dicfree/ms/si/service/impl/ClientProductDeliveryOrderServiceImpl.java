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
import com.dicfree.ms.si.service.ClientProductDeliveryOrderService;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.service.DevicePrinterService;
import com.dicfree.ms.wms.service.ProductDeliveryOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2023/11/2
 */
@Service("clientProductDeliveryOrderService")
public class ClientProductDeliveryOrderServiceImpl implements ClientProductDeliveryOrderService {

    @Resource
    private ProductDeliveryOrderService productDeliveryOrderService;

    @Resource
    private DevicePrinterService devicePrinterService;

    @Override
    public Long productDeliveryOrderAdd(ExProductDeliveryOrderDto productDeliveryOrderDto) throws BusinessException {
        return productDeliveryOrderService.productDeliveryOrderAdd(productDeliveryOrderDto);
    }

    @Override
    public void productDeliveryOrderTraceCallback(String courier, String waybill, Object callbackParams) throws BusinessException {
        productDeliveryOrderService.productDeliveryOrderTraceCallback(courier, waybill, callbackParams);
    }
}
