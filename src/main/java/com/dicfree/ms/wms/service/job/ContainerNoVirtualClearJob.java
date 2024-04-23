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
package com.dicfree.ms.wms.service.job;

import cn.jzyunqi.common.feature.redis.RedisHelper;
import com.dicfree.ms.wms.common.constant.WmsCache;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wiiyaya
 * @date 2023/9/7
 */
@Component
@Slf4j
public class ContainerNoVirtualClearJob {

    @Resource
    private RedisHelper redisHelper;

    @Scheduled(cron="0 10 4 * * ? ") //每天4点10触发
    public void execute(){
        log.info("========start clear virtual container no !=========");
        redisHelper.removeKey(WmsCache.WMS_AO_CONTAINER_NO_VIRTUAL_V, RedisHelper.COMMON_KEY);
        redisHelper.removeKey(WmsCache.WMS_CT_COLLECTION_NO_VIRTUAL_V, RedisHelper.COMMON_KEY);
        log.info("========clear virtual container no finished !=========");
    }
}
