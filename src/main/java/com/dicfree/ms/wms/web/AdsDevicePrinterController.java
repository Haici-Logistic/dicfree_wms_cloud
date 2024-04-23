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
import cn.jzyunqi.common.utils.CurrentUserUtils;
import cn.jzyunqi.ms.AdsRestBaseController;
import com.dicfree.ms.wms.common.dto.ex.ExDevicePrinterDto;
import com.dicfree.ms.wms.common.enums.DevicePrinterStatus;
import com.dicfree.ms.wms.service.DevicePrinterService;
import jakarta.annotation.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Portal - 超级管理员 - 打印机接口
 *
 * @author wiiyaya
 * @date 2023/07/17
 */
@RestController
@Validated
public class AdsDevicePrinterController extends AdsRestBaseController {

    @Resource
    private DevicePrinterService devicePrinterService;

    /**
     * 账号绑定打印机
     *
     * @param devicePrinterDto 打印机信息
     */
    @PostMapping(value = "/ads/devicePrinter/bind")
    public void devicePrinterBind(@RequestBody @Validated(ExDevicePrinterDto.Add.class) ExDevicePrinterDto devicePrinterDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, devicePrinterDto, ExDevicePrinterDto.Add.class);
        devicePrinterService.devicePrinterBind(devicePrinterDto);
    }

    /**
     * 查看打印机基本信息
     */
    @PostMapping(value = "/ads/devicePrinter/info")
    public ExDevicePrinterDto devicePrinterInfo() throws BusinessException {
        //获取了当前用户id
        Long currentUserId = CurrentUserUtils.currentUserId();
        //这里是打印机状态清除的逻辑
        return devicePrinterService.devicePrinterInfo(currentUserId);
    }

    /**
     * 查询打印机当前状态
     *
     * @return 打印机状态
     */
    @PostMapping(value = "/ads/devicePrinter/status")
    public DevicePrinterStatus devicePrinterStatus() throws BusinessException {
        //获取了当前用户id
        Long currentUserId = CurrentUserUtils.currentUserId();
        //只能查看当前用户绑定的打印机状态
        return devicePrinterService.devicePrinterStatus(currentUserId);
    }

    /**
     * 清空打印队列
     */
    @PostMapping(value = "/ads/devicePrinter/clear")
    public void devicePrinterClear() throws BusinessException {
        //获取了当前用户id
        Long currentUserId = CurrentUserUtils.currentUserId();
        //这里是打印机状态清除的逻辑
        devicePrinterService.devicePrinterClear(currentUserId);
    }
}
