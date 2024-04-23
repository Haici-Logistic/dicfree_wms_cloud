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
import com.dicfree.ms.wms.repository.jpa.entity.BoxDeliveryOrderItem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
public interface BoxDeliveryOrderItemDao extends BaseDao<BoxDeliveryOrderItem, Long> {

    /**
     * 查找整装箱出库订单详情列表
     *
     * @param boxDeliveryOrderId 整装箱出库订单id
     * @return 订单详情列表
     */
    @Query("select doi from BoxDeliveryOrderItem doi where doi.boxDeliveryOrderId = ?1")
    List<BoxDeliveryOrderItem> findAllByBoxDeliveryOrderId(Long boxDeliveryOrderId);

    /**
     * 查找集货任务中全部整装箱出库订单详情列表
     *
     * @param collectionTaskId 集货任务id
     * @return 订单详情列表
     */
    @Query("select doi from BoxDeliveryOrderItem doi where doi.collectionTaskId = ?1")
    List<BoxDeliveryOrderItem> findAllByCollectionTaskId(Long collectionTaskId);

    /**
     * 查找集货任务中全部整装箱出库订单中未入库整装箱列表
     *
     * @param collectionTaskId 集货任务id
     * @return 未入库整装箱列表
     */
    @Query("select doi from BoxDeliveryOrderItem doi where doi.collectionTaskId = ?1 and doi.outboundStatus in (com.dicfree.ms.wms.common.enums.OrderStatus.PENDING, com.dicfree.ms.wms.common.enums.OrderStatus.IN_PROCESSING) order by doi.updateTime desc ")
    List<BoxDeliveryOrderItem> findAllUndoByCollectionTaskId(Long collectionTaskId);

    /**
     * 查找集货任务中全部整装箱出库订单中已入库整装箱列表
     *
     * @param collectionTaskId 集货任务id
     * @return 已入库整装箱列表
     */
    @Query("select doi from BoxDeliveryOrderItem doi where doi.collectionTaskId = ?1 and doi.outboundStatus = com.dicfree.ms.wms.common.enums.OrderStatus.DONE")
    List<BoxDeliveryOrderItem> findAllDoneByCollectionTaskId(Long collectionTaskId);

    /**
     * 查找整装箱出库订单详情
     *
     * @param id              整装箱出库订单详情id
     * @param boxDeliveryOrderId 整装箱出库订单id
     * @param collectionTaskId 集货任务id
     * @return 整装箱出库订单详情
     */
    @Query("select doi from BoxDeliveryOrderItem doi where doi.id = ?1 and doi.boxDeliveryOrderId = ?2 and doi.collectionTaskId = ?3")
    Optional<BoxDeliveryOrderItem> findByIdAndBoxDeliveryOrderIdAndCollectionTaskId(Long id, Long boxDeliveryOrderId, Long collectionTaskId);

    /**
     * 统计所有未完成订单的待出库数量
     * @param boxSkuCode 序列箱号
     * @return 统计数量
     */
    @Query("select doi from BoxDeliveryOrderItem doi where doi.boxSkuCode = ?1 and doi.outboundStatus != com.dicfree.ms.wms.common.enums.OrderStatus.DONE")
    List<BoxDeliveryOrderItem> findAllNotDone(String boxSkuCode);

    /**
     * 查找整装箱详情
     *
     * @param collectionTaskId 集货任务id
     * @param boxSkuCode 整装项编码
     * @return 整装箱详情
     */
    @Query("select doi from BoxDeliveryOrderItem doi where doi.collectionTaskId = ?1 and doi.boxSkuCode = ?2")
    Optional<BoxDeliveryOrderItem> findByCollectionTaskIdAndBoxSkuCode(Long collectionTaskId, String boxSkuCode);
}
