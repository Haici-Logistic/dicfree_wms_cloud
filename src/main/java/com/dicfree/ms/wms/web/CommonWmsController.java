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
package com.dicfree.ms.wms.web;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.ms.CommonRestBaseController;
import com.dicfree.ms.wms.common.dto.ex.qingflow.CourierData;
import com.dicfree.ms.wms.common.dto.ex.qingflow.LocationData;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 通用(轻流) - 快递/库位
 *
 * @author wiiyaya
 * @date 2023/11/7
 */
@RestController
@Validated
public class CommonWmsController extends CommonRestBaseController {

    @Resource
    private QingFlowClient qingFlowClient;

    /**
     * 获取快递商列表
     *
     * @return 快递商列表
     */
    @PostMapping("/common/wms/courierList")
    public List<CourierData> wmsCourierList() throws BusinessException {
        return qingFlowClient.queryCourierList();
    }

    /**
     * 获取库位列表
     *
     * @return 库位列表
     */
    @PostMapping("/common/wms/locationList")
    public List<LocationData> wmsLocationList() throws BusinessException {
        return qingFlowClient.queryLocationList();
    }
}
