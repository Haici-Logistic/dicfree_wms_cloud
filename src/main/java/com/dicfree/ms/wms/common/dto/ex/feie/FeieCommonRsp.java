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
package com.dicfree.ms.wms.common.dto.ex.feie;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wiiyaya
 * @date 2023/7/19.
 */
@Getter
@Setter
@ToString
public class FeieCommonRsp<T> {

    /**
     * 返回码，正确返回0，【注意：结果正确与否的判断请用此返回参数】，错误返回非零。
     */
    private int ret;

    /**
     * 结果提示信息，正确返回”ok”，如果有错误，返回错误信息。
     */
    private String msg;

    /**
     * 数据类型和内容详看私有返回参数data，如果有错误，返回null。
     */
    private T data;

    /**
     * 服务器程序执行时间，单位：毫秒。
     */
    private int serverExecutedTime;
}
