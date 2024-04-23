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
import com.dicfree.ms.se.service.ThirdBoxArrivalOrderService;
import com.dicfree.ms.wms.common.dto.ex.ExBoxArrivalOrderDto;
import jakarta.annotation.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 外部系统 - 整装箱入库订单接口
 *
 * @author wiiyaya
 * @date 2023/09/04
 */
@RestController
@Validated
public class ThirdBoxArrivalOrderController extends ThirdRestBaseController {

    @Resource
    private ThirdBoxArrivalOrderService thirdBoxArrivalOrderService;

    /**
     * 整装箱入库订单新增
     *
     * @param boxArrivalOrderDto 整装箱入库订单信息
     */
    @PostMapping(value = "/third/boxArrivalOrder/add")
    public String boxArrivalOrderAdd(@RequestBody @Validated(ExBoxArrivalOrderDto.ThirdAdd.class) ExBoxArrivalOrderDto boxArrivalOrderDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, boxArrivalOrderDto, ExBoxArrivalOrderDto.ThirdAdd.class);
        LoginUserDto currentThird = CurrentUserUtils.currentUser();
        boxArrivalOrderDto.setSupplier(currentThird.getUsername());
        thirdBoxArrivalOrderService.boxArrivalOrderAdd(boxArrivalOrderDto);
        return "success";
    }

    /**
     * 查询入库订单信息
     *
     * @param boxArrivalOrderDto 入库订单信息
     * @param bindingResult      校验结果
     * @return 入库订单信息
     */
    @PostMapping(value = "/third/boxArrivalOrder/info")
    public ExBoxArrivalOrderDto boxArrivalOrderInfo(@RequestBody @Validated(ExBoxArrivalOrderDto.ThirdInfo.class) ExBoxArrivalOrderDto boxArrivalOrderDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, boxArrivalOrderDto, ExBoxArrivalOrderDto.ThirdInfo.class);
        LoginUserDto currentThird = CurrentUserUtils.currentUser();
        return thirdBoxArrivalOrderService.boxArrivalOrderInfo(currentThird.getUsername(), boxArrivalOrderDto.getThirdOrderNo());
    }
}
