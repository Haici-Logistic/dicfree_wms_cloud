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

/**
 * @author wiiyaya
 * @date 2023/11/28
 */
@Getter
@Setter
@ToString
public class ThirdTransportOrderAddReq {
    private String supplier;
    private Long memberId;
    private String waybill;
    private String province;
    private String city;
    private String address;
    private String phone;
    private Float weight;
    private Float length;
    private Float width;
    private Float height;
    private String transportMode; //海运为”navigation“，航运为”aviation“
    private Boolean transportable;
}
