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
package com.dicfree.ms.cts.repository.jpa.entity;

import cn.jzyunqi.common.support.hibernate.persistence.domain.BaseDomain;
import cn.jzyunqi.common.support.hibernate.persistence.domain.IdGenConstant;
import com.dicfree.ms.cts.common.enums.TransportMode;
import com.dicfree.ms.cts.common.enums.TransportOrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 转运订单
 *
 * @author wiiyaya
 * @date 2023/11/28
 */
@Entity
@Table(name = "df_cts_transport_order")
@GenericGenerator(name=IdGenConstant.NAME)
public class TransportOrder extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = 119786183496027650L;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 货品的快递面单号
     */
    private String waybill;

    /**
     * 计价金额
     */
    private BigDecimal amount;

    /**
     * 快递件上的姓名
     */
    private String name;

    /**
     * 快递件上的手机号
     */
    private String phone;

    /**
     * 发运目的地酋长国
     */
    private String province;

    /**
     * 发运目的地城市
     */
    private String city;

    /**
     * 发运目的地地址
     */
    private String address;

    /**
     * 包裹重量
     */
    private Float weight;

    /**
     * 包裹长度
     */
    private Float length;

    /**
     * 包裹宽度
     */
    private Float width;

    /**
     * 包裹高度
     */
    private Float height;

    /**
     * 是否可运
     */
    private Boolean transportable;

    /**
     * 运单模式
     */
    private TransportMode transportMode;

    /**
     * 运单状态
     */
    private TransportOrderStatus status;

    /**
     * 头程入库时间
     */
    private LocalDateTime firstInboundTime;

    /**
     * 头程发货时间
     */
    private LocalDateTime firstDeliveryTime;

    /**
     * 尾程入库时间
     */
    private LocalDateTime lastInboundTime;

    /**
     * 尾程派送时间
     */
    private LocalDateTime lastDeliveryTime;

    /**
     * 尾程签收时间
     */
    private LocalDateTime lastSigningTime;

    @Column
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Column
    public String getWaybill() {
        return waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }

    @Column
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column
    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @Column
    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    @Column
    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    @Column
    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    @Column
    public Boolean getTransportable() {
        return transportable;
    }

    public void setTransportable(Boolean transportable) {
        this.transportable = transportable;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public TransportMode getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(TransportMode transportMode) {
        this.transportMode = transportMode;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public TransportOrderStatus getStatus() {
        return status;
    }

    public void setStatus(TransportOrderStatus status) {
        this.status = status;
    }

    @Column
    public LocalDateTime getFirstInboundTime() {
        return firstInboundTime;
    }

    public void setFirstInboundTime(LocalDateTime firstInboundTime) {
        this.firstInboundTime = firstInboundTime;
    }

    @Column
    public LocalDateTime getFirstDeliveryTime() {
        return firstDeliveryTime;
    }

    public void setFirstDeliveryTime(LocalDateTime firstDeliveryTime) {
        this.firstDeliveryTime = firstDeliveryTime;
    }

    @Column
    public LocalDateTime getLastInboundTime() {
        return lastInboundTime;
    }

    public void setLastInboundTime(LocalDateTime lastInboundTime) {
        this.lastInboundTime = lastInboundTime;
    }

    @Column
    public LocalDateTime getLastDeliveryTime() {
        return lastDeliveryTime;
    }

    public void setLastDeliveryTime(LocalDateTime lastDeliveryTime) {
        this.lastDeliveryTime = lastDeliveryTime;
    }

    @Column
    public LocalDateTime getLastSigningTime() {
        return lastSigningTime;
    }

    public void setLastSigningTime(LocalDateTime lastSigningTime) {
        this.lastSigningTime = lastSigningTime;
    }
}
