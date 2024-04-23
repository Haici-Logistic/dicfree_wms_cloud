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
import com.dicfree.ms.wms.repository.jpa.entity.BoxSn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
public interface BoxSnDao extends BaseDao<BoxSn, Long> {

    /**
     * 根据批次号获取序列箱信息
     *
     * @param batchNo 批次号
     * @return 序列箱列表
     */
    @Query("select sn, sp from BoxSn sn join BoxSku sp on sn.boxSkuCode =sp.code where sn.batchNo = ?1")
    List<Object> findByBatchNo(String batchNo);

    /**
     * 获取全量序列箱信息
     *
     * @return 序列箱列表
     */
    @Query("select sn, sp from BoxSn sn join BoxSku sp on sn.boxSkuCode =sp.code order by sp.createTime desc, sn.serialNo asc limit 100000")
    List<Object> findLimitAll();

    /**
     * 根据箱号获取序列箱信息
     *
     * @param boxSkuCode  箱号
     * @param pageable 分页参数
     * @return 序列箱列表
     */
    @Query("select sn from BoxSn sn where sn.boxSkuCode = ?1")
    Page<BoxSn> findAllByBoxSkuCode(String boxSkuCode, Pageable pageable);

    /**
     * 根据序列箱号获取序列箱信息
     *
     * @param code 序列箱号
     * @return 序列箱信息
     */
    @Query("select sn from BoxSn sn where sn.code = ?1")
    Optional<BoxSn> findByCode(String code);

    /**
     * 根据序列箱号获取序列箱信息
     *
     * @param code 序列箱号
     * @return 序列箱列表
     */
    @Query("select sn, sku from BoxSn sn join BoxSku sku on sn.boxSkuCode = sku.code where sn.code = ?1")
    Optional<Object> findByCodeWithSku(String code);

    /**
     * 根据序列箱号获取序列箱信息
     *
     * @param boxSnId 序列箱号
     * @return 序列箱列表
     */
    @Query("select sn, sku from BoxSn sn join BoxSku sku on sn.boxSkuCode = sku.code where sn.id = ?1")
    Optional<Object> findFullInfoById(Long boxSnId);
    /**
     * 根据客户序列箱号获取序列箱信息
     *
     * @param supplierBoxSnCode 客户序列箱号
     * @return 序列箱列表
     */
    @Query("select sn, sku from BoxSn sn join BoxSku sku on sn.boxSkuCode = sku.code where sn.supplierBoxSnCode = ?1")
    Optional<Object> findBySupplierBoxSnCodeWithSku(String supplierBoxSnCode);

    /**
     * 根据整装箱入库订单详情id获取序列箱列表
     *
     * @param boxArrivalOrderItemId 整装箱入库订单详情id
     * @return 序列箱列表
     */
    @Query("select sn from BoxSn sn where sn.boxArrivalOrderItemId = ?1")
    List<BoxSn> findAllByBoxArrivalOrderItemId(Long boxArrivalOrderItemId);

    /**
     * 根据整装箱入库订单详情id列表获取序列箱列表
     *
     * @param boxArrivalOrderItemIdList 整装箱入库订单详情id列表
     * @return 序列箱列表
     */
    @Query("select sn from BoxSn sn where sn.boxArrivalOrderItemId in (?1)")
    List<BoxSn> findAllByBoxArrivalOrderItemIdIn(List<Long> boxArrivalOrderItemIdList);

    /**
     * 根据整装箱入库订单详情id获取还未入库序列箱列表
     *
     * @param boxArrivalOrderItemId 整装箱入库订单详情id
     * @return 序列箱列表
     */
    @Query("select sn from BoxSn sn where sn.boxArrivalOrderItemId = ?1 and sn.status = com.dicfree.ms.wms.common.enums.StockStatus.WAITING order by sn.serialNo asc")
    List<BoxSn> findAllUnArrivalByBoxArrivalOrderItemId(Long boxArrivalOrderItemId);

    /**
     * 根据整装箱入库订单详情id获取还未入库序列箱列表
     *
     * @param boxArrivalOrderItemId 整装箱入库订单详情id
     * @return 序列箱列表
     */
    @Query("select sn, sku from BoxSn sn join BoxSku sku on sn.boxSkuCode = sku.code  where sn.boxArrivalOrderItemId = ?1 and sn.status = com.dicfree.ms.wms.common.enums.StockStatus.WAITING order by sn.serialNo asc")
    List<Object> findAllUnArrivalDetailByBoxArrivalOrderItemId(Long boxArrivalOrderItemId);

    /**
     * 根据整装箱入库订单详情id获取还未入库序列箱列表
     *
     * @param boxArrivalOrderItemIdList 整装箱入库订单详情id列表
     * @return 序列箱列表
     */
    @Query("select sn, sku from BoxSn sn join BoxSku sku on sn.boxSkuCode = sku.code  where sn.boxArrivalOrderItemId in (?1) and sn.status = com.dicfree.ms.wms.common.enums.StockStatus.WAITING order by sn.boxSkuCode asc, sn.serialNo asc")
    List<Object> findAllUnArrivalDetailByBoxArrivalOrderItemIdIn(List<Long> boxArrivalOrderItemIdList);

    /**
     * 根据整装箱入库订单详情id获取还未入库序列箱列表
     *
     * @param boxArrivalOrderItemId 整装箱入库订单详情id
     * @param pageable           分页参数
     * @return 序列箱列表
     */
    @Query("select sn from BoxSn sn where sn.boxArrivalOrderItemId = ?1 and sn.status = com.dicfree.ms.wms.common.enums.StockStatus.WAITING order by sn.serialNo asc")
    Page<BoxSn> findAllUnArrivalByBoxArrivalOrderItemId(Long boxArrivalOrderItemId, Pageable pageable);

    /**
     * 根据箱号获取还未出库序列箱列表
     *
     * @param boxSkuCode  箱号
     * @param pageable 分页参数
     * @return 序列箱列表
     */
    @Query("select sn from BoxSn sn where sn.boxSkuCode = ?1 and sn.status = com.dicfree.ms.wms.common.enums.StockStatus.INBOUND order by sn.inboundTime asc")
    Page<BoxSn> findAllUnDeliveryByBoxSkuCode(String boxSkuCode, Pageable pageable);

    /**
     * 根据箱号获取还未出库序列箱列表
     *
     * @param boxSkuCode 箱号
     * @return 序列箱列表
     */
    @Query("select sn from BoxSn sn where sn.boxSkuCode = ?1 and sn.status = com.dicfree.ms.wms.common.enums.StockStatus.INBOUND order by sn.inboundTime asc")
    List<BoxSn> findAllUnDeliveryByBoxSkuCode(String boxSkuCode);

    /**
     * 根据箱号列表获取还未出库序列箱列表
     *
     * @param boxSkuCodeList 箱号列表
     * @return 序列箱列表
     */
    @Query("select sn from BoxSn sn where sn.boxSkuCode in (?1) and sn.status = com.dicfree.ms.wms.common.enums.StockStatus.INBOUND order by sn.boxSkuCode asc, sn.inboundTime asc")
    List<BoxSn> findAllUnDeliveryByBoxSkuCodeIn(List<String> boxSkuCodeList);

    /**
     * 根据整装箱出库订单详情id获取已出库序列箱列表
     *
     * @param boxDeliveryOrderItemId 整装箱出库订单详情id
     * @return 序列箱列表
     */
    @Query("select sn from BoxSn sn where sn.boxDeliveryOrderItemId = ?1 order by sn.outboundTime asc")
    List<BoxSn> findAllDeliveryByBoxDeliveryOrderItemId(Long boxDeliveryOrderItemId);

    /**
     * 根据整装箱出库订单详情id获取已出库序列箱列表
     *
     * @param boxDeliveryOrderItemIdList 整装箱出库订单详情id列表
     * @return 序列箱列表
     */
    @Query("select sn from BoxSn sn where sn.boxDeliveryOrderItemId in (?1) order by sn.outboundTime asc")
    List<BoxSn> findAllDeliveryByBoxDeliveryOrderItemIdIn(List<Long> boxDeliveryOrderItemIdList);
}
