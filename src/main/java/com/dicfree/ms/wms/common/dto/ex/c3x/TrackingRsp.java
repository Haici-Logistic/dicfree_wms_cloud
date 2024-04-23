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
package com.dicfree.ms.wms.common.dto.ex.c3x;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/1
 */
@Getter
@Setter
@ToString
public class TrackingRsp extends C3xComRsp{

    @JsonProperty("AirwayBillTrackList")
    private List<AirWaybillTrack> airWaybillTrackList;

    @Getter
    @Setter
    @ToString
    public static class AirWaybillTrack {
        @JsonProperty("AirWayBillNo")
        private String airWaybillNo;
        @JsonProperty("TrackingLogDetails")
        private List<TrackingLogDetail> trackingLogDetails;
    }

    @Getter
    @Setter
    @ToString
    public static class TrackingLogDetail {
        @JsonProperty("ActivityDate")
        private String activityDate;
        @JsonProperty("ActivityTime")
        private String activityTime;
        @JsonProperty("DeliveredTo")
        private String deliveredTo;
        @JsonProperty("Location")
        private String location;
        @JsonProperty("Remarks")
        private String remarks;
        @JsonProperty("Status")
        private String Status;
    }
}
