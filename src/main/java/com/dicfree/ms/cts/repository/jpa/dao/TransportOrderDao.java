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
package com.dicfree.ms.cts.repository.jpa.dao;

import cn.jzyunqi.common.support.hibernate.persistence.dao.BaseDao;
import com.dicfree.ms.cts.common.enums.TransportOrderStatus;
import com.dicfree.ms.cts.repository.jpa.entity.TransportOrder;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/11/28
 */
public interface TransportOrderDao extends BaseDao<TransportOrder, Long> {

    /**
     * 根据会员ID和运单号查询转运订单
     *
     * @param memberId 会员ID
     * @param waybill 运单号
     * @return 转运订单
     */
    @Query("select t from TransportOrder t where t.memberId = ?1 and t.waybill = ?2")
    Optional<TransportOrder> findByMemberIdAndWaybill(Long memberId, String waybill);

    /**
     * 根据会员ID查询转运订单
     *
     * @param memberId 会员ID
     * @param status 订单状态
     * @return 转运订单
     */
    @Query("select t from TransportOrder t where t.memberId = ?1 and t.status = ?2")
    List<TransportOrder> findAllByMemberIdAndStatus(Long memberId, TransportOrderStatus status);
}
