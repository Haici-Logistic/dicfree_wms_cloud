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
package com.dicfree.ms.mct.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.feature.redis.RedisHelper;
import cn.jzyunqi.common.third.weixin.model.enums.WeixinType;
import cn.jzyunqi.common.third.weixin.model.response.UserTokenRsp;
import cn.jzyunqi.common.utils.StringUtilPlus;
import cn.jzyunqi.ms.sys.common.dto.ParamRedisDto;
import cn.jzyunqi.ms.sys.service.ParamService;
import cn.jzyunqi.ms.uaa.common.dto.UserDto;
import cn.jzyunqi.ms.uaa.common.enums.AuthType;
import cn.jzyunqi.ms.uaa.common.enums.UserType;
import cn.jzyunqi.ms.uaa.service.UserAuthService;
import com.dicfree.ms.mct.common.constant.MctCache;
import com.dicfree.ms.mct.common.constant.MctMessageConstant;
import com.dicfree.ms.mct.common.dto.ex.ExMemberDto;
import com.dicfree.ms.mct.repository.jpa.dao.MemberDao;
import com.dicfree.ms.mct.repository.jpa.entity.Member;
import com.dicfree.ms.mct.service.MemberService;
import com.dicfree.ms.mct.service.WeixinService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wiiyaya
 * @date 2018/5/22.
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberDao memberDao;

    @Resource
    private UserAuthService userAuthService;

    @Resource
    private ParamService paramService;

    @Resource
    private WeixinService weixinService;

    @Resource
    private RedisHelper redisHelper;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void memberAddInternal(UserTokenRsp userTokenRsp) throws BusinessException {
        //2. 查询会员是否存在
        Long userId = userAuthService.findIdByUsernameWithNull(AuthType.WX_OPEN, userTokenRsp.getUnionId());
        if (userId == null) {
            //注册用户
            UserDto userDto = userAuthService.createUser(UserType.F, AuthType.WX_OPEN, userTokenRsp.getUnionId(), null);
            //注册会员
            createMember(userDto);
            //创建其它基础信息 - 微信-会员关系
            weixinService.modifyWeixin(WeixinType.MP, userDto.getId(), userTokenRsp, Boolean.TRUE);
        }
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void memberEdit(ExMemberDto memberDto) throws BusinessException {
        Member member = memberDao.findById(memberDto.getId()).orElseThrow(() -> new BusinessException(MctMessageConstant.ERROR_MEMBER_NOT_FOUND));
        if (StringUtilPlus.isNotBlank(memberDto.getNickname())) {
            member.setNickname(memberDto.getNickname());
        }
        if (StringUtilPlus.isNotBlank(memberDto.getPhone())) {
            member.setPhone(memberDto.getPhone());
        }
        memberDao.save(member);
    }

    @Override
    public ExMemberDto memberInfoById(Long memberId) throws BusinessException {
        return memberDao.findById(memberId).map(this::prepareMemberInfoDto).orElseThrow(() -> new BusinessException(MctMessageConstant.ERROR_MEMBER_NOT_FOUND));
    }

    @Override
    public ExMemberDto memberInfoByNavigationNoWithNull(String navigationNo) throws BusinessException {
        return memberDao.findByIdNavigationNo(navigationNo).map(this::prepareMemberInfoDto).orElse(null);
    }

    @Override
    public ExMemberDto memberInfoByAviationNoWithNull(String aviationNo) throws BusinessException {
        return memberDao.findByIdAviationNo(aviationNo).map(this::prepareMemberInfoDto).orElse(null);
    }

    /**
     * 创建会员
     *
     * @param userDto 用户信息
     */
    private void createMember(UserDto userDto) throws BusinessException {
        ParamRedisDto defaultAvatar = paramService.getParamByCode(() -> "SYS_DEFAULT_AVATAR");
        long navigationNo = redisHelper.vIncrement(MctCache.MCT_MEMBER_NAVIGATION_NO_V, RedisHelper.COMMON_KEY, 1L);
        long aviationNo = redisHelper.vIncrement(MctCache.MCT_MEMBER_AVIATION_NO_V, RedisHelper.COMMON_KEY, 1L);

        Member member = new Member();
        member.setId(userDto.getId());
        member.setNavigationNo(StringUtilPlus.leftPad(navigationNo, 6, '0'));
        member.setAviationNo(StringUtilPlus.leftPad(aviationNo, 6, '0'));
        member.setNickname(null);
        member.setAvatar(defaultAvatar.getValue());
        memberDao.save(member);
    }

    /**
     * 准备会员信息
     *
     * @param member 会员信息
     * @return 会员信息
     */
    private ExMemberDto prepareMemberInfoDto(Member member) {
        ExMemberDto memberDto = new ExMemberDto();
        memberDto.setId(member.getId());
        memberDto.setNavigationNo(member.getNavigationNo());
        memberDto.setAviationNo(member.getAviationNo());
        memberDto.setNickname(member.getNickname());
        memberDto.setPhone(member.getPhone());
        return memberDto;
    }
}
