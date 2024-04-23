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
import com.dicfree.ms.wms.repository.jpa.entity.ProductSn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
public interface ProductSnDao extends BaseDao<ProductSn, Long> {

    /**
     * 根据到货单详情id获取还未入库序列商品列表
     *
     * @param arrivalOrderItemId 到货单详情id
     * @return 序列商品列表
     */
    @Query("select sn from ProductSn sn where sn.arrivalOrderItemId = ?1 and sn.stockStatus = com.dicfree.ms.wms.common.enums.StockStatus.WAITING order by sn.serialNo asc")
    List<ProductSn> findAllUnArrivalByArrivalOrderItemId(Long arrivalOrderItemId);

    /**
     * 根据到货单详情id获取还未入库序列商品列表
     *
     * @param arrivalOrderItemId 到货单详情id
     * @param pageable           分页参数
     * @return 序列箱列表
     */
    @Query("select sn from ProductSn sn where sn.arrivalOrderItemId = ?1 and sn.stockStatus = com.dicfree.ms.wms.common.enums.StockStatus.WAITING order by sn.serialNo asc")
    Page<ProductSn> findAllUnArrivalByArrivalOrderItemId(Long arrivalOrderItemId, Pageable pageable);

    /**
     * 根据到货单详情id获取还未入库序列商品列表
     *
     * @param arrivalOrderItemId 到货单详情id
     * @return 序列商品列表
     */
    @Query("select sn, sku from ProductSn sn join ProductSku sku on sn.productSkuCode = sku.code where sn.arrivalOrderItemId = ?1 and sn.stockStatus = com.dicfree.ms.wms.common.enums.StockStatus.WAITING order by sn.serialNo asc")
    List<Object> findAllUnArrivalDetailByArrivalOrderItemId(Long arrivalOrderItemId);

    /**
     * 根据到货单详情id获取还未上架序列商品列表
     *
     * @param productSkuCode 到货单详情id
     * @param pageable       分页参数
     * @return 序列箱列表
     */
    @Query("select sn from ProductSn sn where sn.productSkuCode = ?1 and sn.stockStatus = com.dicfree.ms.wms.common.enums.StockStatus.INBOUND and sn.shelfStatus = com.dicfree.ms.wms.common.enums.ShelfStatus.WAITING order by sn.inboundTime asc")
    Page<ProductSn> findAllUnOnShelfBySkuCode(String productSkuCode, Pageable pageable);

    /**
     * 获取出库未核验商品列表
     *
     * @param productSkuCode 商品sku编码
     * @return 出库未核验商品列表
     */
    @Query("select sn from ProductSn sn where sn.productSkuCode = ?1 and sn.sorted = true and sn.verified = false order by sn.offShelfTime asc")
    List<ProductSn> findAllUnVerifyByProductSkuCode(String productSkuCode);

    /**
     * 根据到货单详情id获取还未出库序列商品列表
     *
     * @param productDeliveryOrderItemIdList 到货单详情id列表
     * @return 序列商品列表
     */
    @Query("select sn from ProductSn sn where sn.deliveryOrderItemId in (?1) and sn.stockStatus = com.dicfree.ms.wms.common.enums.StockStatus.INBOUND")
    List<ProductSn> findAllByDeliveryOrderItemIdIn(List<Long> productDeliveryOrderItemIdList);

    /**
     * 获取未下架的序列商品
     *
     * @param productSkuCode 产品编号
     * @param pageRequest    分页参数
     * @return 序列商品列表
     */
    @Query("select sn from ProductSn sn where sn.productSkuCode = ?1 and sn.shelfStatus = com.dicfree.ms.wms.common.enums.ShelfStatus.ON_SHELF and sn.quality = true order by sn.onShelfTime asc")
    Page<ProductSn> findAllUnOffShelfByProductSkuCode(String productSkuCode, PageRequest pageRequest);

    /**
     * 获取货架上的商品
     *
     * @param productSkuCode 商品sku编码
     * @param shelfNo        货架编号
     * @return 序列商品列表
     */
    @Query("select sn from ProductSn sn where sn.productSkuCode = ?1 and sn.shelfNo = ?2 and sn.shelfStatus = com.dicfree.ms.wms.common.enums.ShelfStatus.ON_SHELF and sn.quality=true order by sn.onShelfTime asc")
    List<ProductSn> findByProductSkuCodeAndShelfNo(String productSkuCode, String shelfNo);

    /**
     * 获取序列商品已下架列表
     *
     * @param productDeliveryOrderItemId 出库订单详情id
     * @return 序列商品列表
     */
    @Query("select sn from ProductSn sn where sn.deliveryOrderItemId = ?1 and sn.shelfStatus = com.dicfree.ms.wms.common.enums.ShelfStatus.OFF_SHELF")
    List<ProductSn> findAllOffShelfByDeliveryOrderItemId(Long productDeliveryOrderItemId);

    /**
     * 获取未分拣的序列商品
     *
     * @param productDeliveryOrderItemIdList 出库订单详情id列表
     * @return 序列商品列表
     */
    @Query("select sn from ProductSn sn where sn.deliveryOrderItemId in (?1) and sn.sorted = false")
    List<ProductSn> findAllUnSortByDeliveryOrderItemIdIn(List<Long> productDeliveryOrderItemIdList);

    /**
     * 查找未分拣的序列商品
     *
     * @param productSkuCode 商品编码
     * @param pageRequest    分页参数
     * @return 序列商品列表
     */
    @Query("select sn from ProductSn sn where sn.productSkuCode = ?1 and sn.shelfStatus = com.dicfree.ms.wms.common.enums.ShelfStatus.OFF_SHELF and sn.sorted = false order by sn.offShelfTime asc")
    Page<ProductSn> findAllUnSortByProductSkuCode(String productSkuCode, PageRequest pageRequest);

    /**
     * 查找未检验的序列商品
     *
     * @param productSkuCode             商品编码
     * @param productDeliveryOrderItemId 订单详情id
     * @param pageRequest                分页参数
     * @return 序列商品列表
     */
    @Query("select sn from ProductSn sn where sn.productSkuCode = ?1 and sn.deliveryOrderItemId = ?2 and sn.verified = false order by sn.offShelfTime asc")
    Page<ProductSn> findAllUnVerifyByProductSkuCodeAndDeliveryOrderItemId(String productSkuCode, Long productDeliveryOrderItemId, PageRequest pageRequest);

    /**
     * 根据到货单详情id获取还未入库序列商品列表
     *
     * @param productSkuCode 商品sku编码
     * @return 序列商品列表
     */
    @Query("select sn from ProductSn sn where sn.productSkuCode = ?1 and sn.shelfStatus = com.dicfree.ms.wms.common.enums.ShelfStatus.ON_SHELF")
    List<ProductSn> findAllOnShelfByProductSkuCode(String productSkuCode);

    /**
     * 查找SN详情
     *
     * @param code 商品sn编码
     * @return 序列商品列表
     */
    @Query("select sn from ProductSn sn where sn.code = ?1")
    Optional<ProductSn> findByCode(String code);
}
