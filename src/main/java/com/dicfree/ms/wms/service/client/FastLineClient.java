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
package com.dicfree.ms.wms.service.client;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.feature.redis.RedisHelper;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.constant.WmsCache;
import com.dicfree.ms.wms.common.dto.ex.fastline.AccessTokenRedisDto;
import com.dicfree.ms.wms.common.dto.ex.fastline.AuthTokenRsp;
import com.dicfree.ms.wms.common.dto.ex.fastline.FastLineComRsp;
import com.dicfree.ms.wms.common.dto.ex.fastline.TrackingData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2024/4/17
 */
@Component
@Slf4j
public class FastLineClient{

    private static final String FAST_LINE_AUTH_KEY = "fast_line_auth_token";

    @Value("${df.feature.fast-line.username:}")
    private String username;

    @Value("${df.feature.fast-line.password:}")
    private String password;

    @Value("${df.feature.fast-line.account-no:}")
    private String accountNo;

    @Resource
    private RedisHelper redisHelper;

    @Resource
    private DateTimeProvider dateTimeProvider;

    @Resource
    private FastLineClient.Proxy fastLineClientProxy;

    public TrackingData trackOrder(String waybill, Object callbackParams) throws BusinessException {
        String barcode = (String) callbackParams;
        log.info("FastLine trackOrder[{}] receive with barcode[{}]", waybill, barcode);
        String accessToken = getAuthToken();
        FastLineComRsp<TrackingData> response = fastLineClientProxy.trackOrder(accessToken, waybill, barcode);
        if(StringUtilPlus.isNotBlank(response.getMessage())){
            throw new BusinessException("FastLineClient.trackOrder.failed", response.getMessage());
        }
        return response.getData();
    }

    private String getAuthToken() throws BusinessException {
        AccessTokenRedisDto accessToken = (AccessTokenRedisDto) redisHelper.vGet(WmsCache.WMS_COURIER_TOKEN_V, FAST_LINE_AUTH_KEY);
        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));
        if(accessToken != null && accessToken.getExpiration().isAfter(currTime.plusMinutes(5))) {
            return accessToken.getTokenValue();
        }

        log.info("FastLine getAuthToken start!");
        AuthTokenRsp authTokenRsp = fastLineClientProxy.getAuthToken(username, password, accountNo, "password");
        if(StringUtilPlus.isNotBlank(authTokenRsp.getError())){
            throw new BusinessException("FastLineClient.getAuthToken.failed", authTokenRsp.getError(), authTokenRsp.getErrorDescription());
        }

        accessToken = new AccessTokenRedisDto();
        accessToken.setTokenValue("Bearer " + authTokenRsp.getAccessToken());
        accessToken.setExpiration(currTime.plusSeconds(authTokenRsp.getExpiresIn()));
        redisHelper.vPut(WmsCache.WMS_COURIER_TOKEN_V, FAST_LINE_AUTH_KEY, accessToken);

        return accessToken.getTokenValue();
    }

    @HttpExchange(url = "http://prod-ship-api.fastline.ae", contentType = "application/x-www-form-urlencoded", accept = "application" + "/json")
    public interface Proxy {

        @PostExchange("/GetAuthToken")
        AuthTokenRsp getAuthToken(@RequestParam("Username") String username, @RequestParam("Password") String password, @RequestParam("AccountNumber") String accountNo, @RequestParam("grant_type") String grantType);

        @PostExchange("/api/order/ShipmentDetails")
        FastLineComRsp<TrackingData> trackOrder(@RequestHeader("Authorization") String accessToken, @RequestParam("TrackingNo") String trackingNo, @RequestParam("Barcode") String barcode);
    }
}
