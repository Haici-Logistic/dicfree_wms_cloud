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
import cn.jzyunqi.common.model.PageDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxArrivalOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxArrivalOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import com.dicfree.ms.wms.common.dto.ex.query.ExBoxArrivalOrderQueryDto;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import com.dicfree.ms.wms.common.enums.StocktakeType;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
public interface BoxArrivalOrderService {

    /**
     * 整装箱入库订单已到库
     *
     * @param supplier     客户
     * @param thirdOrderNo 第三方单号
     */
    void boxArrivalOrderArrival(String supplier, String thirdOrderNo) throws BusinessException;

    /**
     * 未完成排库的订单列表
     *
     * @return 未完成排库的订单列表
     */
    List<ExBoxArrivalOrderDto> boxArrivalOrderLocationUndoList();

    /**
     * 订单库位锁定
     *
     * @param boxArrivalOrderId 整装箱入库订单id
     * @param wholeLocation     整库库位
     * @param bulkLocation      混库库位
     */
    void boxArrivalOrderLocationLock(Long boxArrivalOrderId, String wholeLocation, String bulkLocation) throws BusinessException;

    /**
     * 订单库位分配
     *
     * @param boxSkuCode 箱号
     * @param location  库位
     */
    void boxArrivalOrderLocationAssign(String boxSkuCode, String location);

    /**
     * 整装箱入库订单分页
     *
     * @param boxArrivalOrderQueryDto 查询条件
     * @param pageable                分页条件
     * @return 结果
     */
    PageDto<ExBoxArrivalOrderDto> boxArrivalOrderPage(ExBoxArrivalOrderQueryDto boxArrivalOrderQueryDto, Pageable pageable);

    /**
     * 整装箱入库订单明细列表
     *
     * @param boxArrivalOrderId 整装箱入库订单id
     * @return 结果
     */
    List<ExBoxArrivalOrderItemDto> boxArrivalOrderItemList(Long boxArrivalOrderId);

    /**
     * 整装箱入库订单添加
     *
     * @param boxArrivalOrderDto 整装箱入库订单信息
     */
    ExBoxArrivalOrderDto boxArrivalOrderAdd(ExBoxArrivalOrderDto boxArrivalOrderDto) throws BusinessException;

    /**
     * 整装箱入库订单状态编辑
     *
     * @param uniqueNo   入库订单号
     * @param boxSkuCode 箱号
     * @param status     状态
     */
    void boxArrivalOrderStatusEdit(String uniqueNo, String boxSkuCode, OrderStatus status) throws BusinessException;

    /**
     * 未完成整装箱入库订单列表
     *
     * @return 未完成整装箱入库订单列表
     */
    List<ExBoxArrivalOrderDto> boxArrivalOrderUndoList();

    /**
     * 整装箱入库订单未入库整装箱列表
     *
     * @param supplier     客户
     * @param thirdOrderNo 第三方单号
     * @return 未入库整装箱列表
     */
    ExBoxArrivalOrderDto boxArrivalOrderInfo(String supplier, String thirdOrderNo) throws BusinessException;

    /**
     * 整装箱入库订单未入库整装箱列表
     *
     * @param boxArrivalOrderId 整装箱入库订单id
     * @return 未入库整装箱列表
     */
    List<ExBoxArrivalOrderItemDto> boxArrivalOrderItemUndoCalcList(Long boxArrivalOrderId);

    /**
     * 整装箱入库订单已入库整装箱列表
     *
     * @param boxArrivalOrderId 整装箱入库订单id
     * @return 已入库整装箱列表
     */
    List<ExBoxArrivalOrderItemDto> boxArrivalOrderItemDoneCalcList(Long boxArrivalOrderId);

    /**
     * 未入库整装箱序列箱明细
     *
     * @param boxArrivalOrderId     整装箱入库订单id
     * @param boxArrivalOrderItemId 整装箱入库订单详情id
     * @return 序列箱明细
     */
    List<ExBoxSnDto> boxArrivalOrderItemSnUndoList(Long boxArrivalOrderId, Long boxArrivalOrderItemId) throws BusinessException;

    /**
     * 未入库箱序列箱打印
     *
     * @param userId  用户id
     * @param boxSnId 整装箱入库订单id
     */
    void boxArrivalOrderBoxSnUndoPrint(Long userId, Long boxSnId) throws BusinessException;

    /**
     * 未入库整装箱序列箱打印
     *
     * @param userId                用户id
     * @param boxArrivalOrderId     整装箱入库订单id
     * @param boxArrivalOrderItemId 整装箱入库订单详情id
     */
    void boxArrivalOrderItemSnUndoPrint(Long userId, Long boxArrivalOrderId, Long boxArrivalOrderItemId) throws BusinessException;

    /**
     * 未入库整装箱全部序列箱打印
     *
     * @param userId            用户id
     * @param boxArrivalOrderId 整装箱入库订单id
     */
    void boxArrivalOrderItemSnUndoPrintAll(Long userId, Long boxArrivalOrderId) throws BusinessException;

    /**
     * 整装箱入库订单未入库序列箱详情
     *
     * @param boxArrivalOrderId 整装箱入库订单id
     * @return 未入库序列箱详情
     */
    List<ExBoxSnDto> boxArrivalOrderBoxSnList(Long boxArrivalOrderId);

    /**
     * 整装箱入库订单序列箱详情
     *
     * @param boxArrivalOrderId     整装箱入库订单id
     * @param boxArrivalOrderItemId 整装箱入库订单详情id
     * @return 序列箱详情
     */
    List<ExBoxSnDto> boxArrivalOrderItemSnList(Long boxArrivalOrderId, Long boxArrivalOrderItemId) throws BusinessException;

    /**
     * 整装箱入库订单序列箱入库
     *
     * @param userId            用户id
     * @param boxArrivalOrderId 整装箱入库订单id
     * @param type              查询方式
     * @param first             第一个参数
     * @param second            第二个参数
     */
    Long boxArrivalOrderSnInbound(Long userId, Long boxArrivalOrderId, StocktakeType type, String first, String second) throws BusinessException;
}
