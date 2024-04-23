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
package com.dicfree.ms.wms.repository.jpa.entity;

import cn.jzyunqi.common.support.hibernate.persistence.domain.BaseDomain;
import cn.jzyunqi.common.support.hibernate.persistence.domain.IdGenConstant;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;

/**
 * 电商商品出库订单详情
 *
 * @author wiiyaya
 * @date 2023/10/28
 */
@Entity
@Table(name = "df_wms_product_delivery_order_item")
@GenericGenerator(name=IdGenConstant.NAME)
public class ProductDeliveryOrderItem extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = -824350012948723600L;

    /**
     * 波次任务id
     */
    private Long waveTaskId;

    /**
     * 电商商品出库订单
     */
    private Long productDeliveryOrderId;

    /**
     * 电商商品号
     */
    private String productSkuCode;

    /**
     * 下架状态
     */
    private OrderStatus offShelfStatus;

    /**
     * 已下架数量
     */
    private Integer offShelfCount;

    /**
     * 分拣状态
     */
    private OrderStatus sortingStatus;

    /**
     * 已分拣数量
     */
    private Integer sortingCount;

    /**
     * 核验状态
     */
    private OrderStatus verifyStatus;

    /**
     * 已核验数量
     */
    private Integer verifyCount;

    /**
     * 总计数量
     */
    private Integer totalCount;

    @Column
    public Long getWaveTaskId() {
        return waveTaskId;
    }

    public void setWaveTaskId(Long waveTaskId) {
        this.waveTaskId = waveTaskId;
    }

    @Column
    public Long getProductDeliveryOrderId() {
        return productDeliveryOrderId;
    }

    public void setProductDeliveryOrderId(Long productDeliveryOrderId) {
        this.productDeliveryOrderId = productDeliveryOrderId;
    }

    @Column
    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public OrderStatus getOffShelfStatus() {
        return offShelfStatus;
    }

    public void setOffShelfStatus(OrderStatus offShelfStatus) {
        this.offShelfStatus = offShelfStatus;
    }

    @Column
    public Integer getOffShelfCount() {
        return offShelfCount;
    }

    public void setOffShelfCount(Integer offShelfCount) {
        this.offShelfCount = offShelfCount;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public OrderStatus getSortingStatus() {
        return sortingStatus;
    }

    public void setSortingStatus(OrderStatus sortingStatus) {
        this.sortingStatus = sortingStatus;
    }

    @Column
    public Integer getSortingCount() {
        return sortingCount;
    }

    public void setSortingCount(Integer sortingCount) {
        this.sortingCount = sortingCount;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public OrderStatus getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(OrderStatus verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    @Column
    public Integer getVerifyCount() {
        return verifyCount;
    }

    public void setVerifyCount(Integer verifyCount) {
        this.verifyCount = verifyCount;
    }

    @Column
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
