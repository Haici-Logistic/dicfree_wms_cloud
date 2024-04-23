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
import java.time.LocalDateTime;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
@Getter
@Setter
public class BoxArrivalOrderDto extends BaseDto<String, Long> {
    @Serial
    private static final long serialVersionUID = -346108939889496500L;

    /**
     * 单号
     */
    private String uniqueNo;

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
     * 计划到货日期
     */
    private LocalDateTime arrivingDate;

    /**
     * 状态
     */
    private OrderStatus inboundStatus;

    /**
     * 已入库数量
     */
    private Integer inboundCount;

    /**
     * 总计数量
     */
    private Integer totalCount;

    /**
     * 总计整库数量
     */
    private Integer totalWholeCount;

    /**
     * 总计混库数量
     */
    private Integer totalBulkCount;

    /**
     * 是否已排库
     */
    private Boolean locationDone;

    /**
     * 是否已打印
     */
    private Boolean printed;
}
