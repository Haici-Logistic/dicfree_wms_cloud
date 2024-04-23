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
import cn.jzyunqi.ms.uaa.common.enums.AuthType;
import cn.jzyunqi.ms.uaa.service.UserAuthService;
import com.dicfree.ms.mct.common.dto.ex.ExMemberAddressDto;
import com.dicfree.ms.mct.service.MemberAddressService;
import com.dicfree.ms.si.service.ClientMemberAddressService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/26
 */
@Service("clientMemberAddressService")
public class ClientMemberAddressServiceImpl implements ClientMemberAddressService {

    @Resource
    private MemberAddressService memberAddressService;

    @Resource
    private UserAuthService userAuthService;

    @Override
    public List<ExMemberAddressDto> memberAddressList(String unionId) throws BusinessException {
        Long memberId = userAuthService.findIdByUsername(AuthType.WX_OPEN, unionId);
        return memberAddressService.memberAddressList(memberId);
    }

    @Override
    public void memberAddressAdd(String unionId, ExMemberAddressDto memberAddressDto) throws BusinessException {
        Long memberId = userAuthService.findIdByUsername(AuthType.WX_OPEN, unionId);
        memberAddressDto.setMemberId(memberId);
        memberAddressService.memberAddressAdd(memberAddressDto);
    }

    @Override
    public void memberAddressEdit(String unionId, ExMemberAddressDto memberAddressDto) throws BusinessException {
        Long memberId = userAuthService.findIdByUsername(AuthType.WX_OPEN, unionId);
        memberAddressDto.setMemberId(memberId);
        memberAddressService.memberAddressEdit(memberAddressDto);
    }

    @Override
    public ExMemberAddressDto memberAddressInfo(String unionId, Long memberAddressId) throws BusinessException {
        Long memberId = userAuthService.findIdByUsername(AuthType.WX_OPEN, unionId);
        return memberAddressService.memberAddressInfo(memberId, memberAddressId);
    }

    @Override
    public void memberAddressDelete(String unionId, Long memberAddressId) throws BusinessException {
        Long memberId = userAuthService.findIdByUsername(AuthType.WX_OPEN, unionId);
        memberAddressService.memberAddressDelete(memberId, memberAddressId);
    }

    @Override
    public Boolean memberAddressHasDefaultOne(String unionId) throws BusinessException {
        Long memberId = userAuthService.findIdByUsername(AuthType.WX_OPEN, unionId);
        return memberAddressService.memberAddressHasDefaultOne(memberId);
    }

    @Override
    public void memberAddressSetDefaultOne(String unionId, Long memberAddressId) throws BusinessException {
        Long memberId = userAuthService.findIdByUsername(AuthType.WX_OPEN, unionId);
        memberAddressService.memberAddressSetDefaultOne(memberId, memberAddressId);
    }
}
