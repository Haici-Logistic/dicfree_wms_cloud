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
import com.dicfree.ms.mct.repository.jpa.entity.Member;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface MemberDao extends BaseDao<Member, Long> {

    /**
     * 根据会员海运编号查询会员信息
     *
     * @param navigationNo 海运编号
     * @return 会员信息
     */
    @Query("select m from Member m where m.navigationNo = ?1")
    Optional<Member> findByIdNavigationNo(String navigationNo);

    /**
     * 根据会员航运编号查询会员信息
     *
     * @param aviationNo 航运编号
     * @return 会员信息
     */
    @Query("select m from Member m where m.aviationNo = ?1")
    Optional<Member> findByIdAviationNo(String aviationNo);
}
