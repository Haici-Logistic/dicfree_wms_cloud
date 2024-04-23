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

/**
 * @author wiiyaya
 * @date 2023/11/10
 */
@Getter
@Setter
@ToString
public class LocationData {

    /**
     * 库位提供容积
     */
    @QingFlowField("CBM(Surplus)")
    private String cbmSurplus;

    /**
     * 库位规划容积
     */
    @QingFlowField("CBM(Plan)")
    private String cbmPlan;

    /**
     * 库位组
     */
    @QingFlowField("groupCode")
    private String groupCode;

    /**
     * 库位编码
     */
    @QingFlowField("locationCode")
    private String code;

    /**
     * 库位模式
     */
    @QingFlowField("locationMold")
    private String mold;

    /**
     * 整库或混库
     */
    @QingFlowField("LocationType")
    private String locationType;

    /**
     * 被占用库位
     */
    @QingFlowField("arriverOrderNo")
    private String arriverOrderNo;

}
