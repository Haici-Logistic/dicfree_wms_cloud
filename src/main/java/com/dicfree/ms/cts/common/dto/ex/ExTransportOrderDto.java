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
package com.dicfree.ms.cts.common.dto.ex;

import cn.jzyunqi.common.model.ValidatorDto;
import com.dicfree.ms.cts.common.dto.TransportOrderDto;
import com.dicfree.ms.cts.common.enums.TransportMode;
import com.dicfree.ms.wms.common.dto.ex.ExTracingDto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/28
 */
@Setter
public class ExTransportOrderDto extends TransportOrderDto implements ValidatorDto{
    @Serial
    private static final long serialVersionUID = -363625738206581500L;

    public interface ThirdAdd {
    }

    public interface ClientAdd {
    }

    public interface ClientEdit {
    }

    /**
     * 会员编号（海运或航运编号）
     */
    private String xno;

    @Getter
    private List<ExTracingDto> tracingList;

    @NotBlank(groups = {ThirdAdd.class})
    public String getXno() {
        return xno;
    }

    @Override
    @NotNull(groups = {ClientAdd.class, ClientEdit.class})
    public Long getMemberId() {
        return super.getMemberId();
    }

    @Override
    @NotBlank(groups = {ClientAdd.class, ThirdAdd.class, ClientEdit.class})
    public String getWaybill() {
        return super.getWaybill();
    }

    @Override
    @NotBlank(groups = {ThirdAdd.class})
    public String getPhone() {
        return super.getPhone();
    }

    @Override
    @NotNull(groups = {ThirdAdd.class, ClientAdd.class})
    public TransportMode getTransportMode() {
        return super.getTransportMode();
    }

    @Override
    @NotNull(groups = {ClientAdd.class})
    @DecimalMin(value = "0.01", groups = {ClientAdd.class})
    public BigDecimal getAmount() {
        return super.getAmount();
    }

    @Override
    @NotNull(groups = {ClientAdd.class, ThirdAdd.class})
    @DecimalMin(value = "0.001", groups = {ClientAdd.class, ThirdAdd.class})
    public Float getWeight() {
        return super.getWeight();
    }

    @Override
    @NotNull(groups = {ClientAdd.class, ThirdAdd.class})
    @DecimalMin(value = "0.001", groups = {ClientAdd.class, ThirdAdd.class})
    public Float getLength() {
        return super.getLength();
    }

    @Override
    @NotNull(groups = {ClientAdd.class, ThirdAdd.class})
    @DecimalMin(value = "0.001", groups = {ClientAdd.class, ThirdAdd.class})
    public Float getWidth() {
        return super.getWidth();
    }

    @Override
    @NotNull(groups = {ClientAdd.class, ThirdAdd.class})
    @DecimalMin(value = "0.001", groups = {ClientAdd.class, ThirdAdd.class})
    public Float getHeight() {
        return super.getHeight();
    }

    @Override
    @NotNull(groups = {ClientAdd.class, ThirdAdd.class})
    public Boolean getTransportable() {
        return super.getTransportable();
    }
}
