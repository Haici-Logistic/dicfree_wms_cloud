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
import com.dicfree.ms.wms.common.enums.WaveTaskType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;

/**
 * 出库波次任务
 *
 * @author wiiyaya
 * @date 2023/11/06
 */
@Entity
@Table(name = "df_wms_product_wave_task")
@GenericGenerator(name = IdGenConstant.NAME)
public class ProductWaveTask extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = -516507294771821700L;

    /**
     * 波次任务编号
     */
    private String uniqueNo;

    /**
     * 波次任务类型
     */
    private WaveTaskType type;

    /**
     * 集货区编码
     */
    private String collectionAreaCode;

    /**
     * 下架状态
     */
    private OrderStatus offShelfStatus;

    /**
     * 已下架数量
     */
    private Integer offShelfCount;

    /**
     * 集货状态
     */
    private Boolean collection;

    /**
     * 分篮状态
     */
    private OrderStatus basketStatus;

    /**
     * 已分篮数量
     */
    private Integer basketCount;

    /**
     * 分拣状态
     */
    private OrderStatus sortingStatus;

    /**
     * 已分拣数量
     */
    private Integer sortingCount;

    /**
     * 总计订单数量
     */
    private Integer totalOdCount;

    /**
     * 总计商品数量
     */
    private Integer totalSnCount;

    @Column
    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String no) {
        this.uniqueNo = no;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public WaveTaskType getType() {
        return type;
    }

    public void setType(WaveTaskType type) {
        this.type = type;
    }

    @Column
    public String getCollectionAreaCode() {
        return collectionAreaCode;
    }

    public void setCollectionAreaCode(String collectionAreaCode) {
        this.collectionAreaCode = collectionAreaCode;
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
    @Enumerated(EnumType.STRING)
    public OrderStatus getBasketStatus() {
        return basketStatus;
    }

    public void setBasketStatus(OrderStatus basketStatus) {
        this.basketStatus = basketStatus;
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
    public Integer getOffShelfCount() {
        return offShelfCount;
    }

    public void setOffShelfCount(Integer offShelfCount) {
        this.offShelfCount = offShelfCount;
    }

    @Column
    public Boolean getCollection() {
        return collection;
    }

    public void setCollection(Boolean collection) {
        this.collection = collection;
    }

    @Column
    public Integer getBasketCount() {
        return basketCount;
    }

    public void setBasketCount(Integer basketCount) {
        this.basketCount = basketCount;
    }

    @Column
    public Integer getSortingCount() {
        return sortingCount;
    }

    public void setSortingCount(Integer sortingCount) {
        this.sortingCount = sortingCount;
    }

    @Column
    public Integer getTotalOdCount() {
        return totalOdCount;
    }

    public void setTotalOdCount(Integer totalOdCount) {
        this.totalOdCount = totalOdCount;
    }

    @Column
    public Integer getTotalSnCount() {
        return totalSnCount;
    }

    public void setTotalSnCount(Integer totalSnCount) {
        this.totalSnCount = totalSnCount;
    }
}
