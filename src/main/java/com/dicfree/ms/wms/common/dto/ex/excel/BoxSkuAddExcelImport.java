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
package com.dicfree.ms.wms.common.dto.ex.excel;

import cn.jzyunqi.common.model.poi.ExcelImportDto;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
@Getter
@Setter
public class BoxSkuAddExcelImport extends ExcelImportDto {

    /**
     * 客户箱号
     */
    private String supplierBoxCode;

    /**
     * 客户
     */
    private String supplier;

    /**
     * 同一批数量
     */
    private Integer pcs;

    /**
     * 位置-> 随意，可为空
     */
    private String location;

    /**
     * 中转 -> 随意，可为空
     */
    private String sortingTo;

    /**
     * 目的地-> 随意，可为空
     */
    private String deliveryTo;

    @Override
    public Map<String, String> prepareTitleToFieldMap() {
        Map<String, String> objectMapping = new HashMap<>();
        objectMapping.put("Supplier Box Code", "supplierBoxCode");
        objectMapping.put("Supplier", "supplier");
        objectMapping.put("Pcs", "pcs");
        objectMapping.put("Location", "location");
        objectMapping.put("Sorting To", "sortingTo");
        objectMapping.put("Delivery To", "deliveryTo");
        return objectMapping;
    }
}
