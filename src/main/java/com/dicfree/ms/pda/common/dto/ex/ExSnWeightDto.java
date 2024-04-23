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
package com.dicfree.ms.pda.common.dto.ex;

import cn.jzyunqi.common.model.ValidatorDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wiiyaya
 * @date 2024/1/10
 */
@Setter
public class ExSnWeightDto implements ValidatorDto {

    public interface Inbound {
    }

    /**
     * 重量
     */
    private Float weight;

    /**
     * 长
     */
    private Integer length;

    /**
     * 宽
     */
    private Integer width;

    /**
     * 高
     */
    private Integer height;

    /**
     * 质检
     */
    private Boolean quality;

    /**
     * 已入库数量
     */
    @Getter
    private Integer inboundCount;

    /**
     * 总计数量
     */
    @Getter
    private Integer totalCount;

    @NotNull(groups = Inbound.class)
    @Min(value = 0, groups = Inbound.class)
    public Float getWeight() {
        return weight;
    }

    @NotNull(groups = Inbound.class)
    @Min(value = 0, groups = Inbound.class)
    public Integer getLength() {
        return length;
    }

    @NotNull(groups = Inbound.class)
    @Min(value = 0, groups = Inbound.class)
    public Integer getWidth() {
        return width;
    }

    @NotNull(groups = Inbound.class)
    @Min(value = 0, groups = Inbound.class)
    public Integer getHeight() {
        return height;
    }

    @NotNull(groups = Inbound.class)
    public Boolean getQuality() {
        return quality;
    }
}
