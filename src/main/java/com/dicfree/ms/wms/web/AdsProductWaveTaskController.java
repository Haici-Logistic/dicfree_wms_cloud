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
import cn.jzyunqi.ms.AdsRestBaseController;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductWaveTaskDto;
import com.dicfree.ms.wms.service.ProductWaveTaskService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Portal - 超级管理员 - 电商商品出库波次接口
 *
 * @author wiiyaya
 * @date 2023/11/7
 */
@RestController
@Validated
public class AdsProductWaveTaskController extends AdsRestBaseController {

    @Resource
    private ProductWaveTaskService productWaveTaskService;

    /**
     * 出库波次信息
     *
     * @param uniqueNo 出库波次号
     * @return 出库波次信息
     */
    @PostMapping(value = "/ads/productWaveTask/info")
    public ExProductWaveTaskDto productWaveTaskInfo(@RequestParam @NotBlank String uniqueNo) throws BusinessException {
        return productWaveTaskService.productWaveTaskInfoWithNull(uniqueNo);
    }

    /**
     * 波次任务未完成的订单列表
     *
     * @param id 波次任务ID
     * @return 波次任务未完成的订单列表
     */
    @PostMapping(value = "/ads/productWaveTask/orderUndoCalcList")
    public List<ExProductDeliveryOrderDto> productWaveTaskOrderUndoCalcList(@RequestParam @NotNull Long id) throws BusinessException {
        return productWaveTaskService.productWaveTaskOrderUndoCalcList(id);
    }

    /**
     * 波次任务已完成的订单列表
     *
     * @param id 波次任务ID
     * @return 波次任务未完成的订单列表
     */
    @PostMapping(value = "/ads/productWaveTask/orderDoneCalcList")
    public List<ExProductDeliveryOrderDto> productWaveTaskOrderDoneCalcList(@RequestParam @NotNull Long id) throws BusinessException {
        return productWaveTaskService.productWaveTaskOrderDoneCalcList(id);
    }

    /**
     * 出库订单未完成的商品列表
     *
     * @param id                     波次任务ID
     * @param productDeliveryOrderId 电商商品出库订单ID
     * @return 商品列表
     */
    @PostMapping(value = "/ads/productWaveTask/orderSnUndoList")
    public List<ExProductSnDto> productWaveTaskOrderSnUndoList(@RequestParam @NotNull Long id, @RequestParam @NotNull Long productDeliveryOrderId) throws BusinessException {
        return productWaveTaskService.productWaveTaskSnUndoList(id, productDeliveryOrderId);
    }

    /**
     * 商品分拣
     *
     * @param id    波次任务ID
     * @param first 产品编号
     * @return 篮子编号
     */
    @PostMapping(value = "/ads/productWaveTask/snSorting")
    public String productWaveTaskSnSorting(@RequestParam @NotNull Long id, @RequestParam @NotBlank String first) throws BusinessException {
        return productWaveTaskService.productWaveTaskSnSorting(id, first);
    }
}
