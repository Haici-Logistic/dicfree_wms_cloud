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
 * PDA设备
 *
 * @author wiiyaya
 * @date 2023/10/25
 */
@Entity
@Table(name = "df_wms_device_pda")
@GenericGenerator(name=IdGenConstant.NAME)
public class DevicePda extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = 786136501529850200L;

    /**
     * PDA绑定用户id
     */
    private Long userId;

    /**
     * PDA唯一编码
     */
    private String code;

    /**
     * PDA名称
     */
    private String name;

    /**
     * PDA登录密钥
     */
    private String key;

    /**
     * PDA登录密钥密文
     */
    private String keySign;

    /**
     * PDA所属区域
     */
    private String shelfAreaCode;

    @Column
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column
    public String getKeySign() {
        return keySign;
    }

    public void setKeySign(String keySign) {
        this.keySign = keySign;
    }

    @Column
    public String getShelfAreaCode() {
        return shelfAreaCode;
    }

    public void setShelfAreaCode(String shelfAreaCode) {
        this.shelfAreaCode = shelfAreaCode;
    }
}
