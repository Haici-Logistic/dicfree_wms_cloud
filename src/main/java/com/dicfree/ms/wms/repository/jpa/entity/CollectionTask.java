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
import java.time.LocalDateTime;

/**
 * 集货任务
 *
 * @author wiiyaya
 * @date 2023/09/02
 */
@Entity
@Table(name = "df_wms_collection_task")
@GenericGenerator(name=IdGenConstant.NAME)
public class CollectionTask extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = -988188916768590700L;

    /**
     * 虚拟集货任务号
     */
    private String collectionNoVirtual;

    /**
     * 实际集货任务号
     */
    private String collectionNoReal;

    /**
     * 出发日期
     */
    private LocalDateTime departureDate;

    /**
     * 出发目的地
     */
    private String deliveryToArray;

    /**
     * 已出库数量
     */
    private Integer outboundCount;

    /**
     * 总计数量
     */
    private Integer totalCount;

    /**
     * 状态
     */
    private OrderStatus status;

    @Column
    public String getCollectionNoVirtual() {
        return collectionNoVirtual;
    }

    public void setCollectionNoVirtual(String collectionNoVirtual) {
        this.collectionNoVirtual = collectionNoVirtual;
    }

    @Column
    public String getCollectionNoReal() {
        return collectionNoReal;
    }

    public void setCollectionNoReal(String collectionNoReal) {
        this.collectionNoReal = collectionNoReal;
    }

    @Column
    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    @Column
    public String getDeliveryToArray() {
        return deliveryToArray;
    }

    public void setDeliveryToArray(String deliveryToArray) {
        this.deliveryToArray = deliveryToArray;
    }

    @Column
    public Integer getOutboundCount() {
        return outboundCount;
    }

    public void setOutboundCount(Integer outboundCount) {
        this.outboundCount = outboundCount;
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
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
