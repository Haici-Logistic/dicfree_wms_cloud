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
package com.dicfree.ms.wms.common.dto.ex.lark;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wiiyaya
 * @date 2023/9/18
 */
@Getter
@Setter
@ToString
public class LarkCommonRsp<T> {
    /**
     * 返回码，正确返回0，
     */
    private int code;

    /**
     * 结果提示信息
     */
    private String msg;

    /**
     * 数据类型和内容详看私有返回参数data
     */
    private T data;

    /**
     * 访问 token
     */
    @JsonProperty("tenant_access_token")
    private String tenantAccessToken;

    /**
     *
     * token 过期时间，单位: 秒
     */
    private Long expire;
}
