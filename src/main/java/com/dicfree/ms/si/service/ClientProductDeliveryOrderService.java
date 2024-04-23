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
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;

/**
 * @author wiiyaya
 * @date 2023/11/2
 */
public interface ClientProductDeliveryOrderService {

    /**
     * 出库订单新增
     *
     * @param productDeliveryOrderDto 出库订单信息
     * @return 出库订单
     */
    Long productDeliveryOrderAdd(ExProductDeliveryOrderDto productDeliveryOrderDto) throws BusinessException;

    /**
     * 出库订单快递跟踪
     *
     * @param courier        快递公司
     * @param waybill        运单号
     * @param callbackParams 回调参数
     */
    void productDeliveryOrderTraceCallback(String courier, String waybill, Object callbackParams) throws BusinessException;
}
