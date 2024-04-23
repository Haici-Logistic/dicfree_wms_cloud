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
 * @date 2024/1/10
 */
@Getter
@Setter
@ToString
public class ProductSkuWeightData {

    /**
     * 重量
     */
    @QingFlowField("Weight/KGS")
    private String weight;

    /**
     * 长
     */
    @QingFlowField("length/cm")
    private String length;

    /**
     * 宽
     */
    @QingFlowField("width/cm")
    private String width;

    /**
     * 高
     */
    @QingFlowField("height/cm")
    private String height;

}
