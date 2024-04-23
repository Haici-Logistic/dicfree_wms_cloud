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
package com.dicfree.ms.mct.service;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.weixin.model.response.UserTokenRsp;
import com.dicfree.ms.mct.common.dto.ex.ExMemberDto;

/**
 * @author wiiyaya
 * @date 2023/11/26
 */
public interface MemberService {

    /**
     * 创建小程序会员
     *
     * @param userTokenRsp 微信用户信息
     */
    void memberAddInternal(UserTokenRsp userTokenRsp) throws BusinessException;

    /**
     * 修改会员信息
     *
     * @param memberDto 会员信息
     */
    void memberEdit(ExMemberDto memberDto) throws BusinessException;

    /**
     * 查看会员信息
     *
     * @param memberId 会员id
     * @return 会员信息
     */
    ExMemberDto memberInfoById(Long memberId) throws BusinessException;

    /**
     * 查看会员信息
     *
     * @param navigationNo 海运编号
     * @return 会员信息
     */
    ExMemberDto memberInfoByNavigationNoWithNull(String navigationNo) throws BusinessException;

    /**
     * 查看会员信息
     *
     * @param aviationNo 航运编号
     * @return 会员信息
     */
    ExMemberDto memberInfoByAviationNoWithNull(String aviationNo) throws BusinessException;
}
