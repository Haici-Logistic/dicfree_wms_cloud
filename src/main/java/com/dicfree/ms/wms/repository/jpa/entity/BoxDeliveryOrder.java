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
 * 整装箱出库订单
 *
 * @author wiiyaya
 * @date 2023/09/02
 */
@Entity
@Table(name = "df_wms_box_delivery_order")
@GenericGenerator(name = IdGenConstant.NAME)
public class BoxDeliveryOrder extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = 431845845453435970L;

    /**
     * 集货任务id
     */
    private Long collectionTaskId;

    /**
     * 单号
     */
    private String uniqueNo;

    /**
     * 客户
     */
    private String supplier;

    /**
     * 外部订单编号
     */
    private String thirdOrderNo;

    /**
     * 计划派送时间
     */
    private LocalDateTime deliveryDate;

    /**
     * 出发目的地
     */
    private String addressInfo;

    /**
     * 送仓预约号
     */
    private String appointmentId;

    /**
     * 分拣人
     */
    private String sortingMember;

    /**
     * 状态
     */
    private OrderStatus outboundStatus;

    /**
     * 已出库数量
     */
    private Integer outboundCount;

    /**
     * 总计数量
     */
    private Integer totalCount;

    @Column
    public Long getCollectionTaskId() {
        return collectionTaskId;
    }

    public void setCollectionTaskId(Long collectionTaskId) {
        this.collectionTaskId = collectionTaskId;
    }

    @Column
    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    @Column
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Column
    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    @Column
    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Column
    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String deliveryTo) {
        this.addressInfo = deliveryTo;
    }

    @Column
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Column
    public String getSortingMember() {
        return sortingMember;
    }

    public void setSortingMember(String sortingMember) {
        this.sortingMember = sortingMember;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public OrderStatus getOutboundStatus() {
        return outboundStatus;
    }

    public void setOutboundStatus(OrderStatus status) {
        this.outboundStatus = status;
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
}
