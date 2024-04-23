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
import com.dicfree.ms.se.service.ThirdBoxDeliveryOrderService;
import com.dicfree.ms.wms.common.dto.ex.ExBoxDeliveryOrderDto;
import jakarta.annotation.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 外部系统 - 整装箱出库订单接口
 *
 * @author wiiyaya
 * @date 2023/09/02
 */
@RestController
@Validated
public class ThirdBoxDeliveryOrderController extends ThirdRestBaseController {

    @Resource
    private ThirdBoxDeliveryOrderService thirdBoxDeliveryOrderService;

    /**
     * 整装箱出库订单新增(公共地址)
     *
     * @param boxDeliveryOrderDto 整装箱出库订单信息
     */
    @PostMapping(value = "/third/boxDeliveryOrder/publicAdd")
    public String boxDeliveryOrderPublicAdd(@RequestBody @Validated(ExBoxDeliveryOrderDto.ThirdPublicAdd.class) ExBoxDeliveryOrderDto boxDeliveryOrderDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, boxDeliveryOrderDto, ExBoxDeliveryOrderDto.ThirdPublicAdd.class);
        LoginUserDto currentThird = CurrentUserUtils.currentUser();
        boxDeliveryOrderDto.setSupplier(currentThird.getUsername());
        thirdBoxDeliveryOrderService.boxDeliveryOrderPublicAdd(boxDeliveryOrderDto);
        return "success";
    }

    /**
     * 整装箱出库订单新增(私有地址)
     *
     * @param boxDeliveryOrderDto 整装箱出库订单信息
     */
    @PostMapping(value = "/third/boxDeliveryOrder/privateAdd")
    public String boxDeliveryOrderPrivateAdd(@RequestBody @Validated(ExBoxDeliveryOrderDto.ThirdPrivateAdd.class) ExBoxDeliveryOrderDto boxDeliveryOrderDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, boxDeliveryOrderDto, ExBoxDeliveryOrderDto.ThirdPrivateAdd.class);
        LoginUserDto currentThird = CurrentUserUtils.currentUser();
        boxDeliveryOrderDto.setSupplier(currentThird.getUsername());
        thirdBoxDeliveryOrderService.boxDeliveryOrderPrivateAdd(boxDeliveryOrderDto);
        return "success";
    }

    /**
     * 整装箱出库订单新增(轻流)
     *
     * @param boxDeliveryOrderDto 整装箱出库订单信息
     * @return 订单号
     */
    @PostMapping(value = "/third/boxDeliveryOrder/info")
    public ExBoxDeliveryOrderDto boxDeliveryOrderInfo(@RequestBody @Validated(ExBoxDeliveryOrderDto.ThirdInfo.class) ExBoxDeliveryOrderDto boxDeliveryOrderDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, boxDeliveryOrderDto, ExBoxDeliveryOrderDto.ThirdInfo.class);
        LoginUserDto currentThird = CurrentUserUtils.currentUser();
        return thirdBoxDeliveryOrderService.boxDeliveryOrderInfo(currentThird.getUsername(), boxDeliveryOrderDto.getThirdOrderNo());
    }

}
