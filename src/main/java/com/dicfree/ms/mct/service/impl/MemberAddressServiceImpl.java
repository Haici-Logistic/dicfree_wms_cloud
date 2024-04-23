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
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import com.dicfree.ms.mct.common.constant.MctMessageConstant;
import com.dicfree.ms.mct.common.dto.ex.ExMemberAddressDto;
import com.dicfree.ms.mct.common.dto.ex.ExMemberDto;
import com.dicfree.ms.mct.common.dto.ex.query.ExMemberAddressQueryDto;
import com.dicfree.ms.mct.repository.jpa.dao.MemberAddressDao;
import com.dicfree.ms.mct.repository.jpa.dao.querydsl.MemberAddressQry;
import com.dicfree.ms.mct.repository.jpa.entity.MemberAddress;
import com.dicfree.ms.mct.service.MemberAddressService;
import com.dicfree.ms.mct.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wiiyaya
 * @date 2023/11/26
 */
@Service("memberAddressService")
public class MemberAddressServiceImpl implements MemberAddressService {

    @Resource
    private MemberAddressDao memberAddressDao;

    @Resource
    private MemberService memberService;

    @Override
    public PageDto<ExMemberAddressDto> memberAddressPage(ExMemberAddressQueryDto memberAddressQueryDto, Pageable pageable) {
        Page<MemberAddress> memberAddressPage = memberAddressDao.findAll(MemberAddressQry.searchMemberAddress(memberAddressQueryDto), MemberAddressQry.searchMemberAddressOrder(pageable));

        List<ExMemberAddressDto> memberAddressDtoList = memberAddressPage.stream().map(memberAddress -> {
            ExMemberAddressDto memberAddressDto = new ExMemberAddressDto();
            //TODO replace this
            memberAddressDto = BeanUtilPlus.copyAs(memberAddress, ExMemberAddressDto.class);
            return memberAddressDto;
        }).collect(Collectors.toList());

        return new PageDto<>(memberAddressDtoList, memberAddressPage.getTotalElements());
    }

    @Override
    public List<ExMemberAddressDto> memberAddressList(Long memberId) {
        List<MemberAddress> memberAddressList = memberAddressDao.findAllByMemberId(memberId);
        return memberAddressList.stream().map(memberAddress -> {
            ExMemberAddressDto memberAddressDto = new ExMemberAddressDto();
            memberAddressDto.setId(memberAddress.getId());
            memberAddressDto.setName(memberAddress.getName());
            memberAddressDto.setPhone(memberAddress.getPhone());
            memberAddressDto.setProvince(memberAddress.getProvince());
            memberAddressDto.setCity(memberAddress.getCity());
            memberAddressDto.setAddress(memberAddress.getAddress());
            memberAddressDto.setDefaultOne(memberAddress.getDefaultOne());
            return memberAddressDto;
        }).toList();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void memberAddressAdd(ExMemberAddressDto memberAddressDto) throws BusinessException {
        ExMemberDto memberDto = memberService.memberInfoById(memberAddressDto.getMemberId());

        MemberAddress memberAddress = new MemberAddress();
        memberAddress.setMemberId(memberDto.getId());
        memberAddress.setName(memberAddressDto.getName());
        memberAddress.setPhone(memberAddressDto.getPhone());
        memberAddress.setProvince(memberAddressDto.getProvince());
        memberAddress.setCity(memberAddressDto.getCity());
        memberAddress.setAddress(memberAddressDto.getAddress());
        memberAddress.setDefaultOne(memberAddressDto.getDefaultOne());

        MemberAddress defaultAddress = memberAddressDao.findDefaultByMemberId(memberDto.getId()).orElse(null);
        //查询默认地址,如果有,则取消默认地址
        if (defaultAddress == null) {
            memberAddress.setDefaultOne(Boolean.TRUE);
        } else if (memberAddress.getDefaultOne()) {
            defaultAddress.setDefaultOne(Boolean.FALSE);
            memberAddressDao.save(defaultAddress);
        }
        memberAddressDao.save(memberAddress);
    }

    @Override
    public ExMemberAddressDto memberAddressEditInit(Long memberAddressId) throws BusinessException {
        MemberAddress memberAddress = memberAddressDao.findById(memberAddressId).orElseThrow(() -> new BusinessException(MctMessageConstant.ERROR_MEMBER_ADDRESS_NOT_FOUND));

        ExMemberAddressDto memberAddressDto = new ExMemberAddressDto();
        memberAddressDto.setId(memberAddress.getId());
        return memberAddressDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void memberAddressEdit(ExMemberAddressDto memberAddressDto) throws BusinessException {
        MemberAddress memberAddress = memberAddressDao.findByIdAndMemberId(memberAddressDto.getId(), memberAddressDto.getMemberId()).orElseThrow(() -> new BusinessException(MctMessageConstant.ERROR_MEMBER_ADDRESS_NOT_FOUND));
        memberAddress.setName(memberAddressDto.getName());
        memberAddress.setPhone(memberAddressDto.getPhone());
        memberAddress.setProvince(memberAddressDto.getProvince());
        memberAddress.setCity(memberAddressDto.getCity());
        memberAddress.setAddress(memberAddressDto.getAddress());

        memberAddressDao.save(memberAddress);
    }

    @Override
    public ExMemberAddressDto memberAddressInfo(Long memberId, Long memberAddressId) throws BusinessException {
        MemberAddress memberAddress = memberAddressDao.findByIdAndMemberId(memberAddressId, memberId).orElseThrow(() -> new BusinessException(MctMessageConstant.ERROR_MEMBER_ADDRESS_NOT_FOUND));

        ExMemberAddressDto memberAddressDto = new ExMemberAddressDto();
        memberAddressDto.setId(memberAddress.getId());
        memberAddressDto.setName(memberAddress.getName());
        memberAddressDto.setPhone(memberAddress.getPhone());
        memberAddressDto.setProvince(memberAddress.getProvince());
        memberAddressDto.setCity(memberAddress.getCity());
        memberAddressDto.setAddress(memberAddress.getAddress());
        memberAddressDto.setDefaultOne(memberAddress.getDefaultOne());
        return memberAddressDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void memberAddressDelete(Long memberId, Long memberAddressId) throws BusinessException {
        MemberAddress memberAddress = memberAddressDao.findByIdAndMemberId(memberAddressId, memberId).orElseThrow(() -> new BusinessException(MctMessageConstant.ERROR_MEMBER_ADDRESS_NOT_FOUND));

        if(memberAddress.getDefaultOne()){
            throw new BusinessException(MctMessageConstant.ERROR_MEMBER_ADDRESS_DEFAULT_ONE_CANNOT_DELETE);
        }
        memberAddressDao.delete(memberAddress);
    }

    @Override
    public Boolean memberAddressHasDefaultOne(Long memberId) throws BusinessException {
        MemberAddress defaultAddress = memberAddressDao.findDefaultByMemberId(memberId).orElse(null);
        return defaultAddress != null;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void memberAddressSetDefaultOne(Long memberId, Long memberAddressId) throws BusinessException {
        MemberAddress memberAddress = memberAddressDao.findByIdAndMemberId(memberAddressId, memberId).orElseThrow(() -> new BusinessException(MctMessageConstant.ERROR_MEMBER_ADDRESS_NOT_FOUND));
        if(!memberAddress.getDefaultOne()){
            MemberAddress defaultAddress = memberAddressDao.findDefaultByMemberId(memberId).orElse(null);
            //查询默认地址,如果有,则取消默认地址
            if (defaultAddress != null) {
                defaultAddress.setDefaultOne(Boolean.FALSE);
                memberAddressDao.save(defaultAddress);
            }
            memberAddress.setDefaultOne(Boolean.TRUE);
            memberAddressDao.save(memberAddress);
        }
    }

    @Override
    public ExMemberAddressDto memberAddressDefaultOne(Long memberId) throws BusinessException {
        MemberAddress memberAddress = memberAddressDao.findDefaultByMemberId(memberId).orElseThrow(() -> new BusinessException(MctMessageConstant.ERROR_MEMBER_ADDRESS_NOT_FOUND));
        ExMemberAddressDto memberAddressDto = new ExMemberAddressDto();
        memberAddressDto.setId(memberAddress.getId());
        memberAddressDto.setName(memberAddress.getName());
        memberAddressDto.setPhone(memberAddress.getPhone());
        memberAddressDto.setProvince(memberAddress.getProvince());
        memberAddressDto.setCity(memberAddress.getCity());
        memberAddressDto.setAddress(memberAddress.getAddress());
        return memberAddressDto;
    }
}
