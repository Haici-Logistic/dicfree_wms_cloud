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
import com.dicfree.ms.si.service.ClientBoxSkuService;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSkuDto;
import jakarta.annotation.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端 - 整装箱管理接口
 *
 * @author wiiyaya
 * @date 2023/07/17
 */
@RestController
@Validated
public class ClientBoxSkuController extends ClientRestBaseController {

    @Resource
    private ClientBoxSkuService clientBoxSkuService;

    /**
     * 整装箱新增
     *
     * @param boxSkuDto 整装箱新增信息
     * @return 随机取件码
     */
    @PostMapping(value = "/client/boxSku/add")
    public String boxSkuAdd(@RequestBody @Validated(ExBoxSkuDto.Add.class) ExBoxSkuDto boxSkuDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, boxSkuDto, ExBoxSkuDto.Add.class);
        return clientBoxSkuService.boxSkuAdd(boxSkuDto).getPickUpCode();
    }

    /**
     * 修改整装箱收货地
     *
     * @param boxSkuDto 整装箱收货信息
     * @return 成功
     */
    @PostMapping(value = "/client/boxSku/deliveryEdit")
    public String boxSkuDeliveryEdit(@RequestBody @Validated(ExBoxSkuDto.DeliveryEdit.class) ExBoxSkuDto boxSkuDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, boxSkuDto, ExBoxSkuDto.DeliveryEdit.class);
        clientBoxSkuService.boxSkuDeliveryEdit(boxSkuDto.getCode(), boxSkuDto.getSortingTo(), boxSkuDto.getDeliveryTo());
        return "success";
    }

    /**
     * 修改整装箱货架
     *
     * @param boxSkuDto 整装箱货架信息
     * @return 成功
     */
    @PostMapping(value = "/client/boxSku/locationEdit")
    public String boxSkuLocationEdit(@RequestBody @Validated(ExBoxSkuDto.LocationEdit.class) ExBoxSkuDto boxSkuDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, boxSkuDto, ExBoxSkuDto.LocationEdit.class);
        clientBoxSkuService.boxSkuLocationEdit(boxSkuDto.getCode(), boxSkuDto.getLocation());
        return "success";
    }
}
