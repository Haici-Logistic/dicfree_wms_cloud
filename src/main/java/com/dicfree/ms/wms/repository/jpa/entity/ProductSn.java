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
import com.dicfree.ms.wms.common.enums.ShelfStatus;
import com.dicfree.ms.wms.common.enums.StockStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 序列商品
 *
 * @author wiiyaya
 * @date 2023/10/25
 */
@Entity
@Table(name = "df_wms_product_sn")
@GenericGenerator(name=IdGenConstant.NAME)
public class ProductSn extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = -122416582955007230L;

    /**
     * 电商商品编码
     */
    private String productSkuCode;

    /**
     * 序列商品编码 -> 毫秒时间戳+序号
     */
    private String code;

    /**
     * 货架区域
     */
    private String shelfAreaCode;

    /**
     * 货架号
     */
    private String shelfNo;

    /**
     * 是否完整货物
     */
    private Boolean quality;

    /**
     * 货物库存状态
     */
    private StockStatus stockStatus;

    /**
     * 入库时间
     */
    private LocalDateTime inboundTime;

    /**
     * 出库时间
     */
    private LocalDateTime outboundTime;

    /**
     * 货物货架状态
     */
    private ShelfStatus shelfStatus;

    /**
     * 上架时间
     */
    private LocalDateTime onShelfTime;

    /**
     * 下架时间
     */
    private LocalDateTime offShelfTime;

    /**
     * 是否已分拣
     */
    private Boolean sorted;

    /**
     * 是否已检验
     */
    private Boolean verified;

    /**
     * 商品入库订单详情id
     */
    private Long arrivalOrderItemId;

    /**
     * 商品出库订单详情id
     */
    private Long deliveryOrderItemId;

    /**
     * 同一批数量
     */
    private Integer pcs;

    /**
     * 序号 -> 3位，数值
     */
    private String serialNo;

    /**
     * 备注
     */
    private String remark;

    @Column
    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    @Column
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column
    public String getShelfAreaCode() {
        return shelfAreaCode;
    }

    public void setShelfAreaCode(String shelfAreaCode) {
        this.shelfAreaCode = shelfAreaCode;
    }

    @Column
    public String getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(String shelfNo) {
        this.shelfNo = shelfNo;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public StockStatus getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(StockStatus stockStatus) {
        this.stockStatus = stockStatus;
    }

    @Column
    public LocalDateTime getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(LocalDateTime inboundTime) {
        this.inboundTime = inboundTime;
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
    public ShelfStatus getShelfStatus() {
        return shelfStatus;
    }

    public void setShelfStatus(ShelfStatus shelfStatus) {
        this.shelfStatus = shelfStatus;
    }

    @Column
    public LocalDateTime getOnShelfTime() {
        return onShelfTime;
    }

    public void setOnShelfTime(LocalDateTime onShelfTime) {
        this.onShelfTime = onShelfTime;
    }

    @Column
    public LocalDateTime getOffShelfTime() {
        return offShelfTime;
    }

    public void setOffShelfTime(LocalDateTime offShelfTime) {
        this.offShelfTime = offShelfTime;
    }

    @Column
    public Boolean getQuality() {
        return quality;
    }

    public void setQuality(Boolean quality) {
        this.quality = quality;
    }

    @Column
    public Boolean getSorted() {
        return sorted;
    }

    public void setSorted(Boolean sorted) {
        this.sorted = sorted;
    }

    @Column
    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Column
    public Long getArrivalOrderItemId() {
        return arrivalOrderItemId;
    }

    public void setArrivalOrderItemId(Long arrivalOrderItemId) {
        this.arrivalOrderItemId = arrivalOrderItemId;
    }

    @Column
    public Long getDeliveryOrderItemId() {
        return deliveryOrderItemId;
    }

    public void setDeliveryOrderItemId(Long deliveryOrderItemId) {
        this.deliveryOrderItemId = deliveryOrderItemId;
    }

    @Column
    public Integer getPcs() {
        return pcs;
    }

    public void setPcs(Integer pcs) {
        this.pcs = pcs;
    }

    @Column
    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Column
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
