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
 * @date 2023/07/17
 */
@Getter
@Setter
public class DevicePrinterDto extends BaseDto<String, Long> {
    @Serial
    private static final long serialVersionUID = 515521171155351550L;

    /**
     * 打印机绑定用户id
     */
    private Long userId;

    /**
     * 打印机绑定用户名
     */
    private String username;

    /**
     * 打印机编码
     */
    private String sn;

    /**
     * 打印机识别码
     */
    private String key;

    /**
     * 打印机名称
     */
    private String name;

    /**
     * 打印机sim卡
     */
    private String sim;
}
