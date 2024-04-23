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
import com.dicfree.ms.wms.common.dto.BoxSkuDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.ScriptAssert;

import java.io.Serial;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
@Setter
@ScriptAssert(script = "_this.sortingTo != null && _this.sortingTo !=''  || _this.deliveryTo != null && _this.deliveryTo != ''", lang = "javascript", message = "{NotAllBlank.exBoxSkuDto.sortingToAndDeliveryTo}", groups = {ExBoxSkuDto.DeliveryEdit.class})
public class ExBoxSkuDto extends BoxSkuDto implements ValidatorDto {
    @Serial
    private static final long serialVersionUID = 265235015933279500L;

    public interface Add {
    }

    public interface DeliveryEdit {
    }

    public interface LocationEdit {
    }

    /**
     * sn清单
     */
    @Getter
    private List<ExBoxSnDto> boxSnList;

    @Override
    @NotBlank(groups = {Add.class})
    public String getSupplier() {
        return super.getSupplier();
    }

    @Override
    @NotBlank(groups = {Add.class})
    public String getSupplierBoxCode() {
        return super.getSupplierBoxCode();
    }

    @Override
    @NotBlank(groups = {DeliveryEdit.class, LocationEdit.class})
    public String getCode() {
        return super.getCode();
    }

    @Override
    @NotBlank(groups = {LocationEdit.class})
    public String getLocation() {
        return super.getLocation();
    }
}
