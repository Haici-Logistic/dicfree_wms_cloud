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
package com.dicfree.ms.wms.common.constant;

import cn.jzyunqi.common.feature.redis.Cache;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
@Getter
@AllArgsConstructor
public enum WmsCache implements Cache {

    /**
     * 整装箱入库订单虚拟车牌号
     */
    WMS_AO_CONTAINER_NO_VIRTUAL_V(0L, Boolean.FALSE),

    /**
     * 整装箱出库订单虚拟任务号
     */
    WMS_CT_COLLECTION_NO_VIRTUAL_V(0L, Boolean.FALSE),

    /**
     * 入库库位分配队列
     */
    WMS_AO_LOCATION_ASSIGN_L(0L, Boolean.FALSE),

    /**
     * 快递接口token
     */
    WMS_COURIER_TOKEN_V(0L, Boolean.FALSE),
    ;
    private final Long expiration;

    private final Boolean autoRenew;
}
