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
package com.dicfree.ms.mct.repository.jpa.entity;

import cn.jzyunqi.common.support.hibernate.persistence.domain.BaseDomain;
import cn.jzyunqi.common.support.hibernate.persistence.domain.IdGenConstant;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.id.Assigned;

import java.io.Serial;

/**
 * @author wiiyaya
 * @date 2018/5/4
 */
@Entity
@Table(name = "df_mct_member")
@GenericGenerator(name=IdGenConstant.NAME, type = Assigned.class)
public class Member extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = -1148883115839978005L;

    /**
     * 海运编号
     */
    private String navigationNo;

    /**
     * 航运编号
     */
    private String aviationNo;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    @Column
    public String getNavigationNo() {
        return navigationNo;
    }

    public void setNavigationNo(String navigationNo) {
        this.navigationNo = navigationNo;
    }

    @Column
    public String getAviationNo() {
        return aviationNo;
    }

    public void setAviationNo(String aviationNo) {
        this.aviationNo = aviationNo;
    }

    @Column
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Column
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Column
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
