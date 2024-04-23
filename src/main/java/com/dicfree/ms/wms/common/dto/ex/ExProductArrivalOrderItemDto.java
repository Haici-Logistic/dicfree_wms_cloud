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
package com.dicfree.ms.wms.common.dto.ex;

import cn.jzyunqi.common.model.ValidatorDto;
import com.dicfree.ms.wms.common.dto.ProductArrivalOrderItemDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
@Setter
public class ExProductArrivalOrderItemDto extends ProductArrivalOrderItemDto implements ValidatorDto {
    @Serial
    private static final long serialVersionUID = -230441634780450100L;

    public interface Add {
    }

    public interface Edit {
    }

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 序列商品清单
     */
    @Getter
    private List<ExProductSnDto> productSnList;

    @NotBlank(groups = Add.class)
    public String getProductCode() {
        return productCode;
    }

    @Override
    @NotNull(groups = Add.class)
    @Range(min = 1, max = 999, groups = Add.class)
    public Integer getTotalCount() {
        return super.getTotalCount();
    }
}
