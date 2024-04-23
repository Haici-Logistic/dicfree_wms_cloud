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
package com.dicfree.ms.pda.component;

import cn.jzyunqi.autoconfigure.spring.properties.CommonProperties;
import cn.jzyunqi.common.model.spring.security.LoginUserDto;
import cn.jzyunqi.common.support.spring.security.auth.OAuth2AuthorizationServicePlus;
import cn.jzyunqi.common.support.spring.security.auth.OAuth2TokenInternalGen;
import cn.jzyunqi.common.utils.NetworkUtilPlus;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2023/10/26
 */
@Slf4j
@Getter
public class PdaUserAuthProvider extends DaoAuthenticationProvider implements OAuth2TokenInternalGen {

    @Resource
    private RegisteredClientRepository registeredClientService;
    @Resource
    private OAuth2AuthorizationServicePlus authorizationService;
    @Resource
    private OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    @Resource
    private CommonProperties commonProperties;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        authentication = super.authenticate(authentication);

        Map<String, Object> additionalParameters = new HashMap<>();
        LoginUserDto loginUserDto = (LoginUserDto) authentication.getPrincipal();
        loginUserDto.eraseCredentials();
        additionalParameters.put(Principal.class.getName(), loginUserDto);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes != null){
            additionalParameters.put("ipAddress", NetworkUtilPlus.getIpAddress(attributes.getRequest()));
            additionalParameters.put("userAgent", NetworkUtilPlus.getUserAgent(attributes.getRequest()));
            additionalParameters.put("deviceId", NetworkUtilPlus.getDeviceId(attributes.getRequest()));
        }
        return genToken("pda_gw", authentication, additionalParameters);
    }

    @Override
    public boolean multipleLogin() {
        return commonProperties.isMultipleLogin();
    }

    @Override
    public int multipleLoginReserved() {
        return commonProperties.getMultipleLoginReserved();
    }

    @Override
    public MessageSourceAccessor getMessages() {
        return messages;
    }
}
