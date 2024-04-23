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
import com.dicfree.ms.wms.common.dto.ex.ExBoxDeliveryOrderDto;
import com.dicfree.ms.wms.common.enums.OrderStatus;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
public interface ClientBoxDeliveryOrderService {

    /**
     * 整装箱出库订单添加
     *
     * @param boxDeliveryOrderDto 整装箱出库订单信息
     */
    String boxDeliveryOrderAdd(ExBoxDeliveryOrderDto boxDeliveryOrderDto) throws BusinessException;

    /**
     * 整装箱出库订单状态编辑
     *
     * @param uniqueNo   订单号
     * @param boxSkuCode 序列箱号
     * @param status     状态
     */
    void boxDeliveryOrderStatusEdit(String uniqueNo, String boxSkuCode, OrderStatus status) throws BusinessException;
}
