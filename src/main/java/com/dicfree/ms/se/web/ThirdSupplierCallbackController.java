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
package com.dicfree.ms.se.web;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.spring.security.LoginUserDto;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import cn.jzyunqi.common.utils.CurrentUserUtils;
import com.dicfree.ms.ThirdRestBaseController;
import com.dicfree.ms.cts.common.dto.ex.ExTransportOrderDto;
import com.dicfree.ms.se.common.dto.BoxArrivalOrderTraceCallbackReq;
import com.dicfree.ms.se.common.dto.TransportOrderTraceCallbackReq;
import com.dicfree.ms.se.service.ThirdBoxArrivalOrderService;
import com.dicfree.ms.se.service.ThirdTransportOrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 外部系统 - 供应商回调
 *
 * @author wiiyaya
 * @date 2023/11/14
 */
@RestController
@Validated
@Slf4j
public class ThirdSupplierCallbackController extends ThirdRestBaseController {

    @Resource
    private ThirdBoxArrivalOrderService thirdBoxArrivalOrderService;

    @Resource
    private ThirdTransportOrderService thirdTransportOrderService;

    /**
     * 入库订单到货状态回调
     *
     * @param callbackReq 回调信息
     * @param bindingResult            校验结果
     * @return 回调结果
     */
    @PostMapping(value = {"/third/supplier/boxArrivalOrder/traceCallback","/third/boxArrivalOrder/arrivalStatusCallback"})
    public String boxArrivalOrderTraceCallback(@RequestBody @Validated(BoxArrivalOrderTraceCallbackReq.Callback.class) BoxArrivalOrderTraceCallbackReq callbackReq, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, callbackReq, BoxArrivalOrderTraceCallbackReq.Callback.class);
        LoginUserDto currentThird = CurrentUserUtils.currentUser();
        thirdBoxArrivalOrderService.boxArrivalOrderTraceCallback(currentThird.getUsername(), callbackReq.getThirdOrderNo(), callbackReq.getArrivalStatus());
        return "success";
    }

    /**
     * 转运订单跟踪回调
     *
     * @param callbackReq 转运订单信息
     * @param bindingResult 校验信息
     */
    @PostMapping(value = "/third/supplier/transportOrder/traceCallback")
    public void transportOrderTraceCallback(@RequestBody @Validated(TransportOrderTraceCallbackReq.Callback.class) TransportOrderTraceCallbackReq callbackReq, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, callbackReq, TransportOrderTraceCallbackReq.Callback.class);
        LoginUserDto currentThird = CurrentUserUtils.currentUser();
        thirdTransportOrderService.transportOrderTraceCallback(currentThird.getUsername(), callbackReq);
    }
}
