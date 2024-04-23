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
import com.dicfree.ms.wms.common.enums.OrderType;
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
 * 电商商品出库订单
 *
 * @author wiiyaya
 * @date 2023/10/28
 */
@Entity
@Table(name = "df_wms_product_delivery_order")
@GenericGenerator(name=IdGenConstant.NAME)
public class ProductDeliveryOrder extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = -829090062416712000L;

    /**
     * 波次任务id
     */
    private Long waveTaskId;

    /**
     * 订单类型
     */
    private OrderType type;

    /**
     * 客户
     */
    private String supplier;

    /**
     * 外部订单编号
     */
    private String thirdOrderNo;

    /**
     * 是否代发货
     */
    private Boolean proxy;

    /**
     * 是否已派单
     */
    private Boolean dispatched;

    /**
     * 派单时间
     */
    private LocalDateTime dispatchTime;

    /**
     * 是否已出库(是否已打印单号)
     */
    private Boolean outbound;

    /**
     * 出库时间(打印单号时间)
     */
    private LocalDateTime outboundTime;

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

    /**
     * 分配的篮子号
     */
    private String basketNo;

    /**
     * 快运商
     */
    private String courier;

    /**
     * 快递单号
     */
    private String waybill;

    /**
     * 快递单号PDF地址
     */
    private String waybillUrl;

    /**
     * 收件人姓名
     */
    private String name;

    /**
     * 收件人主叫电话
     */
    private String phone1;

    /**
     * 收件人备用电话
     */
    private String phone2;

    /**
     * 收件人省份
     */
    private String province;

    /**
     * 收件人城市
     */
    private String city;

    /**
     * 收件人详细地址
     */
    private String address;

    /**
     * 到付收款金额
     */
    private BigDecimal cod;

    /**
     * 备注
     */
    private String remark;

    @Column
    public Long getWaveTaskId() {
        return waveTaskId;
    }

    public void setWaveTaskId(Long waveTaskId) {
        this.waveTaskId = waveTaskId;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
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
    public Boolean getProxy() {
        return proxy;
    }

    public void setProxy(Boolean proxy) {
        this.proxy = proxy;
    }

    @Column
    public Boolean getDispatched() {
        return dispatched;
    }

    public void setDispatched(Boolean dispatched) {
        this.dispatched = dispatched;
    }

    @Column
    public LocalDateTime getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(LocalDateTime dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    @Column
    public Boolean getOutbound() {
        return outbound;
    }

    public void setOutbound(Boolean outbound) {
        this.outbound = outbound;
    }

    @Column
    public LocalDateTime getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(LocalDateTime outboundTime) {
        this.outboundTime = outboundTime;
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

    @Column
    public String getBasketNo() {
        return basketNo;
    }

    public void setBasketNo(String basketNo) {
        this.basketNo = basketNo;
    }

    @Column
    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    @Column
    public String getWaybill() {
        return waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }

    @Column
    public String getWaybillUrl() {
        return waybillUrl;
    }

    public void setWaybillUrl(String waybillUrl) {
        this.waybillUrl = waybillUrl;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    @Column
    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
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
    public BigDecimal getCod() {
        return cod;
    }

    public void setCod(BigDecimal cod) {
        this.cod = cod;
    }

    @Column
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
