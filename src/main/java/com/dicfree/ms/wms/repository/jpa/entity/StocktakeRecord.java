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
import com.dicfree.ms.wms.common.enums.StocktakeDirection;
import com.dicfree.ms.wms.common.enums.StocktakeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 库存操作记录
 *
 * @author wiiyaya
 * @date 2023/07/17
 */
@Entity
@Table(name = "df_wms_stocktake_record")
@GenericGenerator(name= IdGenConstant.NAME)
public class StocktakeRecord extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = 973602121362722600L;

    /**
     * 触发时间
     */
    private LocalDateTime triggerTime;

    /**
     * 箱号
     */
    private String boxSkuCode;

    /**
     * 序列箱号
     */
    private String boxSnCode;

    /**
     * 客户箱号
     */
    private String supplierBoxCode;

    /**
     * 客户序列箱号 -> 客户箱号+序号
     */
    private String supplierBoxSnCode;

    /**
     * 盘点类型
     */
    private StocktakeType type;

    /**
     * 盘点方向
     */
    private StocktakeDirection direction;

    /**
     * 仓库名
     */
    private String hub;

    @Column
    public LocalDateTime getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(LocalDateTime triggerTime) {
        this.triggerTime = triggerTime;
    }

    @Column
    public String getBoxSkuCode() {
        return boxSkuCode;
    }

    public void setBoxSkuCode(String boxSkuCode) {
        this.boxSkuCode = boxSkuCode;
    }

    @Column
    public String getBoxSnCode() {
        return boxSnCode;
    }

    public void setBoxSnCode(String boxSnCode) {
        this.boxSnCode = boxSnCode;
    }

    @Column
    public String getSupplierBoxCode() {
        return supplierBoxCode;
    }

    public void setSupplierBoxCode(String supplierBoxCode) {
        this.supplierBoxCode = supplierBoxCode;
    }

    @Column
    public String getSupplierBoxSnCode() {
        return supplierBoxSnCode;
    }

    public void setSupplierBoxSnCode(String supplierBoxSnCode) {
        this.supplierBoxSnCode = supplierBoxSnCode;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public StocktakeType getType() {
        return type;
    }

    public void setType(StocktakeType type) {
        this.type = type;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public StocktakeDirection getDirection() {
        return direction;
    }

    public void setDirection(StocktakeDirection direction) {
        this.direction = direction;
    }

    @Column
    public String getHub() {
        return hub;
    }

    public void setHub(String hub) {
        this.hub = hub;
    }
}
