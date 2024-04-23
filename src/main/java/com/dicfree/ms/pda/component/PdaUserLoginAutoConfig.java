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

import cn.jzyunqi.common.model.spring.AuthorityDto;
import cn.jzyunqi.common.support.spring.security.auth.DefaultLoginFailureHandler;
import cn.jzyunqi.common.support.spring.security.auth.DefaultLoginSuccessHandler;
import cn.jzyunqi.common.support.spring.security.auth.DefaultLogoutHandler;
import cn.jzyunqi.common.support.spring.security.auth.DefaultLogoutSuccessHandler;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.ms.uaa.common.enums.AuthType;
import cn.jzyunqi.ms.uaa.service.UserAuthService;
import com.dicfree.ms.pda.service.PdaUserAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author wiiyaya
 * @date 2023/10/26
 */
@Configuration
public class PdaUserLoginAutoConfig {


    private static final String SSO_MATCHER = "/sso/pda/**";

    private static final String LOGIN_URI = "/sso/pda/login";

    private static final String LOGOUT_URI = "/sso/pda/logout";

    @Bean
    @Order(2)
    public SecurityFilterChain mpmUserLoginFilterChain(HttpSecurity http,
                                                       OAuth2AuthorizationService oauth2AuthorizationService,
                                                       Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder,
                                                       PdaUserAuthProvider mpmUserAuthProvider
    ) throws Exception {
        ObjectMapper objectMapper = jackson2ObjectMapperBuilder.build();
        http.securityMatcher(SSO_MATCHER);
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authRequestConfig -> authRequestConfig
                        .requestMatchers(LOGIN_URI, LOGOUT_URI).permitAll()
                        .anyRequest().denyAll()
                )
                .authenticationProvider(mpmUserAuthProvider)
                .formLogin(formLoginConfig -> formLoginConfig
                        .loginProcessingUrl(LOGIN_URI)
                        .successHandler(new DefaultLoginSuccessHandler(objectMapper))
                        .failureHandler(new DefaultLoginFailureHandler(LOGIN_URI, objectMapper))
                )
                .logout(logoutConfig -> logoutConfig
                        .logoutUrl(LOGOUT_URI)
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .addLogoutHandler(new DefaultLogoutHandler(LOGOUT_URI, objectMapper, oauth2AuthorizationService))
                        .logoutSuccessHandler(new DefaultLogoutSuccessHandler(objectMapper))
                );
        return http.build();
    }

    @Bean
    public PdaUserAuthProvider mpmUserAuthProvider(PdaUserAuthService pdaUserAuthService, PasswordEncoder passwordEncoder) {
        PdaUserAuthProvider mpmUserAuthProvider = new PdaUserAuthProvider();
        mpmUserAuthProvider.setPasswordEncoder(passwordEncoder);
        mpmUserAuthProvider.setUserDetailsService(pdaUserAuthService::preOauth2Login);
        return mpmUserAuthProvider;
    }
}
