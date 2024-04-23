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
package com.dicfree.ms.wms.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.ms.sys.common.dto.ex.ExAdminUserDto;
import cn.jzyunqi.ms.sys.service.AdminUserService;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExDevicePdaDto;
import com.dicfree.ms.wms.repository.jpa.dao.DevicePdaDao;
import com.dicfree.ms.wms.repository.jpa.entity.DevicePda;
import com.dicfree.ms.wms.service.DevicePdaService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
@Service("devicePdaService")
public class DevicePdaServiceImpl implements DevicePdaService {

    @Resource
    private DevicePdaDao devicePdaDao;

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void devicePdaBind(ExDevicePdaDto devicePdaDto) throws BusinessException {
        //检查用户是否存在
        ExAdminUserDto adminUserDto = adminUserService.userSimple(devicePdaDto.getUserId());

        //绑定PDA和用户
        DevicePda devicePda = new DevicePda();
        devicePda.setUserId(adminUserDto.getId());
        devicePda.setName(devicePdaDto.getName());
        devicePda.setCode(devicePdaDto.getCode());
        devicePda.setKey(devicePdaDto.getKey());
        devicePda.setKeySign(passwordEncoder.encode(devicePdaDto.getKey()));
        devicePda.setShelfAreaCode(devicePdaDto.getShelfAreaCode());
        devicePdaDao.save(devicePda);
    }

    @Override
    public ExDevicePdaDto devicePdaUserInfoWithNull(String code) throws BusinessException {
        DevicePda devicePda = devicePdaDao.findByCode(code).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_DEVICE_PDA_NOT_FOUND));

        ExDevicePdaDto devicePdaDto = new ExDevicePdaDto();
        devicePdaDto.setKeySign(devicePda.getKeySign());

        ExAdminUserDto adminUserDto = adminUserService.userSimple(devicePda.getUserId());
        devicePdaDto.setUserInfo(adminUserDto);
        return devicePdaDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void devicePdaActive(String code) {
        //TODO 这个是自动注册的方式，看情况再说
    }

    @Override
    public ExDevicePdaDto devicePdaInfo(String code) throws BusinessException {
        DevicePda devicePda = devicePdaDao.findByCode(code).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_DEVICE_PDA_NOT_FOUND));

        ExDevicePdaDto devicePdaDto = new ExDevicePdaDto();
        devicePdaDto.setCode(devicePda.getCode());
        devicePdaDto.setName(devicePda.getName());
        devicePdaDto.setKey(devicePda.getKey());
        devicePdaDto.setShelfAreaCode(devicePda.getShelfAreaCode());
        return devicePdaDto;
    }
}
