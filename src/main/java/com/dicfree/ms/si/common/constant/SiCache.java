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
package com.dicfree.ms.si.common.constant;

import cn.jzyunqi.common.feature.redis.Cache;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
@Getter
@AllArgsConstructor
public enum SiCache implements Cache {

    /**
     * lark 租户token
     */
    SI_LARK_TENANT_TOKEN_V(7100L, Boolean.TRUE),

    /**
     * lark 租户token获取时的锁
     */
    SI_LARK_TENANT_TOKEN_LOCK_H(0L, Boolean.FALSE),
    ;

    private final Long expiration;

    private final Boolean autoRenew;
}
