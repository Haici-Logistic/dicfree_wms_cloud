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
import com.dicfree.ms.wms.repository.jpa.entity.ProductArrivalOrderItem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
public interface ProductArrivalOrderItemDao extends BaseDao<ProductArrivalOrderItem, Long> {

    /**
     * 根据到货单ID查询到货单明细
     *
     * @param arrivalOrderId 到货单ID
     * @return 到货单明细
     */
    @Query("select aoi from ProductArrivalOrderItem aoi where aoi.productArrivalOrderId = ?1")
    List<ProductArrivalOrderItem> findAllByArrivalOrderId(Long arrivalOrderId);

    /**
     * 查找到货单未入库列表
     *
     * @param arrivalOrderId 到货单id
     * @return 未入库列表
     */
    @Query("select aoi from ProductArrivalOrderItem aoi where aoi.productArrivalOrderId = ?1 and aoi.inboundStatus != com.dicfree.ms.wms.common.enums.OrderStatus.DONE order by aoi.updateTime desc ")
    List<ProductArrivalOrderItem> findAllUndoByProductArrivalOrderId(Long arrivalOrderId);

    /**
     * 查找到货单已入库列表
     *
     * @param arrivalOrderId 到货单id
     * @return 已入库列表
     */
    @Query("select aoi from ProductArrivalOrderItem aoi where aoi.productArrivalOrderId = ?1 and aoi.inboundStatus = com.dicfree.ms.wms.common.enums.OrderStatus.DONE order by aoi.updateTime desc ")
    List<ProductArrivalOrderItem> findAllDoneByProductArrivalOrderId(Long arrivalOrderId);

    /**
     * 查找到货单详情
     *
     * @param id             到货单详情id
     * @param arrivalOrderId 到货单id
     * @return 到货单详情
     */
    @Query("select aoi from ProductArrivalOrderItem aoi where aoi.id = ?1 and aoi.productArrivalOrderId = ?2")
    Optional<ProductArrivalOrderItem> findByIdAndProductArrivalOrderId(Long id, Long arrivalOrderId);

    /**
     * 根据到货单ID和商品号查询到货单明细
     *
     * @param skuCode        电商商品编码
     * @return 到货单明细
     */
    @Query("select aoi from ProductArrivalOrderItem aoi where aoi.productSkuCode = ?1 and aoi.inboundStatus != com.dicfree.ms.wms.common.enums.OrderStatus.DONE order by aoi.updateTime desc ")
    Optional<ProductArrivalOrderItem> findUndoBySkuCode(String skuCode);
}
