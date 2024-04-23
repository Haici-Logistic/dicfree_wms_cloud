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
import com.dicfree.ms.si.common.constant.SiCache;
import com.dicfree.ms.wms.common.dto.ex.lark.BiTableRecordData;
import com.dicfree.ms.wms.common.dto.ex.lark.BiTableRowData;
import com.dicfree.ms.wms.common.dto.ex.lark.LarkCommonRsp;
import com.dicfree.ms.wms.common.dto.ex.lark.RootFolderData;
import com.dicfree.ms.wms.common.dto.ex.lark.TenantAccessTokenReq;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wiiyaya
 * @date 2023/9/18
 * 飞书文档流程：
 * 1. 创建应用
 * 2. 设置应用的权限
 * 3. 创建应用自己管理的文件夹（可选）
 * 4. 创建应用自己的文档
 * 5. 通过在线接口查找到需要分享用户的openid，再通过在线增加权限接口给这个opendid授权
 * 6. 用接在线口操作这个文档，或者是这个openid对应的用户操作文档
 */
@Component
@Slf4j
public class LarkClient {

    @Value("${df.feature.lark.app-token:}")
    private String appToken;

    @Value("${df.feature.lark.table-id:}")
    private String tableId;

    @Value("${df.feature.lark.app-id:}")
    private String appId;

    @Value("${df.feature.lark.app-key:}")
    private String appKey;

    @Resource
    private LarkClient.Proxy larkClientProxy;

    @Resource
    private RedisHelper redisHelper;

    @Resource
    private RedissonClient redissonClient;

    /**
     * 获取某个应用的根目录
     *
     * @return 根目录信息
     */
    public RootFolderData rootFolderInfo() throws BusinessException {
        LarkCommonRsp<RootFolderData> result = larkClientProxy.rootFolderMeta(getTenantToken());
        if (result.getCode() != 0) {
            throw new BusinessException("lark rootFolderInfo error {0}", result.getMsg());
        } else {
            return result.getData();
        }
    }

    /**
     * 向某个多维表格新增数据
     *
     * @param rowData 表单数据
     * @return 表单数据
     */
    public List<BiTableRowData> addBiTableRecords(List<BiTableRowData> rowData) throws BusinessException {
        BiTableRecordData tableData = new BiTableRecordData();
        tableData.setRecords(rowData);

        LarkCommonRsp<BiTableRecordData> result = larkClientProxy.addBiTableRecords(getTenantToken(), appToken, tableId, tableData);
        if (result.getCode() != 0) {
            throw new BusinessException("lark addBiTableRecords error: " + result.getMsg());
        } else {
            return result.getData().getRecords();
        }
    }

    private String getTenantToken() throws BusinessException {
        String tenantToken = (String) redisHelper.vGet(SiCache.SI_LARK_TENANT_TOKEN_V, StringUtilPlus.EMPTY);
        if (StringUtilPlus.isNotBlank(tenantToken)) {
            return "Bearer " + tenantToken;
        }
        RLock lock = redissonClient.getLock(SiCache.SI_LARK_TENANT_TOKEN_LOCK_H.getPrefix());
        try {
            boolean locked = lock.tryLock(10, TimeUnit.MILLISECONDS);
            if (locked) {
                TenantAccessTokenReq tokenReqData = new TenantAccessTokenReq();
                tokenReqData.setAppId(appId);
                tokenReqData.setAppSecret(appKey);
                LarkCommonRsp<Void> result = larkClientProxy.getClientToken(tokenReqData);
                if (result.getCode() != 0) {
                    throw new BusinessException("lark getTenantAccessToken error {0}", result.getMsg());
                } else {
                    redisHelper.vPut(SiCache.SI_LARK_TENANT_TOKEN_V, RedisHelper.COMMON_KEY, result.getTenantAccessToken());
                    return "Bearer " + result.getTenantAccessToken();
                }
            } else {
                return null;
            }
        } catch (InterruptedException e) {
            log.warn("=====getTenantToken concurrent occur, return null!=====");
            return null;
        } finally {
            lock.unlock();
        }
    }

    @HttpExchange(url = "https://open.larksuite.com/open-apis/", contentType = "application/json", accept = "application" + "/json")
    public interface Proxy {

        @GetExchange("/drive/explorer/v2/root_folder/meta")
        LarkCommonRsp<RootFolderData> rootFolderMeta(@RequestHeader("Authorization") String authorization);

        @PostExchange("/bitable/v1/apps/{appToken}/tables/{tableId}/records/batch_create")
        LarkCommonRsp<BiTableRecordData> addBiTableRecords(@RequestHeader("Authorization") String authorization, @PathVariable String appToken, @PathVariable String tableId, @RequestBody BiTableRecordData tableRecordsData);

        @PostExchange("/auth/v3/tenant_access_token/internal")
        LarkCommonRsp<Void> getClientToken(@RequestBody TenantAccessTokenReq tenantAccessTokenReq);
    }
}
