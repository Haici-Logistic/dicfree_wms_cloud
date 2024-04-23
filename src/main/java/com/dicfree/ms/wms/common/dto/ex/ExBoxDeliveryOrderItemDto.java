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
import com.dicfree.ms.wms.common.dto.BoxDeliveryOrderItemDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
@Setter
public class ExBoxDeliveryOrderItemDto extends BoxDeliveryOrderItemDto implements ValidatorDto{
    @Serial
    private static final long serialVersionUID = 503869397669507900L;

    public interface Add {
    }

    public interface Edit {
    }

    /**
     * 客户箱号
     */
    private String supplierBoxCode;

    @Getter
    private ExBoxDeliveryOrderDto boxDeliveryOrderDto;

    @Getter
    private ExBoxSkuDto boxSku;

    @NotBlank(groups = Add.class)
    public String getSupplierBoxCode() {
        return supplierBoxCode;
    }

    @Override
    @NotNull(groups = Add.class)
    @Range(min = 1, max = 999, groups = Add.class)
    public Integer getTotalCount() {
        return super.getTotalCount();
    }
}
