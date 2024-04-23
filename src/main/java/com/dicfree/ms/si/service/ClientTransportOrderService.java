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
package com.dicfree.ms.si.service;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.cts.common.dto.ex.ExTransportOrderDto;
import com.dicfree.ms.cts.common.enums.TransportOrderStatus;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/28
 */
public interface ClientTransportOrderService{

    /**
     * 转运订单添加
     *
     * @param transportOrderDto 转运订单信息
     */
    void transportOrderAdd(ExTransportOrderDto transportOrderDto) throws BusinessException;

    /**
     * 转运订单修改
     *
     * @param transportOrderDto 转运订单信息
     */
    void transportOrderEdit(ExTransportOrderDto transportOrderDto) throws BusinessException;

    /**
     * 转运订单详情
     *
     * @param unionId 微信unionId
     * @param waybill 运单号
     */
    ExTransportOrderDto transportOrderInfo(String unionId, String waybill) throws BusinessException;

    /**
     * 转运订单列表
     *
     * @param unionId 微信unionId
     * @param status 订单状态
     */
    List<ExTransportOrderDto> transportOrderList(String unionId, TransportOrderStatus status) throws BusinessException;

    /**
     * 转运订单跟踪
     *
     * @param unionId 微信unionId
     * @param waybill 运单号
     */
    ExTransportOrderDto transportOrderTrace(String unionId, String waybill) throws BusinessException;
}
