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
package com.dicfree.ms.wms.common.dto.ex.fastline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2024/4/17
 */
@Getter
@Setter
@ToString
public class TrackingData {

    private Object shipmentInformation;

    @JsonProperty("tracking_ShipmentItems")
    private Object shipmentItems;

    @JsonProperty("tracking_ShipmentStatusTracking")
    private List<Map<String, Object>> shipmentStatusTrackList;

}