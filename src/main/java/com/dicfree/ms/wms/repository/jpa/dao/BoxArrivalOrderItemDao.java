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
import com.dicfree.ms.wms.repository.jpa.entity.BoxArrivalOrderItem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
public interface BoxArrivalOrderItemDao extends BaseDao<BoxArrivalOrderItem, Long> {

    /**
     * 查找整装箱入库订单明细
     *
     * @param boxArrivalOrderId 整装箱入库订单id
     * @return 整装箱入库订单明细
     */
    @Query("select aoi from BoxArrivalOrderItem aoi where aoi.boxArrivalOrderId = ?1")
    List<BoxArrivalOrderItem> findAllByArrivalOrderId(Long boxArrivalOrderId);

    /**
     * 查找整装箱入库订单对于的详情
     *
     * @param boxArrivalOrderId 整装箱入库订单id
     * @return 到货订单详情列表
     */
    @Query("select aoi from BoxArrivalOrderItem aoi where aoi.boxArrivalOrderId = ?1")
    List<BoxArrivalOrderItem> findAllByBoxArrivalOrderId(Long boxArrivalOrderId);

    /**
     * 查找整装箱入库订单未入库整装箱列表
     *
     * @param boxArrivalOrderId 整装箱入库订单id
     * @return 未入库整装箱列表
     */
    @Query("select aoi from BoxArrivalOrderItem aoi where aoi.boxArrivalOrderId = ?1 and aoi.inboundStatus in (com.dicfree.ms.wms.common.enums.OrderStatus.PENDING, com.dicfree.ms.wms.common.enums.OrderStatus.IN_PROCESSING) order by aoi.updateTime desc ")
    List<BoxArrivalOrderItem> findAllUndoByBoxArrivalOrderId(Long boxArrivalOrderId);

    /**
     * 查找整装箱入库订单已入库整装箱列表
     *
     * @param boxArrivalOrderId 整装箱入库订单id
     * @return 已入库整装箱列表
     */
    @Query("select aoi from BoxArrivalOrderItem aoi where aoi.boxArrivalOrderId = ?1 and aoi.inboundStatus= com.dicfree.ms.wms.common.enums.OrderStatus.DONE order by aoi.updateTime desc ")
    List<BoxArrivalOrderItem> findAllDoneByBoxArrivalOrderId(Long boxArrivalOrderId);

    /**
     * 查找整装箱入库订单详情
     *
     * @param id             整装箱入库订单详情id
     * @param boxArrivalOrderId 整装箱入库订单id
     * @return 整装箱入库订单详情
     */
    @Query("select aoi from BoxArrivalOrderItem aoi where aoi.id = ?1 and aoi.boxArrivalOrderId = ?2")
    Optional<BoxArrivalOrderItem> findByIdAndBoxArrivalOrderId(Long id, Long boxArrivalOrderId);

    /**
     * 查找整装箱入库订单详情
     *
     * @param boxArrivalOrderId 整装箱入库订单id
     * @param boxSkuCode        整装箱编码
     * @return 整装箱入库订单详情
     */
    @Query("select aoi from BoxArrivalOrderItem aoi where aoi.boxArrivalOrderId = ?1 and aoi.boxSkuCode = ?2")
    Optional<BoxArrivalOrderItem> findByBoxArrivalOrderIdAndBoxSkuCode(Long boxArrivalOrderId, String boxSkuCode);

    /**
     * 查找混库列表
     *
     * @param boxArrivalOrderId 整装箱入库订单id
     * @return 混库列表
     */
    @Query("select aoi from BoxArrivalOrderItem aoi where aoi.boxArrivalOrderId = ?1 and aoi.locationType= com.dicfree.ms.wms.common.enums.LocationType.Bulk")
    List<BoxArrivalOrderItem> findAllBulkByBoxArrivalOrderId(Long boxArrivalOrderId);

    /**
     * 查找订单详情
     *
     * @param boxSkuCode 整装箱编码
     * @return 订单详情
     */
    @Query("select aoi from BoxArrivalOrderItem aoi where aoi.boxSkuCode = ?1")
    Optional<BoxArrivalOrderItem> findByBoxSkuCode(String boxSkuCode);
}
