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
package com.dicfree.ms.se.service;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.wms.common.dto.ex.qingflow.DeliveryAddressData;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/14
 */
public interface ThirdDeliveryAddressService {

    /**
     * 查询发货地址
     *
     * @param supplier 客户
     * @param country 国家
     */
    List<DeliveryAddressData> deliveryAddressList(String supplier, String country) throws BusinessException;
}
