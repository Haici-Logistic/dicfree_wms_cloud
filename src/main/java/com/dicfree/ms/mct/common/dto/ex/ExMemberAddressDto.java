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
package com.dicfree.ms.mct.common.dto.ex;

import cn.jzyunqi.common.model.ValidatorDto;
import com.dicfree.ms.mct.common.dto.MemberAddressDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

import java.io.Serial;

/**
 * @author wiiyaya
 * @date 2023/11/26
 */
@Setter
public class ExMemberAddressDto extends MemberAddressDto implements ValidatorDto{
    @Serial
    private static final long serialVersionUID = -342266048924944060L;

    public interface ClientAdd {
    }

    public interface ClientEdit {
    }

    @Override
    @NotNull(groups = {ClientEdit.class})
    public Long getId() {
        return super.getId();
    }

    @Override
    @NotBlank(groups = {ClientAdd.class, ClientEdit.class})
    public String getName() {
        return super.getName();
    }

    @Override
    @NotBlank(groups = {ClientAdd.class, ClientEdit.class})
    public String getPhone() {
        return super.getPhone();
    }

    @Override
    @NotBlank(groups = {ClientAdd.class, ClientEdit.class})
    public String getProvince() {
        return super.getProvince();
    }

    @Override
    @NotBlank(groups = {ClientAdd.class, ClientEdit.class})
    public String getCity() {
        return super.getCity();
    }

    @Override
    @NotBlank(groups = {ClientAdd.class, ClientEdit.class})
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    @NotNull(groups = {ClientAdd.class})
    public Boolean getDefaultOne() {
        return super.getDefaultOne();
    }

}
