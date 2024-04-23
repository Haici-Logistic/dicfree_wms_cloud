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
package com.dicfree.ms.wms.common.enums;

import cn.jzyunqi.common.utils.CollectionUtilPlus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wiiyaya
 * @date 2023/07/24
 */
@AllArgsConstructor
@Slf4j
public enum DevicePrinterStatus {

    /**
     * 在线，工作状态正常
     */
    ONLINE(new String[]{"The online working condition is normal","在线，工作状态正常。"}),

    /**
     * 离线
     */
    OFFLINE(new String[]{"off-line","离线。"}),

    /**
     * 在线，工作状态不正常
     */
    ERROR(new String[]{"error"}),

    ;

    private final String[] apiResult;

    public static DevicePrinterStatus toDevicePrinterStatus(String apiResult){
        for (DevicePrinterStatus value : DevicePrinterStatus.values()) {
            if(CollectionUtilPlus.Array.contains(value.apiResult, apiResult)){
                return value;
            }
        }
        log.error("====[{}] toDevicePrinterStatus error!", apiResult);
        return ERROR;
    }
}
