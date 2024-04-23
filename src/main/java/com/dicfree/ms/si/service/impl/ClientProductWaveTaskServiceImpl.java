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
import com.dicfree.ms.si.service.ClientProductWaveTaskService;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductWaveTaskDto;
import com.dicfree.ms.wms.service.DevicePrinterService;
import com.dicfree.ms.wms.service.ProductWaveTaskService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2024/1/20
 */
@Service("clientProductWaveTaskService")
public class ClientProductWaveTaskServiceImpl implements ClientProductWaveTaskService {

    @Resource
    private ProductWaveTaskService productWaveTaskService;

    @Override
    public void waveTaskGenerate(String username, ExProductWaveTaskDto productWaveTaskDto) throws BusinessException {
        // 生成波次单
        productWaveTaskService.waveTaskGenerate(productWaveTaskDto);
        // 打印波次单
        //String devicePrinterSn = devicePrinterService.devicePrinterSn(username);
        //for (ExProductDeliveryOrderDto productDeliveryOrderDto : productWaveTaskDto.getProductDeliveryOrderList()) {
        //    Map<String, Object> params = new HashMap<>();
        //    params.put("thirdOrderNo", productDeliveryOrderDto.getThirdOrderNo());
        //    params.put("waybill", productDeliveryOrderDto.getWaybill());
        //    devicePrinterService.printTemplate(devicePrinterSn, "waybillTemplate.ftl", params);
        //}
    }

    @Override
    public void waveTaskCollectionDone(ExProductWaveTaskDto productWaveTaskDto) throws BusinessException {
        productWaveTaskService.productWaveTaskCollectionDone(productWaveTaskDto.getUniqueNo());
    }
}
