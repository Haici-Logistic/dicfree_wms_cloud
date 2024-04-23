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
import com.dicfree.ms.wms.common.enums.StocktakeDirection;
import com.dicfree.ms.wms.common.enums.StocktakeType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
@Getter
@Setter
public class StocktakeRecordDto extends BaseDto<String, Long> {
    @Serial
    private static final long serialVersionUID = 394659984816275700L;

    /**
     * 触发时间
     */
    private LocalDateTime triggerTime;

    /**
     * 箱号
     */
    private String boxSkuCode;

    /**
     * 序列箱号
     */
    private String boxSnCode;

    /**
     * 客户箱号
     */
    private String supplierBoxCode;

    /**
     * 客户序列箱号 -> 客户箱号+序号
     */
    private String supplierBoxSnCode;

    /**
     * 盘点类型
     */
    private StocktakeType type;

    /**
     * 盘点类型
     */
    private StocktakeDirection direction;

    /**
     * 仓库名
     */
    private String hub;
}
