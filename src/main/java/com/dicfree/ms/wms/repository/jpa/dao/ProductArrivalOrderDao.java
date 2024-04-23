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
import com.dicfree.ms.wms.repository.jpa.entity.ProductArrivalOrder;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
public interface ProductArrivalOrderDao extends BaseDao<ProductArrivalOrder, Long> {

    /**
     * 根据外部订单编号检查是否已经存在相关数据
     *
     * @param thirdOrderNo 外部订单编号
     * @return 是否存在
     */
    @Query("select count(ao)>0 from ProductArrivalOrder ao where ao.thirdOrderNo = ?1")
    boolean isThirdOrderNoExists(String thirdOrderNo);

    /**
     * 根据第三方订单号查询电商商品到货单
     *
     * @param thirdOrderNo 第三方订单号
     * @return 电商商品到货单
     */
    @Query("select ao from ProductArrivalOrder ao where ao.thirdOrderNo = ?1")
    Optional<ProductArrivalOrder> findByThirdOrderNo(String thirdOrderNo);

    /**
     * 查找未完成入库的电商商品到货单（待处理/处理中）
     *
     * @return 未完成入库的电商商品到货单
     */
    @Query("select ao from ProductArrivalOrder ao where ao.inboundStatus in (com.dicfree.ms.wms.common.enums.OrderStatus.PENDING, com.dicfree.ms.wms.common.enums.OrderStatus.IN_PROCESSING)")
    List<ProductArrivalOrder> findAllInboundUndo();

    /**
     * 查找未完成上架的电商商品到货单（待处理/处理中）
     *
     * @return 未完成上架的电商商品到货单
     */
    @Query("select ao from ProductArrivalOrder ao where ao.inboundStatus=com.dicfree.ms.wms.common.enums.OrderStatus.DONE and ao.onShelfStatus in (com.dicfree.ms.wms.common.enums.OrderStatus.PENDING, com.dicfree.ms.wms.common.enums.OrderStatus.IN_PROCESSING)")
    List<ProductArrivalOrder> findAllOnShelfUndo();
}
