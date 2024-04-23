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
import com.dicfree.ms.wms.repository.jpa.entity.ProductDeliveryOrderItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/10/28
 */
public interface ProductDeliveryOrderItemDao extends BaseDao<ProductDeliveryOrderItem, Long> {

    /**
     * 根据电商发货单ID删除数据
     *
     * @param productDeliveryOrderId 电商发货单ID
     */
    @Modifying
    @Query("delete from ProductDeliveryOrderItem doi where doi.productDeliveryOrderId = ?1")
    void deleteByProductDeliveryOrderId(Long productDeliveryOrderId);

    /**
     * 根据电商发货单ID查询数据
     *
     * @param productDeliveryOrderId 电商发货单ID
     * @return 电商发货单明细列表
     */
    @Query("select doi from ProductDeliveryOrderItem doi where doi.productDeliveryOrderId = ?1")
    List<ProductDeliveryOrderItem> findAllByProductDeliveryOrderId(Long productDeliveryOrderId);

    /**
     * 根据电商发货单ID查询未完成的数据
     *
     * @param productDeliveryOrderId 电商发货单ID
     * @return 电商发货单明细列表
     */
    @Query("select doi from ProductDeliveryOrderItem doi where doi.productDeliveryOrderId = ?1 and doi.verifyStatus != com.dicfree.ms.wms.common.enums.OrderStatus.DONE order by doi.updateTime desc ")
    List<ProductDeliveryOrderItem> findAllVerifyUndoByProductDeliveryOrderId(Long productDeliveryOrderId);

    /**
     * 根据电商发货单ID查询已完成的数据
     *
     * @param productDeliveryOrderId 电商发货单ID
     * @return 电商发货单明细列表
     */
    @Query("select doi from ProductDeliveryOrderItem doi where doi.productDeliveryOrderId = ?1 and doi.verifyStatus = com.dicfree.ms.wms.common.enums.OrderStatus.DONE order by doi.updateTime desc ")
    List<ProductDeliveryOrderItem> findAllVerifyDoneByProductDeliveryOrderId(Long productDeliveryOrderId);

    /**
     * 查询订单详情信息
     *
     * @param id                     订单详情ID
     * @param productDeliveryOrderId 电商发货单ID
     * @return 订单详情信息
     */
    @Query("select doi from ProductDeliveryOrderItem doi where doi.id = ?1 and doi.productDeliveryOrderId = ?2")
    Optional<ProductDeliveryOrderItem> findByIdAndProductDeliveryOrderId(Long id, Long productDeliveryOrderId);

    /**
     * 根据电商发货单ID和商品SKU编码查询数据
     *
     * @param productDeliveryOrderId 电商发货单ID
     * @param productSkuCode         商品SKU编码
     * @return 电商发货单明细
     */
    @Query("select doi from ProductDeliveryOrderItem doi where doi.productDeliveryOrderId = ?1 and doi.productSkuCode = ?2")
    Optional<ProductDeliveryOrderItem> findByProductDeliveryOrderIdAndProductSkuCode(Long productDeliveryOrderId, String productSkuCode);

    /**
     * 查找未完成的下架数据
     *
     * @param productDeliveryOrderId 电商发货单ID
     * @return 电商发货单明细列表
     */
    @Query("select doi from ProductDeliveryOrderItem doi where doi.productDeliveryOrderId = ?1 and doi.offShelfStatus != com.dicfree.ms.wms.common.enums.OrderStatus.DONE")
    List<ProductDeliveryOrderItem> findAllOffShelfUndoByProductDeliveryOrderId(Long productDeliveryOrderId);

    /**
     * 查找波次对应的订单详情
     *
     * @param productWaveTaskId 波次任务ID
     * @return 订单详情列表
     */
    @Query("select doi from ProductDeliveryOrderItem doi where doi.waveTaskId = ?1 and doi.offShelfStatus != com.dicfree.ms.wms.common.enums.OrderStatus.DONE")
    List<ProductDeliveryOrderItem> findAllOffShelfUndoByWaveTaskId(Long productWaveTaskId);

    /**
     * 根据波次任务ID和商品SKU编码查询数据
     *
     * @param waveTaskId 波次任务ID
     * @param productSkuCode 商品SKU编码
     * @return 电商发货单明细列表
     */
    @Query("select do, doi from ProductDeliveryOrderItem doi join ProductDeliveryOrder do on doi.productDeliveryOrderId = do.id" +
            " where doi.waveTaskId = ?1" +
            " and doi.productSkuCode = ?2" +
            " and doi.sortingStatus != com.dicfree.ms.wms.common.enums.OrderStatus.DONE" +
            " order by do.dispatchTime asc")
    List<Object> findAllSortingUndoByWaveTaskIdAndProductSkuCode(Long waveTaskId, String productSkuCode);

    /**
     * 查询所有未完成的出库校验的
     *
     * @param productSkuCode 商品SKU编码
     * @return 电商发货单明细列表
     */
    @Query("select doi from ProductDeliveryOrderItem doi where doi.productSkuCode = ?1 and doi.verifyStatus != com.dicfree.ms.wms.common.enums.OrderStatus.DONE")
    List<ProductDeliveryOrderItem> findAllNotVerify(String productSkuCode);

    /**
     * 根据波次任务ID查询数据
     *
     * @param waveTaskId 波次任务ID
     * @return 电商发货单明细列表
     */
    @Query("select doi from ProductDeliveryOrderItem doi where doi.waveTaskId = ?1")
    List<ProductDeliveryOrderItem> findAllByWaveTaskId(Long waveTaskId);
}
