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
import cn.jzyunqi.common.third.weixin.model.enums.WeixinType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author wiiyaya
 * @date 2018/5/18.
 */
@Entity
@Table(name = "df_mct_weixin")
public class Weixin extends BaseDomain<String, Long> {
    @Serial
    private static final long serialVersionUID = -7700274628563777173L;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 微信UID
     */
    private String unionId;

    /**
     * 微信类型
     */
    private WeixinType type;

    /**
     * 微信openid
     */
    private String openId;

    /**
     * 微信accessToken有效期（2小时）
     */
    private String accessToken;

    /**
     * accessToken过期时间
     */
    private LocalDateTime accessTokenExpDate;

    /**
     * 微信refreshToken有效期（30天）
     */
    private String refreshToken;

    /**
     * refreshToken过期时间
     */
    private LocalDateTime refreshTokenExpDate;

    /**
     * 是否订阅了微信账号
     */
    private Boolean subscribed;

    @Column
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Column
    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public WeixinType getType() {
        return type;
    }

    public void setType(WeixinType type) {
        this.type = type;
    }

    @Column
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Column
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Column
    public LocalDateTime getAccessTokenExpDate() {
        return accessTokenExpDate;
    }

    public void setAccessTokenExpDate(LocalDateTime accessTokenExpDate) {
        this.accessTokenExpDate = accessTokenExpDate;
    }

    @Column
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Column
    public LocalDateTime getRefreshTokenExpDate() {
        return refreshTokenExpDate;
    }

    public void setRefreshTokenExpDate(LocalDateTime refreshTokenExpDate) {
        this.refreshTokenExpDate = refreshTokenExpDate;
    }

    @Column
    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }
}
