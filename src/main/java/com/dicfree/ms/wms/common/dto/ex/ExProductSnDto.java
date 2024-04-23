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
import com.dicfree.ms.wms.common.dto.ProductSnDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
@Setter
public class ExProductSnDto extends ProductSnDto implements ValidatorDto {
    @Serial
    private static final long serialVersionUID = 737762257862771200L;

    public interface ShelfChange {
    }

    public interface ProductSkuCodeChange {
    }

    public interface QualityChange {
    }

    public interface ReOnShelf {
    }

    @Getter
    private ExProductSkuDto productSku;

    @Override
    @NotBlank(groups = {ShelfChange.class, ProductSkuCodeChange.class, QualityChange.class, ReOnShelf.class})
    public String getCode() {
        return super.getCode();
    }

    @Override
    @NotBlank(groups = {ShelfChange.class, ReOnShelf.class})
    public String getShelfNo() {
        return super.getShelfNo();
    }

    @Override
    @NotBlank(groups = {ProductSkuCodeChange.class})
    public String getProductSkuCode() {
        return super.getProductSkuCode();
    }

    @Override
    @NotNull(groups = {QualityChange.class})
    public Boolean getQuality() {
        return super.getQuality();
    }
}
