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
import com.dicfree.ms.wms.common.enums.CollectionAreaStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;

/**
 * 集货区
 *
 * @author wiiyaya
 * @date 2023/11/06
 */
@Entity
@Table(name = "df_wms_collection_area")
@GenericGenerator(name = IdGenConstant.NAME)
public class CollectionArea extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = -367176231875668160L;

    /**
     * 集货区编码
     */
    private String code;

    /**
     * 集货区状态
     */
    private CollectionAreaStatus status;

    /**
     * 波次号
     */
    private String waveTaskUniqueNo;

    @Column
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public CollectionAreaStatus getStatus() {
        return status;
    }

    public void setStatus(CollectionAreaStatus status) {
        this.status = status;
    }

    @Column
    public String getWaveTaskUniqueNo() {
        return waveTaskUniqueNo;
    }

    public void setWaveTaskUniqueNo(String waveTaskUniqueNo) {
        this.waveTaskUniqueNo = waveTaskUniqueNo;
    }
}
