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
package com.dicfree.ms.mct.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.weixin.model.enums.WeixinType;
import cn.jzyunqi.common.third.weixin.model.response.UserTokenRsp;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.mct.repository.jpa.dao.WeixinDao;
import com.dicfree.ms.mct.repository.jpa.entity.Weixin;
import com.dicfree.ms.mct.service.WeixinService;
import jakarta.annotation.Resource;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2021/4/30.
 */
@Service("weixinService")
public class WeixinServiceImpl implements WeixinService {

    @Resource
    private WeixinDao weixinDao;

    @Resource
    private DateTimeProvider dateTimeProvider;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void modifyWeixin(WeixinType weixinType, Long memberId, UserTokenRsp weixinUserToken, Boolean subscribed) {
        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));
        Optional<Weixin> weixinOpt = weixinDao.findByMemberIdAndType(memberId, weixinType);
        Weixin weixin;
        if(weixinOpt.isPresent()) {
            weixin = weixinOpt.get();
            weixin.setOpenId(weixinUserToken.getOpenId()); //微信openid
            weixin.setAccessToken(weixinUserToken.getAccessToken());
            if(weixinUserToken.getExpiresIn() != null){
                weixin.setAccessTokenExpDate(currTime.plusSeconds(weixinUserToken.getExpiresIn()));
            }
            if(StringUtilPlus.isNotBlank(weixinUserToken.getRefreshToken())){
                weixin.setRefreshToken(weixinUserToken.getRefreshToken());
                weixin.setRefreshTokenExpDate(currTime.plusDays(30));
            }
        }else{
            weixin = new Weixin();
            weixin.setMemberId(memberId); //会员id
            weixin.setUnionId(weixinUserToken.getUnionId()); //微信UID
            weixin.setType(weixinType);
            weixin.setOpenId(weixinUserToken.getOpenId()); //微信openid
            weixin.setAccessToken(weixinUserToken.getAccessToken());
            if(weixinUserToken.getExpiresIn() != null){
                weixin.setAccessTokenExpDate(currTime.plusSeconds(weixinUserToken.getExpiresIn()));
            }
            if(StringUtilPlus.isNotBlank(weixinUserToken.getRefreshToken())){
                weixin.setRefreshToken(weixinUserToken.getRefreshToken());
                weixin.setRefreshTokenExpDate(currTime.plusDays(30));
            }
            weixin.setSubscribed(subscribed); //是否订阅了微信账号
        }
        weixinDao.save(weixin);
    }

    @Override
    public String mpOpenId(Long memberId, WeixinType weixinType) {
        return weixinDao.findByMemberIdAndType(memberId, weixinType).orElseGet(Weixin::new).getOpenId();
    }
}

