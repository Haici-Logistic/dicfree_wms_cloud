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
import cn.jzyunqi.common.model.PageDto;
import com.dicfree.ms.mct.common.dto.ex.ExMemberAddressDto;
import com.dicfree.ms.mct.common.dto.ex.query.ExMemberAddressQueryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/26
 */
public interface MemberAddressService {

    /**
     * 会员地址分页
     *
     * @param memberAddressQueryDto 查询条件
     * @param pageable              分页条件
     * @return 结果
     */
    PageDto<ExMemberAddressDto> memberAddressPage(ExMemberAddressQueryDto memberAddressQueryDto, Pageable pageable);

    /**
     * 会员地址列表
     *
     * @param memberId 会员id
     * @return 地址列表
     */
    List<ExMemberAddressDto> memberAddressList(Long memberId);

    /**
     * 会员地址添加
     *
     * @param memberAddressDto 会员地址信息
     */
    void memberAddressAdd(ExMemberAddressDto memberAddressDto) throws BusinessException;

    /**
     * 会员地址编辑初始化
     *
     * @param memberAddressId 会员地址id
     * @return 会员地址信息
     */
    ExMemberAddressDto memberAddressEditInit(Long memberAddressId) throws BusinessException;

    /**
     * 会员地址编辑
     *
     * @param memberAddressDto 会员地址信息
     */
    void memberAddressEdit(ExMemberAddressDto memberAddressDto) throws BusinessException;

    /**
     * 会员地址详情
     *
     * @param memberId        会员id
     * @param memberAddressId 会员地址id
     * @return 会员地址信息
     */
    ExMemberAddressDto memberAddressInfo(Long memberId, Long memberAddressId) throws BusinessException;

    /**
     * 会员地址删除
     *
     * @param memberId        会员id
     * @param memberAddressId 会员地址id
     */
    void memberAddressDelete(Long memberId, Long memberAddressId) throws BusinessException;

    /**
     * 查询是否有默认地址
     *
     * @param memberId 会员id
     * @return 是否有默认地址
     */
    Boolean memberAddressHasDefaultOne(Long memberId) throws BusinessException;

    /**
     * 设置默认地址
     * @param memberId 会员id
     * @param memberAddressId 地址id
     */
    void memberAddressSetDefaultOne(Long memberId, Long memberAddressId) throws BusinessException;

    /**
     * 查询默认地址
     *
     * @param memberId 会员id
     * @return 是否有默认地址
     */
    ExMemberAddressDto memberAddressDefaultOne(Long memberId) throws BusinessException;
}
