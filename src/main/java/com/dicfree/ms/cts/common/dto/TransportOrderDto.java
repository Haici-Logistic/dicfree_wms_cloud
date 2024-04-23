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
package com.dicfree.ms.cts.common.dto;

import cn.jzyunqi.common.model.BaseDto;
import com.dicfree.ms.cts.common.enums.TransportMode;
import com.dicfree.ms.cts.common.enums.TransportOrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author wiiyaya
 * @date 2023/11/28
 */
@Getter
@Setter
public class TransportOrderDto extends BaseDto<String, Long> {
    @Serial
    private static final long serialVersionUID = -317415068461507300L;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 货品的快递面单号
     */
    private String waybill;

    /**
     * 计价金额
     */
    private BigDecimal amount;

    /**
     * 快递件上的姓名
     */
    private String name;

    /**
     * 快递件上的手机号
     */
    private String phone;

    /**
     * 发运目的地酋长国
     */
    private String province;

    /**
     * 发运目的地城市
     */
    private String city;

    /**
     * 发运目的地地址
     */
    private String address;

    /**
     * 包裹重量
     */
    private Float weight;

    /**
     * 包裹长度
     */
    private Float length;

    /**
     * 包裹宽度
     */
    private Float width;

    /**
     * 包裹高度
     */
    private Float height;

    /**
     * 是否可运
     */
    private Boolean transportable;

    /**
     * 运单模式
     */
    private TransportMode transportMode;

    /**
     * 运单状态
     */
    private TransportOrderStatus status;

    /**
     * 头程入库时间
     */
    private LocalDateTime firstInboundTime;

    /**
     * 头程发货时间
     */
    private LocalDateTime firstDeliveryTime;

    /**
     * 尾程入库时间
     */
    private LocalDateTime lastInboundTime;

    /**
     * 尾程派送时间
     */
    private LocalDateTime lastDeliveryTime;

    /**
     * 尾程签收时间
     */
    private LocalDateTime lastSigningTime;
}
