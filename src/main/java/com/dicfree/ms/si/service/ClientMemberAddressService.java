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
import com.dicfree.ms.mct.common.dto.ex.ExMemberAddressDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/26
 */
public interface ClientMemberAddressService {

    /**
     * 会员地址列表
     *
     * @param unionId 小程序unionId
     * @return 结果
     */
    List<ExMemberAddressDto> memberAddressList(String unionId) throws BusinessException;

    /**
     * 会员地址新增
     *
     * @param unionId          小程序unionId
     * @param memberAddressDto 会员地址信息
     */
    void memberAddressAdd(String unionId, ExMemberAddressDto memberAddressDto) throws BusinessException;

    /**
     * 会员地址编辑
     *
     * @param unionId          小程序unionId
     * @param memberAddressDto 会员地址信息
     */
    void memberAddressEdit(String unionId, ExMemberAddressDto memberAddressDto) throws BusinessException;

    /**
     * 获取地址信息
     *
     * @param unionId   小程序unionId
     * @param memberAddressId 地址id
     * @return 地址信息
     */
    ExMemberAddressDto memberAddressInfo(String unionId, Long memberAddressId) throws BusinessException;

    /**
     * 会员地址删除
     *
     * @param unionId   小程序unionId
     * @param memberAddressId 地址id
     */
    void memberAddressDelete(String unionId, Long memberAddressId) throws BusinessException;

    /**
     * 查询是否有默认地址
     *
     * @param unionId 小程序unionId
     * @return 是否有默认地址
     */
    Boolean memberAddressHasDefaultOne(String unionId) throws BusinessException;

    /**
     * 设置默认地址
     *
     * @param unionId   小程序unionId
     * @param memberAddressId 地址id
     */
    void memberAddressSetDefaultOne(String unionId, Long memberAddressId) throws BusinessException;
}
