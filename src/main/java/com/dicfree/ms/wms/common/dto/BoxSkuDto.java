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
package com.dicfree.ms.wms.common.dto;

import cn.jzyunqi.common.model.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
@Getter
@Setter
public class BoxSkuDto extends BaseDto<String, Long> {
    @Serial
    private static final long serialVersionUID = -630515074139790100L;

    /**
     * 箱号 -> 客户箱号 +‘-’+ 客户
     */
    private String code;

    /**
     * 客户箱号
     */
    private String supplierBoxCode;

    /**
     * 客户
     */
    private String supplier;

    /**
     * 随机取件码 -> 英文+数字，随机产生
     */
    private String pickUpCode;

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
}
