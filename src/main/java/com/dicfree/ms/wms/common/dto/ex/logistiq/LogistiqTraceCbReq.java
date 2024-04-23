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
package com.dicfree.ms.wms.common.dto.ex.logistiq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wiiyaya
 * @date 2023/11/8
 */
@Getter
@Setter
@ToString
public class LogistiqTraceCbReq {

    /**
     * cp_awb (awb number shared by the carrier to which the order is allocated)
     */
    @JsonProperty("waybill")
    private String waybill;

    /**
     * Order reference number to identify the shipment
     */
    @JsonProperty("ShipmentNo")
    private String shipmentNo;

    /**
     * order status id
     */
    @JsonProperty("Status")
    private String status;

    /**
     * order status description
     */
    @JsonProperty("StatusCode")
    private String statusCode;

    /**
     * Current time stamp when the status update response is pushed
     * "2023-04-14 10:51:47.348652+00:00", # current timestamp
     */
    @JsonProperty("time_stamp")
    private String timeStamp;

    /**
     * Time stamp when the scan is applied on the order
     * "2023-04-14 10:50:00+00:00", # scan time (optional)
     */
    @JsonProperty("scan_time")
    private String scanTime;

    /**
     * Location when the scan is applied on the order
     */
    @JsonProperty("scan_location")
    private String scanLocation;

    /**
     * Logistiq
     */
    @JsonProperty("Source")
    private String source;
}
