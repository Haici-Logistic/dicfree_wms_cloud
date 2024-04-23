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
import cn.jzyunqi.common.third.weixin.model.enums.WeixinType;
import com.dicfree.ms.mct.repository.jpa.entity.Weixin;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2021/4/30.
 */
public interface WeixinDao extends BaseDao<Weixin, Long> {

    /**
     * 根据会员id获取微信关系
     *
     * @param memberId 会员id
     * @param type     微信类型
     * @return 微信关系
     */
    @Query("select wx from Weixin wx where wx.memberId = ?1 and wx.type = ?2")
    Optional<Weixin> findByMemberIdAndType(Long memberId, WeixinType type);
}
