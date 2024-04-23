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
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.ms.AdsRestBaseController;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import com.dicfree.ms.wms.common.dto.ex.query.ExProductDeliveryOrderQueryDto;
import com.dicfree.ms.wms.service.ProductDeliveryOrderService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Portal - 超级管理员 - 电商商品出库订单接口
 *
 * @author wiiyaya
 * @date 2023/10/28
 */
@RestController
@Validated
public class AdsProductDeliveryOrderController extends AdsRestBaseController {

    @Resource
    private ProductDeliveryOrderService productDeliveryOrderService;

    /**
     * 电商商品出库订单分页
     *
     * @param productDeliveryOrderQueryDto 查询条件
     * @param pageable                     分页参数|cn.jzyunqi.common.model.PageRequestDto
     * @return 电商商品出库订单信息
     */
    @PostMapping(value = "/ads/productDeliveryOrder/page")
    public PageDto<ExProductDeliveryOrderDto> productDeliveryOrderPage(@ModelAttribute ExProductDeliveryOrderQueryDto productDeliveryOrderQueryDto, Pageable pageable) {
        return productDeliveryOrderService.productDeliveryOrderPage(productDeliveryOrderQueryDto, pageable);
    }

    /**
     * 电商商品出库订单跟踪
     *
     * @param id 电商商品出库订单id
     * @return 电商商品出库订单信息
     */
    @PostMapping(value = "/ads/productDeliveryOrder/trace")
    public ExProductDeliveryOrderDto productDeliveryOrderTrace(@RequestParam @NotNull Long id) throws BusinessException {
        return productDeliveryOrderService.productDeliveryOrderTrace(id);
    }

    /**
     * 未完成出库订单列表
     *
     * @param waybill 运单号/第三方单号
     * @return 出库订单信息
     */
    @PostMapping(value = "/ads/productDeliveryOrder/info")
    public ExProductDeliveryOrderDto productDeliveryOrderInfo(@RequestParam @NotBlank String waybill) {
        return productDeliveryOrderService.productDeliveryOrderInfoWithNull(waybill);
    }

    /**
     * 出库订单未出库统计信息
     *
     * @param id 出库订单id
     * @return 未入库统计信息
     */
    @PostMapping(value = "/ads/productDeliveryOrder/itemUndoCalcList")
    public List<ExProductDeliveryOrderItemDto> productDeliveryOrderItemUndoCalcList(@RequestParam @NotNull Long id) throws BusinessException {
        return productDeliveryOrderService.productDeliveryOrderItemUndoCalcList(id);
    }

    /**
     * 出库订单已出库统计信息
     *
     * @param id 出库订单id
     * @return 已入库统计信息
     */
    @PostMapping(value = "/ads/productDeliveryOrder/itemDoneCalcList")
    public List<ExProductDeliveryOrderItemDto> productDeliveryOrderItemDoneCalcList(@RequestParam @NotNull Long id) throws BusinessException {
        return productDeliveryOrderService.productDeliveryOrderItemDoneCalcList(id);
    }

    /**
     * 出库订单指定整裝箱中序列商品出库明细
     *
     * @param id                         出库订单id
     * @param productDeliveryOrderItemId 出库订单详情id
     * @return 序列商品明细
     */
    @PostMapping(value = "/ads/productDeliveryOrder/itemSnUndoList")
    public List<ExProductSnDto> productDeliveryOrderItemSnUndoList(@RequestParam @NotNull Long id, @RequestParam @NotNull Long productDeliveryOrderItemId) throws BusinessException {
        return productDeliveryOrderService.productDeliveryOrderItemProductSnUndoList(id, productDeliveryOrderItemId);
    }

    /**
     * 序列商品出库校验
     *
     * @param id    出库任务id
     * @param first 第一个参数
     */
    @PostMapping(value = "/ads/productDeliveryOrder/snVerify")
    public void productDeliveryOrderProductSnVerify(@RequestParam @NotNull Long id, @RequestParam @NotBlank String first) throws BusinessException {
        productDeliveryOrderService.productDeliveryOrderProductSnVerify(id, first);
    }

    /**
     * 未完成出库订单列表
     *
     * @param waybill 运单号
     * @return 出库订单信息
     */
    @PostMapping(value = "/ads/productDeliveryOrder/waybillPrint")
    public ExProductDeliveryOrderDto productDeliveryOrderWaybillPrint(@RequestParam @NotBlank String waybill) throws BusinessException {
        return productDeliveryOrderService.productDeliveryOrderWaybillPrint(waybill);
    }
}
