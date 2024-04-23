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
package com.dicfree.ms.si.service;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.mct.common.dto.ex.ExMemberDto;
import jakarta.validation.constraints.NotBlank;

/**
 * @author wiiyaya
 * @date 2023/11/26
 */
public interface ClientMemberService {

    /**
     * 创建小程序会员
     *
     * @param unionId 小程序unionId
     * @param openId  小程序openId
     */
    void memberAdd(String unionId, String openId) throws BusinessException;

    /**
     * 修改会员信息
     *
     * @param unionId   小程序unionId
     * @param memberDto 会员信息
     */
    void memberEdit(String unionId, ExMemberDto memberDto) throws BusinessException;

    /**
     * 查看会员信息
     *
     * @param unionId 小程序unionId
     * @return 会员信息
     */
    ExMemberDto memberInfo(String unionId) throws BusinessException;
}
