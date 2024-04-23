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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2023/10/28
 */
@Getter
@Setter
public class ProductDeliveryOrderExcelImport extends ExcelImportDto {

    /**
     * 外部订单编号
     */
    private String thirdOrderNo;

    /**
     * 收件人姓名
     */
    private String name;

    /**
     * 收件人主叫电话
     */
    private String phone1;

    /**
     * 收件人备用电话
     */
    private String phone2;

    /**
     * 收件人省份
     */
    private String province;

    /**
     * 收件人城市
     */
    private String city;

    /**
     * 收件人详细地址
     */
    private String address;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 数量
     */
    private Integer pcs;

    /**
     * 到付收款金额
     */
    private BigDecimal cod;

    /**
     * 备注
     */
    private String remark;

    @Override
    public Map<String, String> prepareTitleToFieldMap() {
        Map<String, String> objectMapping = new HashMap<>();
        objectMapping.put("第三方订单号", "thirdOrderNo");
        objectMapping.put("收件人姓名", "name");
        objectMapping.put("手机号", "phone1");
        objectMapping.put("备用手机号", "phone2");
        objectMapping.put("收件省份", "province");
        objectMapping.put("收件城市", "city");
        objectMapping.put("详细地址", "address");
        objectMapping.put("产品SKU", "productCode");
        objectMapping.put("数量", "pcs");
        objectMapping.put("到付收款金额（AED）", "cod");
        objectMapping.put("备注", "remark");
        return objectMapping;
    }
}
