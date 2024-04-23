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
import com.dicfree.ms.wms.common.enums.OrderStatus;
import com.dicfree.ms.wms.common.enums.WaveTaskType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author wiiyaya
 * @date 2023/11/06
 */
@Getter
@Setter
public class ProductWaveTaskDto extends BaseDto<String, Long> {
    @Serial
    private static final long serialVersionUID = -266488871933608480L;

    /**
     * 波次任务编号
     */
    private String uniqueNo;

    /**
     * 波次任务类型
     */
    private WaveTaskType type;

    /**
     * 集货区编码
     */
    private String collectionAreaCode;

    /**
     * 下架状态
     */
    private OrderStatus offShelfStatus;

    /**
     * 已下架数量
     */
    private Integer offShelfCount;

    /**
     * 分篮状态
     */
    private OrderStatus basketStatus;

    /**
     * 已分篮数量
     */
    private Integer basketCount;

    /**
     * 分拣状态
     */
    private OrderStatus sortingStatus;

    /**
     * 已分拣数量
     */
    private Integer sortingCount;

    /**
     * 核验状态
     */
    private OrderStatus verifyStatus;

    /**
     * 总计商品数量
     */
    private Integer totalSnCount;
}
