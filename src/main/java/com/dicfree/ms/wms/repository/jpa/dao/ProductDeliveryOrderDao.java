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
import com.dicfree.ms.wms.repository.jpa.entity.ProductDeliveryOrder;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/10/28
 */
public interface ProductDeliveryOrderDao extends BaseDao<ProductDeliveryOrder, Long> {

    /**
     * 根据外部订单编号检查是否已经存在相关数据
     *
     * @param thirdOrderNo 外部订单编号
     * @return 是否存在
     */
    @Query("select count(mdo)>0 from ProductDeliveryOrder mdo where mdo.thirdOrderNo = ?1")
    boolean isThirdOrderNoExists(String thirdOrderNo);

    /**
     * 根据运单号检查是否已经存在相关数据
     *
     * @param waybill 运单号
     * @return 是否存在
     */
    @Query("select count(mdo)>0 from ProductDeliveryOrder mdo where mdo.waybill = ?1")
    boolean isWaybillExists(String waybill);

    /**
     * 查找订单信息
     *
     * @param productDeliveryOrderId 电商发货单ID
     * @param supplier               客户
     * @return 电商发货单信息
     */
    @Query("select mdo from ProductDeliveryOrder mdo where mdo.id = ?1 and mdo.supplier = ?2")
    Optional<ProductDeliveryOrder> findByIdAndSupplier(Long productDeliveryOrderId, String supplier);

    /**
     * 根据外部订单编号查找订单信息
     *
     * @param thirdOrderNo 外部订单编号
     * @return 电商发货单信息
     */
    @Query("select mdo from ProductDeliveryOrder mdo where mdo.thirdOrderNo = ?1")
    Optional<ProductDeliveryOrder> findByThirdOrderNo(String thirdOrderNo);

    /**
     * 根据运单号查找订单信息
     *
     * @param waybill 运单号
     * @return 电商发货单信息
     */
    @Query("select mdo from ProductDeliveryOrder mdo where mdo.waybill = ?1")
    Optional<ProductDeliveryOrder> findByWaybill(String waybill);

    /**
     * 查找下架未处理的发货订单
     *
     * @param waveTaskIdList 波次任务ID
     * @return 下架未处理的发货订单
     */
    @Query("select mdo from ProductDeliveryOrder mdo where mdo.waveTaskId = ?1 and mdo.offShelfStatus != com.dicfree.ms.wms.common.enums.OrderStatus.DONE order by mdo.dispatchTime asc")
    List<ProductDeliveryOrder> findAllOffShelfUndo(Long waveTaskIdList);

    /**
     * 查找发货订单
     *
     * @param waveTaskId 波次任务ID
     * @return 发货订单列表
     */
    @Query("select mdo from ProductDeliveryOrder mdo where mdo.waveTaskId = ?1")
    List<ProductDeliveryOrder> findAllByWaveTaskId(Long waveTaskId);

    /**
     * 查找未完成的发货订单
     *
     * @param waveTaskId 波次任务ID
     * @return 未完成的发货订单
     */
    @Query("select mdo from ProductDeliveryOrder mdo where mdo.waveTaskId = ?1 and mdo.sortingStatus != com.dicfree.ms.wms.common.enums.OrderStatus.DONE order by mdo.updateTime desc")
    List<ProductDeliveryOrder> findAllUndoByWaveTaskId(Long waveTaskId);

    /**
     * 查找已完成的发货订单
     *
     * @param waveTaskId 波次任务ID
     * @return 已完成的发货订单
     */
    @Query("select mdo from ProductDeliveryOrder mdo where mdo.waveTaskId = ?1 and mdo.sortingStatus = com.dicfree.ms.wms.common.enums.OrderStatus.DONE order by mdo.updateTime desc")
    List<ProductDeliveryOrder> findAllDoneByWaveTaskId(Long waveTaskId);

    /**
     * 根据运单号或外部订单编号查找订单信息
     *
     * @param waybill 运单号
     * @return 电商发货单信息
     */
    @Query("select mdo from ProductDeliveryOrder mdo where mdo.waybill = ?1 or mdo.thirdOrderNo = ?1")
    Optional<ProductDeliveryOrder> findByWaybillOrThirdOrderNo(String waybill);
}
