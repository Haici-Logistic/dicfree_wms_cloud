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
 * 整装箱出库订单详情
 *
 * @author wiiyaya
 * @date 2023/09/02
 */
@Entity
@Table(name = "df_wms_box_delivery_order_item")
@GenericGenerator(name=IdGenConstant.NAME)
public class BoxDeliveryOrderItem extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = -794434468099716200L;

    /**
     * 集货任务id
     */
    private Long collectionTaskId;

    /**
     * 整装箱出库订单id
     */
    private Long boxDeliveryOrderId;

    /**
     * 箱号 -> 客户箱号 +‘-’+ 客户
     */
    private String boxSkuCode;

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
    public Long getBoxDeliveryOrderId() {
        return boxDeliveryOrderId;
    }

    public void setBoxDeliveryOrderId(Long boxDeliveryOrderId) {
        this.boxDeliveryOrderId = boxDeliveryOrderId;
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
