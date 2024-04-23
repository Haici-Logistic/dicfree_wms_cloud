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
package com.dicfree.ms.cts.service;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import com.dicfree.ms.cts.common.dto.ex.ExTransportOrderDto;
import com.dicfree.ms.cts.common.dto.ex.query.ExTransportOrderQueryDto;
import com.dicfree.ms.cts.common.enums.TransportOrderStatus;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/28
 */
public interface TransportOrderService {

    /**
     * 转运订单分页
     *
     * @param transportOrderQueryDto 查询条件
     * @param pageable          分页条件
     * @return 结果
     */
    PageDto<ExTransportOrderDto> transportOrderPage(ExTransportOrderQueryDto transportOrderQueryDto, Pageable pageable);

    /**
     * 转运订单列表
     *
     * @param memberId 会员id
     * @param status  订单状态
     * @return 结果
     */
    List<ExTransportOrderDto> transportOrderList(Long memberId, TransportOrderStatus status);

    /**
     * 转运订单添加
     *
     * @param transportOrderDto 转运订单信息
     */
    void transportOrderAdd(ExTransportOrderDto transportOrderDto) throws BusinessException;

    /**
     * 转运订单编辑初始化
     *
     * @param transportOrderId 转运订单id
     * @return 转运订单信息
     */
    ExTransportOrderDto transportOrderEditInit(Long transportOrderId) throws BusinessException;

    /**
     * 转运订单编辑
     *
     * @param transportOrderDto 转运订单信息
     */
    void transportOrderEdit(ExTransportOrderDto transportOrderDto) throws BusinessException;

    /**
     * 转运订单详情
     *
     * @param transportOrderId 转运订单id
     * @return 转运订单信息
     */
    ExTransportOrderDto transportOrderDetail(Long transportOrderId) throws BusinessException;

    /**
     * 转运订单信息
     *
     * @param memberId 会员id
     * @param waybill  运单号
     */
    ExTransportOrderDto transportOrderInfo(Long memberId, String waybill) throws BusinessException;

    /**
     * 转运订单删除
     *
     * @param transportOrderId 转运订单id
     */
    void transportOrderDelete(Long transportOrderId) throws BusinessException;

    /**
     * 转运订单跟踪
     *
     * @param memberId 会员id
     * @param waybill  运单号
     */
    ExTransportOrderDto transportOrderTrace(Long memberId, String waybill) throws BusinessException;
}
