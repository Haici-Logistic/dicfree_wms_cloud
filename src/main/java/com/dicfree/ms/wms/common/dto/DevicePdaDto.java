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
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
@Getter
@Setter
public class DevicePdaDto extends BaseDto<String, Long> {
    @Serial
    private static final long serialVersionUID = 206302604846267940L;

    /**
     * PDA绑定用户id
     */
    private Long userId;

    /**
     * PDA唯一编码
     */
    private String code;

    /**
     * PDA名称
     */
    private String name;

    /**
     * PDA登录密钥
     */
    private String key;

    /**
     * PDA登录密钥密文
     */
    private String keySign;

    /**
     * PDA所属区域
     */
    private String shelfAreaCode;
}
