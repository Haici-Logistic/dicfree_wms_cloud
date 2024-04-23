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
package com.dicfree.ms.si.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.si.service.ClientCourierTrackService;
import com.dicfree.ms.wms.common.dto.ex.fastline.TrackingData;
import com.dicfree.ms.wms.service.client.FastLineClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2024/4/18
 */
@Service("clientCourierTrackService")
public class ClientCourierCallbackServiceImpl implements ClientCourierTrackService {

    @Resource
    private FastLineClient fastLineClient;

    @Override
    public TrackingData fastLineTrackInfo(String waybill, String thirdOrderNo) throws BusinessException {
        //将list倒序
        TrackingData trackingData = fastLineClient.trackOrder(waybill, thirdOrderNo);
        List<Map<String, Object>> list = trackingData.getShipmentStatusTrackList();
        Collections.reverse(list);
        return trackingData;
    }
}
