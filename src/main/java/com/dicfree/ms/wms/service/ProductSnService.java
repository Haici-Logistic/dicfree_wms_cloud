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
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
public interface ProductSnService {

    /**
     * 序列商品添加
     *
     * @param productArrivalOrderId 序列商品到货单id
     * @param productSkuCode        电商商品编码
     * @param totalCount            总数
     * @return 序列商品列表
     */
    List<ExProductSnDto> productSnAdd(Long productArrivalOrderId, String productSkuCode, Integer totalCount) throws BusinessException;

    /**
     * 获取未入库序列商品信息
     *
     * @param productArrivalOrderItemId 到货单详情id
     * @return 未入库序列商品信息
     */
    List<ExProductSnDto> productSnUnArrivalList(Long productArrivalOrderItemId);

    /**
     * 获取未入库序列商品信息
     *
     * @param productArrivalOrderItemId 到货单id
     * @return 未入库序列商品信息
     */
    List<ExProductSnDto> productSnUnArrivalPrintList(Long productArrivalOrderItemId);

    /**
     * 序列商品入库
     *
     * @param productArrivalOrderItemId 到货单详情id
     * @param quality                   是否完整货物
     */
    String productSnInbound(Long productArrivalOrderItemId, Boolean quality) throws BusinessException;

    /**
     * 序列商品在货架列表
     *
     * @param productSkuCode 商品编码
     * @return 在货架列表
     */
    List<ExProductSnDto> productSnOnShelfList(String productSkuCode);

    /**
     * 序列商品上架
     *
     * @param productSkuCode 商品编码
     * @param shelfNo        上架货位
     * @return 上架的序列商品
     */
    ExProductSnDto productSnOnShelf(String productSkuCode, String shelfNo) throws BusinessException;

    /**
     * 序列商品货架更换
     *
     * @param productSnCode 序列商品编码
     * @param shelfNo       货架号
     */
    void productSnShelfChange(String productSnCode, String shelfNo) throws BusinessException;

    /**
     * 序列商品SKU code更换
     *
     * @param productSnCode  序列商品编码
     * @param productSkuCode 商品编码
     */
    void productSnProductSkuCodeChange(String productSnCode, String productSkuCode) throws BusinessException;

    /**
     * 序列商品quality更换
     *
     * @param productSnCode 序列商品编码
     * @param quality       是否完整货物
     */
    void productSnQualityChange(String productSnCode, Boolean quality) throws BusinessException;

    /**
     * 序列商品重新上架
     *
     * @param productSnCode 序列商品编码
     * @param shelfNo       货架号
     */
    void productSnReOnShelf(String productSnCode, String shelfNo) throws BusinessException;

    /**
     * 序列商品出库核验未完成列表
     *
     * @param productSkuCode 商品编码
     * @return 未完成列表
     */
    List<ExProductSnDto> productSnUnVerifyList(String productSkuCode);

    /**
     * 序列商品分拣(快速分拣)
     *
     * @param productDeliveryOrderItemId 出库单详情id
     */
    void productSnSorting(List<Long> productDeliveryOrderItemId) throws BusinessException;

    /**
     * 序列商品分拣
     *
     * @param productDeliveryOrderItemId 出库单详情id
     * @param productSkuCode             商品编码
     */
    void productSnSorting(Long productDeliveryOrderItemId, String productSkuCode) throws BusinessException;

    /**
     * 序列商品出库核验
     *
     * @param productDeliveryOrderItemId 出库单详情id
     * @param productSkuCode             商品编码
     */
    String productSnVerify(Long productDeliveryOrderItemId, String productSkuCode) throws BusinessException;

    /**
     * 序列商品未下架列表
     *
     * @param productSkuCode 商品编码
     * @param count          数量
     * @return 未下架列表
     */
    List<ExProductSnDto> productSnOffShelfUndoList(String productSkuCode, int count);

    /**
     * 序列商品已下架列表
     *
     * @param productDeliveryOrderItemId 出库单详情id
     * @return 已下架列表
     */
    List<ExProductSnDto> productSnOffShelfDoneList(Long productDeliveryOrderItemId);

    /**
     * 序列商品下架
     *
     * @param productDeliveryOrderItemId 出库单详情id
     * @param shelfNo                    货位
     * @param productSkuCode             商品编码
     */
    String productSnOffShelf(Long productDeliveryOrderItemId, String shelfNo, String productSkuCode) throws BusinessException;

    /**
     * 序列商品未分拣列表
     *
     * @param productDeliveryOrderItemIdList 出库单详情id列表
     * @return 未分拣列表
     */
    List<ExProductSnDto> productSnUnSortingList(List<Long> productDeliveryOrderItemIdList);
}
