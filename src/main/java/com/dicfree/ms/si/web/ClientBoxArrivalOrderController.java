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
package com.dicfree.ms.si.web;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import com.dicfree.ms.ClientRestBaseController;
import com.dicfree.ms.si.service.ClientBoxArrivalOrderService;
import com.dicfree.ms.wms.common.dto.ex.ExBoxArrivalOrderDto;
import jakarta.annotation.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 客户端 - 整装箱入库订单接口
 *
 * @author wiiyaya
 * @date 2023/09/04
 */
@RestController
@Validated
public class ClientBoxArrivalOrderController extends ClientRestBaseController {

    @Resource
    private ClientBoxArrivalOrderService clientBoxArrivalOrderService;

    /**
     * 整装箱入库订单新增
     *
     * @param boxArrivalOrderDto 整装箱入库订单信息
     * @param bindingResult 校验信息
     */
    @PostMapping(value = "/client/boxArrivalOrder/add")
    public String boxArrivalOrderAdd(@RequestBody @Validated(ExBoxArrivalOrderDto.Add.class) ExBoxArrivalOrderDto boxArrivalOrderDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, boxArrivalOrderDto, ExBoxArrivalOrderDto.Add.class);
        return clientBoxArrivalOrderService.boxArrivalOrderAdd(boxArrivalOrderDto).getUniqueNo();
    }

    /**
     * 整装箱入库订单修改
     *
     * @param boxArrivalOrderDto 整装箱入库订单信息
     * @param bindingResult 校验信息
     */
    @PostMapping(value = "/client/boxArrivalOrder/statusEdit")
    public String boxArrivalOrderStatusEdit(@RequestBody @Validated(ExBoxArrivalOrderDto.StatusEdit.class) ExBoxArrivalOrderDto boxArrivalOrderDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, boxArrivalOrderDto, ExBoxArrivalOrderDto.StatusEdit.class);
        clientBoxArrivalOrderService.boxArrivalOrderStatusEdit(boxArrivalOrderDto.getUniqueNo(), boxArrivalOrderDto.getBoxSkuCode(), boxArrivalOrderDto.getInboundStatus());
        return "success";
    }

}
