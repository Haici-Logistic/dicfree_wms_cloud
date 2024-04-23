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
 * 序列箱表
 *
 * @author wiiyaya
 * @date 2023/07/17
 */
@Entity
@Table(name = "df_wms_box_sn")
@GenericGenerator(name = IdGenConstant.NAME)
public class BoxSn extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = -900413586478796500L;

    /**
     * 序列箱号 -> 毫秒时间戳+序号
     */
    private String code;

    /**
     * 整装箱入库订单详情id
     */
    private Long boxArrivalOrderItemId;

    /**
     * 入库时间
     */
    private LocalDateTime inboundTime;

    /**
     * 整装箱出库订单详情id
     */
    private Long boxDeliveryOrderItemId;

    /**
     * 出库时间
     */
    private LocalDateTime outboundTime;

    /**
     * 货物状态
     */
    private StockStatus status;

    /**
     * 箱号
     */
    private String boxSkuCode;

    /**
     * 存放位置
     */
    private String location;

    /**
     * 序号 -> 3位，数值
     */
    private String serialNo;

    /**
     * 同一批数量
     */
    private Integer pcs;

    /**
     * 客户序列箱号 -> 客户箱号+序号
     */
    private String supplierBoxSnCode;

    /**
     * 导入批次号
     */
    private String batchNo;

    /**
     * 是否已打印
     */
    private Boolean printed;

    @Column
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column
    public Long getBoxArrivalOrderItemId() {
        return boxArrivalOrderItemId;
    }

    public void setBoxArrivalOrderItemId(Long boxArrivalOrderId) {
        this.boxArrivalOrderItemId = boxArrivalOrderId;
    }

    @Column
    public LocalDateTime getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(LocalDateTime inboundTime) {
        this.inboundTime = inboundTime;
    }

    @Column
    public Long getBoxDeliveryOrderItemId() {
        return boxDeliveryOrderItemId;
    }

    public void setBoxDeliveryOrderItemId(Long boxDeliveryOrderId) {
        this.boxDeliveryOrderItemId = boxDeliveryOrderId;
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
    public StockStatus getStatus() {
        return status;
    }

    public void setStatus(StockStatus status) {
        this.status = status;
    }

    @Column
    public String getBoxSkuCode() {
        return boxSkuCode;
    }

    public void setBoxSkuCode(String boxSkuCode) {
        this.boxSkuCode = boxSkuCode;
    }

    @Column
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column
    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Column
    public Integer getPcs() {
        return pcs;
    }

    public void setPcs(Integer pcs) {
        this.pcs = pcs;
    }

    @Column
    public String getSupplierBoxSnCode() {
        return supplierBoxSnCode;
    }

    public void setSupplierBoxSnCode(String supplierBoxSnCode) {
        this.supplierBoxSnCode = supplierBoxSnCode;
    }

    @Column
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    @Column
    public Boolean getPrinted() {
        return printed;
    }

    public void setPrinted(Boolean printed) {
        this.printed = printed;
    }
}
