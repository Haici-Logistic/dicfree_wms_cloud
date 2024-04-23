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
package com.dicfree.ms.pda.service;

import cn.jzyunqi.common.model.spring.AuthorityDto;
import cn.jzyunqi.common.model.spring.security.LoginUserDto;
import cn.jzyunqi.ms.uaa.common.enums.AuthType;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/10/27
 */
public interface PdaUserAuthService {

    /**
     * PDA用户登录
     *
     * @param devicePdaCode           用户名
     * @return token
     */
    LoginUserDto preOauth2Login(String devicePdaCode);
}
