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
package com.dicfree.ms.si.service;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSkuDto;

/**
 * @author wiiyaya
 * @date 2023/8/27.
 */
public interface ClientBoxSkuService {

    /**
     * 整装箱新增
     *
     * @param boxSkuDto 整装箱
     */
    ExBoxSkuDto boxSkuAdd(ExBoxSkuDto boxSkuDto) throws BusinessException;

    /**
     * 整装箱更新发货地址
     *
     * @param boxSkuCode 箱号
     * @param sortingTo 中转
     * @param deliveryTo 目的地
     */
    void boxSkuDeliveryEdit(String boxSkuCode, String sortingTo, String deliveryTo) throws BusinessException;

    /**
     * 整装箱更新库位
     *
     * @param boxSkuCode 箱号
     * @param location 库位
     */
    void boxSkuLocationEdit(String boxSkuCode, String location) throws BusinessException;
}
