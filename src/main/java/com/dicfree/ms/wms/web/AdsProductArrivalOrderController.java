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
import cn.jzyunqi.common.utils.CurrentUserUtils;
import cn.jzyunqi.ms.AdsRestBaseController;
import com.dicfree.ms.pda.common.dto.ex.ExSnWeightDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductArrivalOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductArrivalOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import com.dicfree.ms.wms.service.ProductArrivalOrderService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Portal - 个人用户 - 电商商品入库订单接口
 *
 * @author wiiyaya
 * @date 2023/09/02
 */
@RestController
@Validated
public class AdsProductArrivalOrderController extends AdsRestBaseController {

    @Resource
    private ProductArrivalOrderService productArrivalOrderService;

    /**
     * 未完成序列商品入库订单列表
     *
     * @return 未完成序列商品入库订单KV
     */
    @PostMapping(value = "/ads/productArrivalOrder/undoList")
    public List<ExProductArrivalOrderDto> productArrivalOrderUndoList() throws BusinessException {
        return productArrivalOrderService.productAarrivalOrderUndoList();
    }

    /**
     * 入库订单未入库电商商品统计信息
     *
     * @param id 电商商品入库订单id
     * @return 未入库电商商品统计信息
     */
    @PostMapping(value = "/ads/productArrivalOrder/itemUndoCalcList")
    public List<ExProductArrivalOrderItemDto> productArrivalOrderItemUndoCalcList(@RequestParam @NotNull Long id) throws BusinessException {
        return productArrivalOrderService.productAarrivalOrderItemUndoCalcList(id);
    }

    /**
     * 入库订单已入库电商商品统计信息
     *
     * @param id 电商商品入库订单id
     * @return 已入库电商商品统计信息
     */
    @PostMapping(value = "/ads/productArrivalOrder/itemDoneCalcList")
    public List<ExProductArrivalOrderItemDto> productArrivalOrderItemDoneCalcList(@RequestParam @NotNull Long id) throws BusinessException {
        return productArrivalOrderService.productAarrivalOrderItemDoneCalcList(id);
    }

    /**
     * 入库订单指定电商商品中序列商品入库明细
     *
     * @param id                     电商商品入库订单id
     * @param productArrivalOrderItemId 电商商品入库订单详情id
     * @return 序列商品明细
     */
    @PostMapping(value = "/ads/productArrivalOrder/itemSnUndoList")
    public List<ExProductSnDto> productArrivalOrderItemSnUndoList(@RequestParam @NotNull Long id, @RequestParam @NotNull Long productArrivalOrderItemId) throws BusinessException {
        return productArrivalOrderService.productAarrivalOrderItemSnUndoList(id, productArrivalOrderItemId);
    }

    /**
     * 电商商品入库订单指定电商商品中序列商品未入库打印
     *
     * @param id                     电商商品入库订单id
     * @param productArrivalOrderItemId 电商商品入库订单详情id
     * @param printCount             打印数量
     */
    @PostMapping(value = "/ads/productArrivalOrder/itemSnUndoPrint")
    public void productArrivalOrderItemSnUndoPrint(@RequestParam @NotNull Long id, @RequestParam @NotNull Long productArrivalOrderItemId, @RequestParam @NotNull Integer printCount) throws BusinessException {
        Long currentUserId = CurrentUserUtils.currentUserId();
        productArrivalOrderService.productArrivalOrderItemSnUndoPrint(currentUserId, id, productArrivalOrderItemId, printCount);
    }


    /**
     * 序列商品入库
     *
     * @param id    电商商品入库订单id
     * @param first 第一个参数
     * @return 电商商品入库订单详情id
     */
    @PostMapping(value = "/ads/productArrivalOrder/snInbound")
    public Long productArrivalOrderSnInbound(@RequestParam @NotNull Long id, @RequestParam @NotBlank String first) throws BusinessException {
        return productArrivalOrderService.productArrivalOrderSnInbound(id, first, new ExSnWeightDto());
    }
}
