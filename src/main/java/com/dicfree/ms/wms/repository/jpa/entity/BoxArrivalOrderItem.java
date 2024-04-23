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
import com.dicfree.ms.wms.common.enums.LocationType;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;

/**
 * 整装箱入库订单详情
 *
 * @author wiiyaya
 * @date 2023/09/02
 */
@Entity
@Table(name = "df_wms_box_arrival_order_item")
@GenericGenerator(name = IdGenConstant.NAME)
public class BoxArrivalOrderItem extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = 161441626916305700L;

    /**
     * 整装箱入库订单id
     */
    private Long boxArrivalOrderId;

    /**
     * 箱号 -> 客户箱号 +‘-’+ 客户
     */
    private String boxSkuCode;

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
     * 库位类型
     */
    private LocationType locationType;

    /**
     * 是否已打印
     */
    private Boolean printed;

    @Column
    public Long getBoxArrivalOrderId() {
        return boxArrivalOrderId;
    }

    public void setBoxArrivalOrderId(Long boxArrivalOrderId) {
        this.boxArrivalOrderId = boxArrivalOrderId;
    }

    @Column
    public String getBoxSkuCode() {
        return boxSkuCode;
    }

    public void setBoxSkuCode(String boxSkuCode) {
        this.boxSkuCode = boxSkuCode;
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
    @Enumerated(EnumType.STRING)
    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    @Column
    public Boolean getPrinted() {
        return printed;
    }

    public void setPrinted(Boolean printed) {
        this.printed = printed;
    }
}
