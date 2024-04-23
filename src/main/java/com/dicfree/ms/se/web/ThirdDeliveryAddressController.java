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
import com.dicfree.ms.se.service.ThirdDeliveryAddressService;
import com.dicfree.ms.wms.common.dto.ex.qingflow.DeliveryAddressData;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ThirdBoxDeliveryOrderAddReq;
import com.dicfree.ms.wms.common.dto.ex.query.ExBoxArrivalOrderQueryDto;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 外部系统 - 发货地址接口
 *
 * @author wiiyaya
 * @date 2023/11/14
 */
@RestController
@Validated
public class ThirdDeliveryAddressController extends ThirdRestBaseController {

    @Resource
    private ThirdDeliveryAddressService thirdDeliveryAddressService;

    /**
     * 整装箱入库订单分页
     *
     * @param deliveryAddressData 地址查询信息
     * @return 整装箱入库订单信息
     */
    @PostMapping(value = "/third/deliveryAddress/list")
    public List<DeliveryAddressData> deliveryAddressList(@RequestBody DeliveryAddressData deliveryAddressData) throws BusinessException {
        LoginUserDto currentThird = CurrentUserUtils.currentUser();
        return thirdDeliveryAddressService.deliveryAddressList(currentThird.getUsername(), deliveryAddressData.getCountry());
    }
}
