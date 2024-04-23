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
package com.dicfree.ms.wms.common.dto.ex;

import com.dicfree.ms.wms.common.dto.ex.qingflow.QingFlowField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wiiyaya
 * @date 2023/11/4
 */
@Getter
@Setter
public class ExTracingDto {

    /**
     * 快递方向，如果是正常运输为“Forward”、逆向运输为“Return”
     */
    private String shipmentDirection;

    /**
     * 配送目的地
     */
    private String deliveryTo;

    /**
     * 快递当前的位置区域
     */
    private String scanLocation;

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 快递状态码
     */
    private String statusCode;

    /**
     * 快递状态说明
     */
    private String status;
}
