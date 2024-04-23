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
package com.dicfree.ms.mct.repository.jpa.dao;

import cn.jzyunqi.common.support.hibernate.persistence.dao.BaseDao;
import com.dicfree.ms.mct.repository.jpa.entity.MemberAddress;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/11/26
 */
public interface MemberAddressDao extends BaseDao<MemberAddress, Long> {

    /**
     * 根据默认地址查询
     *
     * @param memberId 会员id
     * @return 默认地址
     */
    @Query("select ma from MemberAddress ma where ma.defaultOne = true and ma.memberId = ?1")
    Optional<MemberAddress> findDefaultByMemberId(Long memberId);

    /**
     * 根据地址id和会员id查询
     *
     * @param id       地址id
     * @param memberId 会员id
     * @return 地址信息
     */
    @Query("select ma from MemberAddress ma where ma.id = ?1 and ma.memberId = ?2")
    Optional<MemberAddress> findByIdAndMemberId(Long id, Long memberId);

    /**
     * 根据会员id查询
     *
     * @param memberId 会员id
     * @return 地址列表
     */
    @Query("select ma from MemberAddress ma where ma.memberId = ?1 order by ma.defaultOne desc, ma.createTime desc")
    List<MemberAddress> findAllByMemberId(Long memberId);
}
