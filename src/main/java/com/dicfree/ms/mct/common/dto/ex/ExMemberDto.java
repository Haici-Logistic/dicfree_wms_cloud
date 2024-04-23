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
import cn.jzyunqi.ms.uaa.common.dto.ex.ExUserDto;
import com.dicfree.ms.mct.common.dto.MemberDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author wiiyaya
 * @date 2023/11/28
 */
@Setter
public class ExMemberDto extends MemberDto implements ValidatorDto{
    @Serial
    private static final long serialVersionUID = -662082894325667800L;

    public interface ClientEdit {
    }

    @Getter
    private String uid;
}
