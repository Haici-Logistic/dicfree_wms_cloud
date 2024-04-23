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
package com.dicfree.ms.wms.service;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
public interface BoxSnService {

    /**
     * 创建序列箱
     *
     * @param boxArrivalOrderId 整装箱入库订单id
     * @param boxSkuCode     箱号
     * @param totalCount     总数
     * @param batchNo     批次号
     * @return 序列箱列表
     */
    List<ExBoxSnDto> boxSnAdd(Long boxArrivalOrderId, String boxSkuCode, Integer totalCount, String batchNo) throws BusinessException;

    /**
     * 获取整装箱入库订单详情序列箱信息
     *
     * @param boxArrivalOrderItemId 整装箱入库订单id
     * @return 入库序列箱信息
     */
    List<ExBoxSnDto> boxSnArrivalList(Long boxArrivalOrderItemId);

    /**
     * 获取整装箱入库订单序列箱信息
     *
     * @param boxArrivalOrderItemIdList 整装箱入库订单id列表
     * @return 入库序列箱信息
     */
    List<ExBoxSnDto> boxSnArrivalList(List<Long> boxArrivalOrderItemIdList);

    /**
     * 获取整装箱出库订单序列箱信息（全部已出库）
     *
     * @param boxDeliveryOrderItemId 整装箱出库订单id
     * @return 已出库序列箱信息
     */
    List<ExBoxSnDto> boxSnDeliveryList(Long boxDeliveryOrderItemId);

    /**
     * 获取整装箱出库订单序列箱信息（全部已出库）
     *
     * @param boxDeliveryOrderItemIdList 整装箱出库订单id列表
     * @return 已出库序列箱信息
     */
    List<ExBoxSnDto> boxSnDeliveryList(List<Long> boxDeliveryOrderItemIdList);

    /**
     * 获取未入库序列箱信息
     *
     * @param boxArrivalOrderItemId 整装箱入库订单id
     * @return 未入库序列箱信息
     */
    List<ExBoxSnDto> boxSnUnArrivalList(Long boxArrivalOrderItemId);

    /**
     * 获取未入库序列箱信息
     *
     * @param boxArrivalOrderItemId 整装箱入库订单id
     * @return 未入库序列箱信息
     */
    List<ExBoxSnDto> boxSnUnArrivalPrintList(Long boxArrivalOrderItemId);

    /**
     * 获取未入库序列箱信息
     *
     * @param boxArrivalOrderItemIdList 整装箱入库订单id列表
     * @return 未入库序列箱信息
     */
    List<ExBoxSnDto> boxSnUnArrivalPrintList(List<Long> boxArrivalOrderItemIdList);

    /**
     * 获取未出库序列箱信息
     *
     * @param boxSkuCode 箱号列表
     * @return 出库序列箱信息
     */
    List<ExBoxSnDto> boxSnUnDeliveryList(String boxSkuCode);

    /**
     * 获取未出库序列箱信息
     *
     * @param boxSkuCodeList 箱号列表
     * @return 出库序列箱信息
     */
    List<ExBoxSnDto> boxSnUnDeliveryList(List<String> boxSkuCodeList);

    /**
     * 查询序列箱信息
     *
     * @param boxSnCode 序列箱号
     * @return 序列箱详情
     */
    ExBoxSnDto skuDetailByBoxSnCode(String boxSnCode) throws BusinessException;

    /**
     * 序列箱详情
     *
     * @param boxSnId 序列箱id
     * @return 序列箱详情
     */
    ExBoxSnDto skuDetailById(Long boxSnId) throws BusinessException;

    /**
     * 查询序列箱信息
     *
     * @param supplierBoxSnCode 客户序列箱号
     * @return 序列箱详情
     */
    ExBoxSnDto skuInfoBySupplierBoxSnCode(String supplierBoxSnCode) throws BusinessException;

    /**
     * 序列箱入库
     *
     * @param boxArrivalOrderItemId 整装箱入库订单id
     * @param boxSnCode          序列箱号
     */
    String boxSnInbound(Long boxArrivalOrderItemId, @Nullable String boxSnCode) throws BusinessException;

    /**
     * 序列箱出库
     *
     * @param boxDeliveryOrderItemId 整装箱出库订单id
     * @param boxSkuCode          箱号
     * @param boxSnCode           序列箱号
     */
    String boxSnOutbound(Long boxDeliveryOrderItemId, String boxSkuCode, @Nullable String boxSnCode) throws BusinessException;

    /**
     * 标记序列箱已打印
     *
     * @param boxSnId 序列箱id
     */
    void boxSnMarkPrinted(Long boxSnId) throws BusinessException;
}
