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
 * 打印机信息
 *
 * @author wiiyaya
 * @date 2023/07/17
 */
@Entity
@Table(name = "df_wms_device_printer")
@GenericGenerator(name= IdGenConstant.NAME)
public class DevicePrinter extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = -256525052257157600L;

    /**
     * 打印机绑定用户id
     */
    private Long userId;

    /**
     * 打印机绑定用户名
     */
    private String username;

    /**
     * 打印机编码
     */
    private String sn;

    /**
     * 打印机识别码
     */
    private String key;

    /**
     * 打印机名称
     */
    private String name;

    /**
     * 打印机sim卡
     */
    private String sim;

    @Column
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Column
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String note) {
        this.name = note;
    }

    @Column
    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }
}
