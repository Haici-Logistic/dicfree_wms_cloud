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
package com.dicfree.ms.wms.common.dto.ex.lark;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wiiyaya
 * @date 2023/9/18
 */
@Getter
@Setter
@ToString
public class BiTableRowData {

    private String id;

    @JsonProperty("record_id")
    private String recordId;

    private Fields fields;

    @Getter
    @Setter
    @ToString
    public static class Fields {

        @JsonProperty("Collection No Virtual")
        private String collectionNoVirtual;

        @JsonProperty("Pick Up Code")
        private String pickUpCode;

        @JsonProperty("Pcs")
        private int pcs;

        @JsonProperty("Member")
        private String member;

        @JsonProperty("Location")
        private String location;

        @JsonProperty("Supplier Box Code")
        private String supplierBoxCode;

        @JsonProperty("Sorting Date")
        private long sortingDate;

        @JsonProperty("Status")
        private String status;
    }
}
