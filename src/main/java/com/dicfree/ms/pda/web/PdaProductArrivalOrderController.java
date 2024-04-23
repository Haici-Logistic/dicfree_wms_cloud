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
package com.dicfree.ms.pda.web;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import com.dicfree.ms.PdaRestBaseController;
import com.dicfree.ms.pda.common.dto.ex.ExSnWeightDto;
import com.dicfree.ms.pda.service.PdaProductArrivalOrderService;
import com.dicfree.ms.wms.common.dto.ex.ExProductArrivalOrderDto;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * PDA - 到货单接口
 *
 * @author wiiyaya
 * @date 2023/10/26
 */
@RestController
@Validated
public class PdaProductArrivalOrderController extends PdaRestBaseController {

    @Resource
    private PdaProductArrivalOrderService pdaArrivalOrderService;

    /**
     * 序列商品入库
     *
     * @param productCode 商品编码
     * @return 电商商品入库订单详情id
     */
    @PostMapping(value = "/pda/productArrivalOrder/snInboundInit")
    public ExSnWeightDto productArrivalOrderSnInboundInit(@RequestParam @NotBlank String productCode) throws BusinessException {
        return pdaArrivalOrderService.productArrivalOrderSnInboundInit(productCode);
    }

    /**
     * 序列商品入库
     *
     * @param productCode 商品编码
     * @param snWeightDto SN重量信息
     * @return 电商商品入库订单详情id
     */
    @PostMapping(value = "/pda/productArrivalOrder/snInbound")
    public Long productArrivalOrderSnInbound(@RequestParam @NotBlank String productCode, @RequestBody @Validated(ExSnWeightDto.Inbound.class) ExSnWeightDto snWeightDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, snWeightDto, ExSnWeightDto.Inbound.class);
        return pdaArrivalOrderService.productArrivalOrderSnInbound(productCode, snWeightDto);
    }

    /**
     * 未完成的上架任务数量统计
     */
    @PostMapping(value = "/pda/productArrivalOrder/onShelfUndoListCount")
    public Integer arrivalOrderOnShelfUndoListCount() throws BusinessException {
        return pdaArrivalOrderService.arrivalOrderOnShelfUndoListCount();
    }

    /**
     * 未完成的上架任务
     */
    @PostMapping(value = "/pda/productArrivalOrder/onShelfUndoListAll")
    public List<ExProductArrivalOrderDto> arrivalOrderOnShelfUndoListAll() throws BusinessException {
        return pdaArrivalOrderService.arrivalOrderOnShelfUndoListAll();
    }

    /**
     * 序列商品上架
     *
     * @param productCode 商品编码
     * @param shelfNo     上架货位
     * @return 成功
     */
    @PostMapping(value = "/pda/productArrivalOrder/snOnShelf")
    public String arrivalOrderSnOnShelf(@RequestParam @NotBlank String productCode, @RequestParam @NotBlank String shelfNo) throws BusinessException {
        pdaArrivalOrderService.arrivalOrderSnOnShelf(productCode, shelfNo);
        return "success";
    }
}
