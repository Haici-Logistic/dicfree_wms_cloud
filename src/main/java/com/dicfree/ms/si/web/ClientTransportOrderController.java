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
import cn.jzyunqi.common.model.spring.security.LoginUserDto;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import cn.jzyunqi.common.utils.CurrentUserUtils;
import com.dicfree.ms.ClientRestBaseController;
import com.dicfree.ms.cts.common.dto.ex.ExTransportOrderDto;
import com.dicfree.ms.cts.common.enums.TransportOrderStatus;
import com.dicfree.ms.si.service.ClientTransportOrderService;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 客户端 - 转运订单接口
 *
 * @author wiiyaya
 * @date 2023/11/28
 */
@RestController
@Validated
public class ClientTransportOrderController extends ClientRestBaseController {

    @Resource
    private ClientTransportOrderService clientTransportOrderService;

    /**
     * 转运订单新增
     *
     * @param transportOrderDto 转运订单信息
     * @param bindingResult 校验信息
     */
    @PostMapping(value = "/client/transportOrder/add")
    public String transportOrderAdd(@RequestBody @Validated(ExTransportOrderDto.ClientAdd.class) ExTransportOrderDto transportOrderDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, transportOrderDto, ExTransportOrderDto.ClientAdd.class);
        clientTransportOrderService.transportOrderAdd(transportOrderDto);
        return "success";
    }

    /**
     * 转运订单编辑
     *
     * @param transportOrderDto 转运订单信息
     * @param bindingResult 校验信息
     */
    @PostMapping(value = "/client/transportOrder/edit")
    public String transportOrderEdit(@RequestBody @Validated(ExTransportOrderDto.ClientEdit.class) ExTransportOrderDto transportOrderDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, transportOrderDto, ExTransportOrderDto.ClientEdit.class);
        clientTransportOrderService.transportOrderEdit(transportOrderDto);
        return "success";
    }

    /**
     * 转运订单详情
     *
     * @param unionId 微信unionId
     */
    @PostMapping(value = "/client/transportOrder/info")
    public ExTransportOrderDto transportOrderInfo(@RequestParam @NotBlank String unionId, @RequestParam @NotBlank String waybill) throws BusinessException {
        return clientTransportOrderService.transportOrderInfo(unionId, waybill);
    }

    /**
     * 转运订单详情
     *
     * @param unionId 微信unionId
     */
    @PostMapping(value = "/client/transportOrder/list")
    public List<ExTransportOrderDto> transportOrderList(@RequestParam @NotBlank String unionId, @RequestParam @NotNull TransportOrderStatus status) throws BusinessException {
        return clientTransportOrderService.transportOrderList(unionId, status);
    }

    /**
     * 转运订单跟踪信息
     *
     * @param waybill 订单信息
     * @return 跟踪信息
     */
    @PostMapping(value = "/client/transportOrder/trace")
    public ExTransportOrderDto transportOrderTrace(@RequestParam @NotBlank String unionId, @RequestParam @NotBlank String waybill) throws BusinessException {
        return clientTransportOrderService.transportOrderTrace(unionId, waybill);
    }
}
