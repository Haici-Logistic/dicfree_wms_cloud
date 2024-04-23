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
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;

/**
 * 整装箱表
 *
 * @author wiiyaya
 * @date 2023/07/17
 */
@Entity
@Table(name = "df_wms_box_sku")
@GenericGenerator(name= IdGenConstant.NAME)
public class BoxSku extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = -844653694034797700L;

    /**
     * 箱号 -> 客户箱号 +‘-’+ 客户
     */
    private String code;

    /**
     * 客户箱号
     */
    private String supplierBoxCode;

    /**
     * 客户
     */
    private String supplier;

    /**
     * 随机取件码 -> 英文+数字，随机产生
     */
    private String pickUpCode;

    /**
     * 存放位置-> 随意，可为空
     */
    private String location;

    /**
     * 中转 -> 随意，可为空
     */
    private String sortingTo;

    /**
     * 目的地-> 随意，可为空
     */
    private String deliveryTo;

    @Column
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column
    public String getSupplierBoxCode() {
        return supplierBoxCode;
    }

    public void setSupplierBoxCode(String supplierBoxCode) {
        this.supplierBoxCode = supplierBoxCode;
    }

    @Column
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Column
    public String getPickUpCode() {
        return pickUpCode;
    }

    public void setPickUpCode(String pickUpCode) {
        this.pickUpCode = pickUpCode;
    }

    @Column
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column
    public String getSortingTo() {
        return sortingTo;
    }

    public void setSortingTo(String sortingTo) {
        this.sortingTo = sortingTo;
    }

    @Column
    public String getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(String deliveryTo) {
        this.deliveryTo = deliveryTo;
    }
}
