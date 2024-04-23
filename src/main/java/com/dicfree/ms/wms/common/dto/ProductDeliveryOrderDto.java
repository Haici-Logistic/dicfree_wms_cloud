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
import com.dicfree.ms.wms.common.enums.CourierType;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import com.dicfree.ms.wms.common.enums.OrderType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author wiiyaya
 * @date 2023/10/28
 */
@Getter
@Setter
public class ProductDeliveryOrderDto extends BaseDto<String, Long> {
    @Serial
    private static final long serialVersionUID = -826522072408976100L;

    /**
     * 波次任务id
     */
    private Long waveTaskId;

    /**
     * 波次任务id
     */
    private OrderType type;

    /**
     * 客户
     */
    private String supplier;

    /**
     * 外部订单编号
     */
    private String thirdOrderNo;

    /**
     * 是否代发货
     */
    private Boolean proxy;

    /**
     * 是否已派单
     */
    private Boolean dispatched;

    /**
     * 派单时间
     */
    private LocalDateTime dispatchTime;

    /**
     * 是否已出库
     */
    private Boolean outbound;

    /**
     * 出库时间
     */
    private LocalDateTime outboundTime;

    /**
     * 核验状态
     * 下架状态
     */
    private OrderStatus offShelfStatus;

    /**
     * 已下架数量
     */
    private Integer offShelfCount;

    /**
     * 分拣状态
     */
    private OrderStatus sortingStatus;

    /**
     * 已分拣数量
     */
    private Integer sortingCount;

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

    /**
     * 分配的篮子号
     */
    private String basketNo;

    /**
     * 快运商
     */
    private String courier;

    /**
     * 快递单号
     */
    private String waybill;

    /**
     * 快递单号PDF地址
     */
    private String waybillUrl;

    /**
     * 收件人姓名
     */
    private String name;

    /**
     * 收件人主叫电话
     */
    private String phone1;

    /**
     * 收件人备用电话
     */
    private String phone2;

    /**
     * 收件人省份
     */
    private String province;

    /**
     * 收件人城市
     */
    private String city;

    /**
     * 收件人详细地址
     */
    private String address;

    /**
     * 到付收款金额
     */
    private BigDecimal cod;

    /**
     * 备注
     */
    private String remark;
}
