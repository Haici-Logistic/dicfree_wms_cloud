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
package com.dicfree.ms.si.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.weixin.model.response.UserTokenRsp;
import cn.jzyunqi.ms.uaa.common.dto.ex.ExUserDto;
import cn.jzyunqi.ms.uaa.common.enums.AuthType;
import cn.jzyunqi.ms.uaa.service.UserAuthService;
import com.dicfree.ms.mct.common.dto.ex.ExMemberDto;
import com.dicfree.ms.mct.service.MemberService;
import com.dicfree.ms.si.service.ClientMemberService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

/**
 * @author wiiyaya
 * @date 2023/11/26
 */
@Service("clientMemberService")
public class ClientMemberServiceImpl implements ClientMemberService {

    @Resource
    private MemberService memberService;

    @Resource
    private UserAuthService userAuthService;

    @Override
    public void memberAdd(String unionId, String openId) throws BusinessException {
        UserTokenRsp userTokenRsp = new UserTokenRsp();
        userTokenRsp.setUnionId(unionId);
        userTokenRsp.setOpenId(openId);
        memberService.memberAddInternal(userTokenRsp);
    }

    @Override
    public void memberEdit(@NotBlank String unionId, ExMemberDto memberDto) throws BusinessException {
        Long memberId = userAuthService.findIdByUsername(AuthType.WX_OPEN, unionId);
        memberDto.setId(memberId);
        memberService.memberEdit(memberDto);
    }

    @Override
    public ExMemberDto memberInfo(String unionId) throws BusinessException {
        ExUserDto userDto = userAuthService.userAuthInfoByUsername(AuthType.WX_OPEN, unionId);
        ExMemberDto memberDto = memberService.memberInfoById(userDto.getId());
        memberDto.setId(null);
        memberDto.setUid(userDto.getUid());
        return memberDto;
    }
}
