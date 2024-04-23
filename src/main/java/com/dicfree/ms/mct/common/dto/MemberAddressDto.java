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
package com.dicfree.ms.mct.common.dto;

import cn.jzyunqi.common.model.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author wiiyaya
 * @date 2023/11/26
 */
@Getter
@Setter
public class MemberAddressDto extends BaseDto<String, Long> {
    @Serial
    private static final long serialVersionUID = 565435965599751400L;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 收件人姓名
     */
    private String name;

    /**
     * 收件人主叫电话
     */
    private String phone;

    /**
     * 收件人省份
     */
    private String province;

    /**
     * 收件人城市
     */
    private String city;

    /**
     * 收件人详细地址
     */
    private String address;

    /**
     * 是否默认地址
     */
    private Boolean defaultOne;
}
