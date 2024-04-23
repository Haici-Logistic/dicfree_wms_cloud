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
package com.dicfree.ms.wms.common.dto;

import cn.jzyunqi.common.model.BaseDto;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author wiiyaya
 * @date 2023/10/28
 */
@Getter
@Setter
public class ProductDeliveryOrderItemDto extends BaseDto<String, Long> {
    @Serial
    private static final long serialVersionUID = -229293344062256450L;

    /**
     * 电商商品出库订单
     */
    private Long productDeliveryOrderId;

    /**
     * 电商商品号
     */
    private String productSkuCode;

    /**
     * 下架状态
     */
    private OrderStatus offShelfStatus;

    /**
     * 已下架数量
     */
    private Integer offShelfCount;

    /**
     * 核验状态
     */
    private OrderStatus verifyStatus;

    /**
     * 已核验数量
     */
    private Integer verifyCount;

    /**
     * 总计数量
     */
    private Integer totalCount;
}
