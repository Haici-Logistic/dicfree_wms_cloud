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
package com.dicfree.ms.wms.common.dto.ex.qingflow;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/2
 */
@Getter
@Setter
@ToString
public class ProductDeliveryOrderAddReq {

    /**
     * 客户
     */
    private String supplier;

    /**
     * 内部订单编号
     */
    private Long deliveryOrder;

    /**
     * 外部订单编号
     */
    private String thirdDeliveryOrder;

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

    /**
     * 产品列表
     */
    private List<Sku> skuList;

    @Getter
    @Setter
    @ToString
    public static class Sku{
        /**
         * 产品编号
         */
        private String productCode;

        /**
         * 数量
         */
        private Integer pcs;
    }
}
