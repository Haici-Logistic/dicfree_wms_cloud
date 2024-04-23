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
package com.dicfree.ms.wms.repository.jpa.dao;

import cn.jzyunqi.common.support.hibernate.persistence.dao.BaseDao;
import com.dicfree.ms.wms.repository.jpa.entity.BoxArrivalOrder;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
public interface BoxArrivalOrderDao extends BaseDao<BoxArrivalOrder, Long> {

    /**
     * 查找未完成的整装箱入库订单（待处理/处理中）
     *
     * @return 未完成的整装箱入库订单
     */
    @Query("select ao from BoxArrivalOrder ao where ao.locationLock = true and ao.inboundStatus in (com.dicfree.ms.wms.common.enums.OrderStatus.PENDING, com.dicfree.ms.wms.common.enums.OrderStatus.IN_PROCESSING)")
    List<BoxArrivalOrder> findUndoBoxArrivalOrder();

    /**
     * 根据三方订单号查询订单
     *
     * @param uniqueNo 订单号
     * @return 整装箱入库订单
     */
    @Query("select ao from BoxArrivalOrder ao where ao.uniqueNo = ?1")
    Optional<BoxArrivalOrder> findByUniqueNo(String uniqueNo);

    /**
     * 根据供应商和三方订单号查询订单
     *
     * @param supplier     客户
     * @param thirdOrderNo 三方订单号
     * @return 整装箱入库订单
     */
    @Query("select ao from BoxArrivalOrder ao where ao.supplier = ?1 and ao.thirdOrderNo = ?2")
    Optional<BoxArrivalOrder> findBySupplierAndThirdOrderNo(String supplier, String thirdOrderNo);

    /**
     * 查找全部未排库的订单
     *
     * @return 查找全部未排库的订单
     */
    //@Query("select ao from BoxArrivalOrder ao where ao.locationLock = false and ao.arrivingDate is not null")
    @Query("select ao from BoxArrivalOrder ao where ao.locationLock = false")
    List<BoxArrivalOrder> findLocationUndoAll();
}
