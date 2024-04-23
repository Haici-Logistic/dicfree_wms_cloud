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

import cn.jzyunqi.common.model.ValidatorDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/14
 */
@Getter
@Setter
@ToString
public class ThirdBoxDeliveryOrderAddReq {
    private String supplier;
    private String thirdBoxDeliveryOrder;
    private String appointmentId;
    private LocalDateTime deliveryDate;
    private String deliveryTo;
    private String name;
    private String phone1;
    private String phone2;
    private String country;
    private String province;
    private String city;
    private String address;
    private List<DeliveryOrderItem> deliveryOrderItemList;

    @Getter
    @Setter
    @ToString
    public static class DeliveryOrderItem implements ValidatorDto {
        private String supplierBoxCode;
        private Integer pcs;
    }
}
