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
import com.dicfree.ms.pda.common.dto.ex.ExSnWeightDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductArrivalOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductArrivalOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import com.dicfree.ms.wms.common.enums.OrderStatus;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
public interface ProductArrivalOrderService {

    /**
     * 电商商品到货单添加
     *
     * @param productArrivalOrderDto 电商商品到货单信息
     */
    ExProductArrivalOrderDto productArrivalOrderAdd(ExProductArrivalOrderDto productArrivalOrderDto) throws BusinessException;

    /**
     * 电商商品到货单状态编辑
     *
     * @param thirdOrderNo   轻流电商商品到货单号
     * @param productSkuCode 电商商品编码
     * @param status         状态
     */
    void productAarrivalOrderStatusEdit(String thirdOrderNo, String productSkuCode, OrderStatus status) throws BusinessException;

    /**
     * 未完成电商商品到货单列表
     *
     * @return 未完成电商商品到货单列表
     */
    List<ExProductArrivalOrderDto> productAarrivalOrderUndoList();

    /**
     * 电商商品到货单未入库电商商品列表
     *
     * @param productArrivalOrderId 电商商品到货单id
     * @return 未入库电商商品列表
     */
    List<ExProductArrivalOrderItemDto> productAarrivalOrderItemUndoCalcList(Long productArrivalOrderId);

    /**
     * 电商商品到货单已入库电商商品列表
     *
     * @param productArrivalOrderId 电商商品到货单id
     * @return 已入库电商商品列表
     */
    List<ExProductArrivalOrderItemDto> productAarrivalOrderItemDoneCalcList(Long productArrivalOrderId);

    /**
     * 未入库序列商品明细
     *
     * @param productArrivalOrderId     到货单id
     * @param productArrivalOrderItemId 到货单详情id
     * @return 序列商品明细
     */
    List<ExProductSnDto> productAarrivalOrderItemSnUndoList(Long productArrivalOrderId, Long productArrivalOrderItemId) throws BusinessException;

    /**
     * 未入库序列箱打印
     *
     * @param userId                    用户id
     * @param productArrivalOrderId     到货单id
     * @param productArrivalOrderItemId 到货单详情id
     * @param printCount                打印数量
     */
    void productArrivalOrderItemSnUndoPrint(Long userId, Long productArrivalOrderId, Long productArrivalOrderItemId, Integer printCount) throws BusinessException;

    /**
     * 到货单序列商品入库
     *
     * @param productArrivalOrderId 到货单id,可以为空
     * @param productCode           电商商品编码
     * @param snWeightDto           序列商品信息
     */
    Long productArrivalOrderSnInbound(Long productArrivalOrderId, String productCode, ExSnWeightDto snWeightDto) throws BusinessException;

    /**
     * 序列商品上架
     *
     * @param productCode 商品编码
     * @param shelfNo     上架货位
     */
    void productArrivalOrderSnOnShelf(String productCode, String shelfNo) throws BusinessException;

    /**
     * 未完成的待上架入库单数量统计
     *
     * @return 统计数量
     */
    Integer productArrivalOrderOnShelfUndoListCount();

    /**
     * 未完成的待上架入库单
     *
     * @return 入库单列表
     */
    List<ExProductArrivalOrderDto> productArrivalOrderOnShelfUndoListAll();
}
