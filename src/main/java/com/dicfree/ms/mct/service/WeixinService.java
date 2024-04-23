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
package com.dicfree.ms.mct.service;

import cn.jzyunqi.common.third.weixin.model.enums.WeixinType;
import cn.jzyunqi.common.third.weixin.model.response.UserTokenRsp;

/**
 * @author wiiyaya
 * @date 2021/4/30.
 */
public interface WeixinService {

    /**
     * 创建微信关系
     *
     * @param memberId        会员id
     * @param weixinUserToken 微信token信息
     * @param subscribe       是否关注
     */
    void modifyWeixin(WeixinType weixinType, Long memberId, UserTokenRsp weixinUserToken, Boolean subscribe);

    /**
     * 通过会员id查找openId
     *
     * @param memberId 会员id
     * @return openId列表
     */
    String mpOpenId(Long memberId, WeixinType weixinType);
}
