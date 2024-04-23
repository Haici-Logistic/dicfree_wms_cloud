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
 * 整装箱入库订单
 *
 * @author wiiyaya
 * @date 2023/09/02
 */
@Entity
@Table(name = "df_wms_box_arrival_order")
@GenericGenerator(name = IdGenConstant.NAME)
public class BoxArrivalOrder extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = 205363773374769950L;

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
     * 虚拟车号
     */
    private String containerNoVirtual;

    /**
     * 真实车号
     */
    private String containerNoReal;

    /**
     * 计划到货日期
     */
    private LocalDateTime arrivingDate;

    /**
     * 状态
     */
    private OrderStatus inboundStatus;

    /**
     * 已入库数量
     */
    private Integer inboundCount;

    /**
     * 总计数量
     */
    private Integer totalCount;

    /**
     * 总计整库数量
     */
    private Integer totalWholeCount;

    /**
     * 总计混库数量
     */
    private Integer totalBulkCount;

    /**
     * 是否已锁库
     */
    private Boolean locationLock;

    /**
     * 是否已打印
     */
    private Boolean printed;

    @Column
    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String no) {
        this.uniqueNo = no;
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
    public String getContainerNoVirtual() {
        return containerNoVirtual;
    }

    public void setContainerNoVirtual(String containerNoVirtual) {
        this.containerNoVirtual = containerNoVirtual;
    }

    @Column
    public String getContainerNoReal() {
        return containerNoReal;
    }

    public void setContainerNoReal(String containerNoReal) {
        this.containerNoReal = containerNoReal;
    }

    @Column
    public LocalDateTime getArrivingDate() {
        return arrivingDate;
    }

    public void setArrivingDate(LocalDateTime arrivingDate) {
        this.arrivingDate = arrivingDate;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public OrderStatus getInboundStatus() {
        return inboundStatus;
    }

    public void setInboundStatus(OrderStatus status) {
        this.inboundStatus = status;
    }

    @Column
    public Integer getInboundCount() {
        return inboundCount;
    }

    public void setInboundCount(Integer inboundCount) {
        this.inboundCount = inboundCount;
    }

    @Column
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Column
    public Integer getTotalWholeCount() {
        return totalWholeCount;
    }

    public void setTotalWholeCount(Integer totalWholeCount) {
        this.totalWholeCount = totalWholeCount;
    }

    @Column
    public Integer getTotalBulkCount() {
        return totalBulkCount;
    }

    public void setTotalBulkCount(Integer totalBulkCount) {
        this.totalBulkCount = totalBulkCount;
    }

    @Column
    public Boolean getLocationLock() {
        return locationLock;
    }

    public void setLocationLock(Boolean locationDone) {
        this.locationLock = locationDone;
    }

    @Column
    public Boolean getPrinted() {
        return printed;
    }

    public void setPrinted(Boolean printed) {
        this.printed = printed;
    }
}
