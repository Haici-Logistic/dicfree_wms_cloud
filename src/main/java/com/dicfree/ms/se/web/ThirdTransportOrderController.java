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
import com.dicfree.ms.se.service.ThirdTransportOrderService;
import jakarta.annotation.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 外部系统 - 转运订单接口
 *
 * @author wiiyaya
 * @date 2023/11/28
 */
@RestController
@Validated
public class ThirdTransportOrderController extends ThirdRestBaseController {

    @Resource
    private ThirdTransportOrderService thirdTransportOrderService;

    /**
     * 转运订单新增
     *
     * @param transportOrderDto 转运订单信息
     * @param bindingResult 校验信息
     */
    @PostMapping(value = "/third/transportOrder/add")
    public void transportOrderAdd(@RequestBody @Validated(ExTransportOrderDto.ThirdAdd.class) ExTransportOrderDto transportOrderDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, transportOrderDto, ExTransportOrderDto.ThirdAdd.class);
        LoginUserDto currentThird = CurrentUserUtils.currentUser();
        thirdTransportOrderService.transportOrderAdd(currentThird.getUsername(), transportOrderDto);
    }
}
