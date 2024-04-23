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
import com.dicfree.ms.wms.repository.jpa.entity.BoxDeliveryOrder;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
public interface BoxDeliveryOrderDao extends BaseDao<BoxDeliveryOrder, Long> {

    /**
     * 查询指定目的地整装箱出库订单(未关联任务且未处理)
     *
     * @param collectionTaskId 集货任务id
     * @return 整装箱出库订单列表
     */
    @Query("select do from BoxDeliveryOrder do where (do.collectionTaskId is null or do.collectionTaskId = ?1) and do.outboundStatus = com.dicfree.ms.wms.common.enums.OrderStatus.PENDING")
    List<BoxDeliveryOrder> findAllWaitingByCollectionTaskId(Long collectionTaskId);

    /**
     * 获取集货任务下全部整装箱出库订单
     *
     * @param collectionTaskId 集货任务id
     * @return 整装箱出库订单列表
     */
    @Query("select do from BoxDeliveryOrder do where do.collectionTaskId = ?1")
    List<BoxDeliveryOrder> findAllByCollectionTaskId(Long collectionTaskId);

    /**
     * 根据发货日期查找整装箱出库订单
     *
     * @param boxDeliveryOrderIdList 整装箱出库订单ID列表
     * @return 整装箱出库订单列表
     */
    @Query("select do from BoxDeliveryOrder do where do.id in (?1)")
    List<BoxDeliveryOrder> findAllByIdIn(List<Long> boxDeliveryOrderIdList);

    /**
     * 查找整装箱出库订单
     *
     * @param uniqueNo 订单号
     * @return 整装箱出库订单
     */
    @Query("select do from BoxDeliveryOrder do where do.uniqueNo = ?1")
    Optional<BoxDeliveryOrder> findByUniqueNo(String uniqueNo);

    /**
     * 查找整装箱出库订单
     *
     * @param supplier     客户
     * @param thirdOrderNo 三方订单号
     * @return 整装箱出库订单
     */
    @Query("select do from BoxDeliveryOrder do where do.supplier = ?1 and do.thirdOrderNo = ?2")
    Optional<BoxDeliveryOrder> findBySupplierAndThirdOrderNo(String supplier, String thirdOrderNo);
}
