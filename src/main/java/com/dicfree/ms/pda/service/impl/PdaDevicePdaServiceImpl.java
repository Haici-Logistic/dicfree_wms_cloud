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
package com.dicfree.ms.pda.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.pda.service.PdaDevicePdaService;
import com.dicfree.ms.wms.common.dto.ex.ExDevicePdaDto;
import com.dicfree.ms.wms.service.DevicePdaService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author wiiyaya
 * @date 2023/10/27
 */
@Service("pdaDevicePdaService")
public class PdaDevicePdaServiceImpl implements PdaDevicePdaService {

    @Resource
    private DevicePdaService devicePdaService;

    @Override
    public void devicePdaActive(String code) {
        devicePdaService.devicePdaActive(code);
    }

    @Override
    public ExDevicePdaDto devicePdaInfo(String code) throws BusinessException {
        return devicePdaService.devicePdaInfo(code);
    }
}
