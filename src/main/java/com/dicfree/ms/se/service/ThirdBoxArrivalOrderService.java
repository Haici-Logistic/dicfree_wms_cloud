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
package com.dicfree.ms.se.service;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.wms.common.dto.ex.ExBoxArrivalOrderDto;

/**
 * @author wiiyaya
 * @date 2023/11/13
 */
public interface ThirdBoxArrivalOrderService {

    /**
     * 整装箱入库订单添加
     *
     * @param boxArrivalOrderDto 整装箱入库订单信息
     */
    void boxArrivalOrderAdd(ExBoxArrivalOrderDto boxArrivalOrderDto) throws BusinessException;

    /**
     * 整装箱入库订单详情
     *
     * @param supplier     客户
     * @param thirdOrderNo 整装箱入库订单号
     * @return 整装箱入库订单详情
     */
    ExBoxArrivalOrderDto boxArrivalOrderInfo(String supplier, String thirdOrderNo) throws BusinessException;

    /**
     * 整装箱入库订单到货状态回调
     *
     * @param supplier      客户
     * @param thirdOrderNo  整装箱入库订单号
     * @param arrivalStatus 到货状态
     */
    void boxArrivalOrderTraceCallback(String supplier, String thirdOrderNo, String arrivalStatus) throws BusinessException;
}
