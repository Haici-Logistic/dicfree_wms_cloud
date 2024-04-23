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
 * @date 2023/11/26
 */
@Getter
@Setter
@ToString
public class CityData {

    /**
     * 所属省份
     */
    @QingFlowField("Province")
    private String province;

    /**
     * 城市编号
     */
    @QingFlowField("编号")
    private String code;

    /**
     * 城市名称
     */
    @QingFlowField("City")
    private String name;
}
