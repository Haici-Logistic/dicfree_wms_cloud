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
 * @date 2023/10/25
 */
@Getter
@Setter
public class ProductArrivalOrderDto extends BaseDto<String, Long> {
    @Serial
    private static final long serialVersionUID = -975215094029979300L;

    /**
     * 客户
     */
    private String supplier;

    /**
     * 外部订单编号
     */
    private String thirdOrderNo;

    /**
     * 虚拟车号
     */
    private String containerNoVirtual;

    /**
     * 真实车号
     */
    private String containerNoReal;

    /**
     * 已入库数量
     */
    private Integer inboundCount;

    /**
     * 已上架数量
     */
    private Integer onShelfCount;

    /**
     * 总计数量
     */
    private Integer totalCount;

    /**
     * 入库状态
     */
    private OrderStatus inboundStatus;

    /**
     * 上架状态
     */
    private OrderStatus onShelfStatus;
}
