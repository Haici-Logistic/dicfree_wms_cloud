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
package com.dicfree.ms.si.web;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import com.dicfree.ms.ClientRestBaseController;
import com.dicfree.ms.si.service.ClientCourierTrackService;
import com.dicfree.ms.si.service.ClientProductDeliveryOrderService;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.fastline.FastLineTraceCbReq;
import com.dicfree.ms.wms.common.dto.ex.fastline.TrackingData;
import com.dicfree.ms.wms.common.dto.ex.logistiq.LogistiqTraceCbReq;
import com.dicfree.ms.wms.common.enums.CourierType;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 外部系统 - 快递回调
 *
 * @author wiiyaya
 * @date 2023/11/14
 */
@RestController
@Validated
@Slf4j
public class ClientCourierCallbackController extends ClientRestBaseController {

    @Resource
    private ClientProductDeliveryOrderService clientDeliveryOrderService;

    @Resource
    private ClientCourierTrackService clientCourierTrackService;

    /**
     * 出库订单快递跟踪状态获取(c3x)
     *
     * @param productDeliveryOrderDto 电商商品入库订单信息
     * @param bindingResult           校验信息
     */
    @PostMapping(value = "/client/productDeliveryOrder/trace")
    public String courierC3xTraceCallback(@RequestBody @Validated(ExProductDeliveryOrderDto.Trace.class) ExProductDeliveryOrderDto productDeliveryOrderDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, productDeliveryOrderDto, ExProductDeliveryOrderDto.Trace.class);
        clientDeliveryOrderService.productDeliveryOrderTraceCallback(productDeliveryOrderDto.getCourier(), productDeliveryOrderDto.getWaybill(), null);
        return "success";
    }

    /**
     * 出库订单快递跟踪状态获取(logistiq)
     *
     * @param orderStatusCbReq 订单状态回调数据
     */
    @PostMapping(value = "/client/productDeliveryOrder/traceCallback")
    public String courierLogistiqTraceCallback(@RequestBody LogistiqTraceCbReq orderStatusCbReq) throws BusinessException {
        clientDeliveryOrderService.productDeliveryOrderTraceCallback(CourierType.Logistiq.name(), orderStatusCbReq.getWaybill(), orderStatusCbReq);
        return "success";
    }

    /**
     * 出库订单快递跟踪状态获取(FastLine)
     *
     * @param fastLineTraceCbReq 订单状态回调数据
     * @return 快递轨迹信息
     */
    @PostMapping(value = "/client/courier/fastLine/traceInfo")
    public TrackingData courierFastLineTraceInfo(@RequestBody FastLineTraceCbReq fastLineTraceCbReq) throws BusinessException {
        return clientCourierTrackService.fastLineTrackInfo(fastLineTraceCbReq.getWaybill(), fastLineTraceCbReq.getThirdOrderNo());
    }
}
