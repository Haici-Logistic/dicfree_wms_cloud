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
import com.dicfree.ms.wms.common.dto.ex.ExDevicePrinterDto;
import com.dicfree.ms.wms.common.enums.DevicePrinterStatus;
import com.dicfree.ms.wms.repository.jpa.dao.DevicePrinterDao;
import com.dicfree.ms.wms.repository.jpa.entity.DevicePrinter;
import com.dicfree.ms.wms.service.DevicePrinterService;
import com.dicfree.ms.wms.service.client.FeieyunClient;
import freemarker.template.Configuration;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.Map;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
@Service("devicePrinterService")
public class DevicePrinterServiceImpl implements DevicePrinterService {

    @Resource
    private DevicePrinterDao devicePrinterDao;

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private FeieyunClient feieyunClient;

    @Resource
    private Configuration freemarkerConfiguration;

    @Override
    public ExDevicePrinterDto devicePrinterInfo(Long userId) throws BusinessException {
        DevicePrinter devicePrinter = devicePrinterDao.findByUserId(userId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_DEVICE_PRINTER_NOT_FOUND));
        ExDevicePrinterDto devicePrinterDto = new ExDevicePrinterDto();
        devicePrinterDto.setId(devicePrinter.getId());
        devicePrinterDto.setName(devicePrinter.getName());
        devicePrinterDto.setSn(devicePrinter.getSn());
        return devicePrinterDto;
    }

    @Override
    public String devicePrinterSn(Long userId) throws BusinessException {
        DevicePrinter devicePrinter = devicePrinterDao.findByUserId(userId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_DEVICE_PRINTER_NOT_FOUND));
        return devicePrinter.getSn();
    }

    @Override
    public String devicePrinterSn(String username) throws BusinessException {
        DevicePrinter devicePrinter = devicePrinterDao.findByUsername(username).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_DEVICE_PRINTER_NOT_FOUND));
        return devicePrinter.getSn();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void devicePrinterBind(ExDevicePrinterDto devicePrinterDto) throws BusinessException {

        //删除打印机
//        feieyunClient.devicePrinterDelete(devicePrinterDto.getSn());

        //检查用户是否存在
        ExAdminUserDto adminUserDto = adminUserService.userSimple(devicePrinterDto.getUsername());

        //检查打印机是否已存在
        Optional<DevicePrinter> devicePrinterOpt = devicePrinterDao.findBySn(devicePrinterDto.getSn());
        if(devicePrinterOpt.isPresent()){
            throw new BusinessException(WmsMessageConstant.ERROR_DEVICE_PRINTER_ALREADY_BIND);
        }

        //注册打印机
        feieyunClient.devicePrinterAdd(devicePrinterDto.getSn(), devicePrinterDto.getKey(), devicePrinterDto.getName(), devicePrinterDto.getSim());

        //绑定打印机和用户
        DevicePrinter devicePrinter = new DevicePrinter();
        devicePrinter.setUserId(adminUserDto.getId());
        devicePrinter.setUsername(adminUserDto.getUsername());
        devicePrinter.setSn(devicePrinterDto.getSn());
        devicePrinter.setKey(devicePrinterDto.getKey());
        devicePrinter.setName(devicePrinterDto.getName());
        devicePrinter.setSim(devicePrinterDto.getSim());

        devicePrinterDao.save(devicePrinter);
    }

    @Override
    public DevicePrinterStatus devicePrinterStatus(Long userId) throws BusinessException {
        DevicePrinter devicePrinter = devicePrinterDao.findByUserId(userId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_DEVICE_PRINTER_NOT_FOUND));
        return feieyunClient.devicePrinterStatus(devicePrinter.getSn());
    }

    @Override
    public void devicePrinterClear(Long userId) throws BusinessException {
        DevicePrinter devicePrinter = devicePrinterDao.findByUserId(userId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_DEVICE_PRINTER_NOT_FOUND));
        feieyunClient.devicePrinterClear(devicePrinter.getSn());
    }

    @Override
    public void printTemplate(String devicePrinterSn, String templateName, Map<String, Object> params) throws BusinessException {
        try (StringWriter fw = new StringWriter()) {
            freemarkerConfiguration.getTemplate(templateName).process(params, fw);
            String content = fw.toString();
            feieyunClient.print(devicePrinterSn, content);
        } catch (Exception e) {
            throw new BusinessException(WmsMessageConstant.ERROR_DEVICE_PRINTER_PRINT_FAILED, e);
        }
    }
}
