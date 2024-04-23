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

import java.math.BigDecimal;

/**
 * @author wiiyaya
 * @date 2023/11/1
 */
@Getter
@Setter
@ToString
public class WaybillReq extends C3xComReq {

    @JsonProperty("AirwayBillData")
    private Data airWaybillData;

    @Getter
    @Setter
    @ToString
    public static class Data{
        @JsonProperty("AirWayBillCreatedBy")
        private String airWaybillCreatedBy;

        @JsonProperty("CODAmount")
        private BigDecimal codAmount;

        @JsonProperty("CODCurrency")
        private String codCurrency;

        @JsonProperty("Destination")
        private String destination;

        @JsonProperty("DutyConsigneePay")
        private Integer dutyConsigneePay;

        @JsonProperty("GoodsDescription")
        private String goodsDescription;

        @JsonProperty("NumberofPeices")
        private Integer numberOfPieces;

        @JsonProperty("Origin")
        private String origin;

        @JsonProperty("ProductType")
        private String productType;

        @JsonProperty("ReceiversAddress1")
        private String receiversAddress1;

        @JsonProperty("ReceiversAddress2")
        private String receiversAddress2;

        @JsonProperty("ReceiversCity")
        private String receiversCity;

        @JsonProperty("ReceiversCountry")
        private String receiversCountry;

        @JsonProperty("ReceiversCompany")
        private String receiversCompany;

        @JsonProperty("ReceiversContactPerson")
        private String receiversContactPerson;

        @JsonProperty("ReceiversMobile")
        private String receiversMobile;

        @JsonProperty("SendersAddress1")
        private String sendersAddress1;

        @JsonProperty("SendersCity")
        private String sendersCity;

        @JsonProperty("SendersCountry")
        private String sendersCountry;

        @JsonProperty("SendersCompany")
        private String sendersCompany;

        @JsonProperty("SendersContactPerson")
        private String sendersContactPerson;

        @JsonProperty("SendersMobile")
        private String sendersMobile;

        @JsonProperty("ServiceType")
        private String serviceType;

        @JsonProperty("ShipmentInvoiceCurrency")
        private String shipmentInvoiceCurrency;

        @JsonProperty("ShipmentInvoiceValue")
        private BigDecimal shipmentInvoiceValue;

        @JsonProperty("Weight")
        private Double weight;
    }

}
