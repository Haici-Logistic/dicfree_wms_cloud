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
package com.dicfree.ms.wms.web;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import cn.jzyunqi.ms.AdsRestBaseController;
import com.dicfree.ms.wms.common.dto.ex.ExDevicePdaDto;
import com.dicfree.ms.wms.service.DevicePdaService;
import jakarta.annotation.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Portal - 超级管理员 - PDA接口
 *
 * @author wiiyaya
 * @date 2023/10/25
 */
@RestController
@Validated
public class AdsDevicePdaController extends AdsRestBaseController {

    @Resource
    private DevicePdaService devicePdaService;

    /**
     * 账号绑定PDA能力
     *
     * @param devicePdaDto 设备信息
     */
    @PostMapping(value = "/ads/devicePda/bind")
    public void devicePdaBind(@RequestBody @Validated(ExDevicePdaDto.Add.class) ExDevicePdaDto devicePdaDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, devicePdaDto, ExDevicePdaDto.Add.class);
        devicePdaService.devicePdaBind(devicePdaDto);
    }
}
