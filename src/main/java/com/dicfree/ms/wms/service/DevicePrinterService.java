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
package com.dicfree.ms.wms.service;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.wms.common.dto.ex.ExDevicePrinterDto;
import com.dicfree.ms.wms.common.enums.DevicePrinterStatus;
import com.dicfree.ms.wms.common.enums.StocktakeType;

import java.util.Map;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
public interface DevicePrinterService {

    /**
     * 获取打印机信息
     *
     * @param userId 用户id
     */
    ExDevicePrinterDto devicePrinterInfo(Long userId) throws BusinessException;

    /**
     * 获取打印机SN
     *
     * @param userId 用户id
     */
    String devicePrinterSn(Long userId) throws BusinessException;

    /**
     * 获取打印机SN
     *
     * @param username 用户名
     */
    String devicePrinterSn(String username) throws BusinessException;

    /**
     * 绑定打印机
     *
     * @param devicePrinterDto 打印机信息
     */
    void devicePrinterBind(ExDevicePrinterDto devicePrinterDto) throws BusinessException;

    /**
     * 获取打印机状态
     *
     * @param userId 用户id
     * @return 打印机状态
     */
    DevicePrinterStatus devicePrinterStatus(Long userId) throws BusinessException;

    /**
     * 清除打印机状态
     *
     * @param userId 用户id
     */
    void devicePrinterClear(Long userId) throws BusinessException;

    /**
     * 打印数据
     *
     * @param devicePrinterSn 打印机SN
     * @param templateName    模板名称
     * @param params          模板参数
     */
    void printTemplate(String devicePrinterSn, String templateName, Map<String, Object> params) throws BusinessException;
}
