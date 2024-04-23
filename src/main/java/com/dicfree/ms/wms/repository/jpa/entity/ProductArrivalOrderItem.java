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
 * 电商商品入库订单详情
 *
 * @author wiiyaya
 * @date 2023/10/25
 */
@Entity
@Table(name = "df_wms_product_arrival_order_item")
@GenericGenerator(name=IdGenConstant.NAME)
public class ProductArrivalOrderItem extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = 802662159184018400L;

    /**
     * 电商商品入库订单id
     */
    private Long productArrivalOrderId;

    /**
     * 电商商品编码
     */
    private String productSkuCode;

    /**
     * 已入库数量
     */
    private Integer inboundCount;

    /**
     * 已上架数量
     */
    private Integer onShelfCount;

    /**
     * 总计数量
     */
    private Integer totalCount;

    /**
     * 入库状态
     */
    private OrderStatus inboundStatus;

    /**
     * 上架状态
     */
    private OrderStatus onShelfStatus;

    @Column
    public Long getProductArrivalOrderId() {
        return productArrivalOrderId;
    }

    public void setProductArrivalOrderId(Long productArrivalOrderId) {
        this.productArrivalOrderId = productArrivalOrderId;
    }

    @Column
    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    @Column
    public Integer getInboundCount() {
        return inboundCount;
    }

    public void setInboundCount(Integer inboundCount) {
        this.inboundCount = inboundCount;
    }

    @Column
    public Integer getOnShelfCount() {
        return onShelfCount;
    }

    public void setOnShelfCount(Integer onShelfCount) {
        this.onShelfCount = onShelfCount;
    }

    @Column
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public OrderStatus getInboundStatus() {
        return inboundStatus;
    }

    public void setInboundStatus(OrderStatus inboundStatus) {
        this.inboundStatus = inboundStatus;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public OrderStatus getOnShelfStatus() {
        return onShelfStatus;
    }

    public void setOnShelfStatus(OrderStatus onShelfStatus) {
        this.onShelfStatus = onShelfStatus;
    }
}
