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
package com.dicfree.ms.pda.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.spring.security.LoginUserDto;
import cn.jzyunqi.ms.uaa.common.enums.AuthType;
import cn.jzyunqi.ms.uaa.service.UserAuthService;
import com.dicfree.ms.pda.common.constant.PdaConstant;
import com.dicfree.ms.pda.service.PdaUserAuthService;
import com.dicfree.ms.wms.common.dto.ex.ExDevicePdaDto;
import com.dicfree.ms.wms.service.DevicePdaService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2023/10/27
 */
@Service("pdaUserAuthService")
public class PdaUserAuthServiceImpl implements PdaUserAuthService {

    @Resource
    private UserAuthService userAuthService;

    @Resource
    private DevicePdaService devicePdaService;

    @Override
    public LoginUserDto preOauth2Login(String devicePdaCode) {
        try {
            ExDevicePdaDto devicePdaDto =  devicePdaService.devicePdaUserInfoWithNull(devicePdaCode);
            Map<String, Object> extInfo = new HashMap<>();
            extInfo.put(PdaConstant.LOGIN_ATTR_DEVICE_PDA_CODE, devicePdaCode);
            LoginUserDto loginUserDto = userAuthService.preOauth2Login(AuthType.ACCOUNT, devicePdaDto.getUserInfo().getUsername(), null, extInfo);
            loginUserDto.setPassword(devicePdaDto.getKeySign());

            return loginUserDto;
        } catch (BusinessException e) {
            throw new UsernameNotFoundException(devicePdaCode, e);
        }
    }
}
