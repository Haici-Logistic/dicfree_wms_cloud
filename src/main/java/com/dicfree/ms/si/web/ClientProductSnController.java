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
package com.dicfree.ms.si.web;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import com.dicfree.ms.ClientRestBaseController;
import com.dicfree.ms.si.service.ClientProductSnService;
import com.dicfree.ms.wms.common.dto.ex.ExProductSkuDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import jakarta.annotation.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端 - 电商商品SN管理接口
 *
 * @author wiiyaya
 * @date 2023/10/26
 */
@RestController
@Validated
public class ClientProductSnController extends ClientRestBaseController {

    @Resource
    private ClientProductSnService clientProductSnService;

    /**
     * 电商商品SN更换货架
     *
     * @param productSnDto 电商商品新增信息
     * @return 成功
     */
    @PostMapping(value = "/client/productSn/shelfChange")
    public String productSnShelfChange(@RequestBody @Validated(ExProductSnDto.ShelfChange.class) ExProductSnDto productSnDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, productSnDto, ExProductSnDto.ShelfChange.class);
        clientProductSnService.snShelfChange(productSnDto);
        return "success";
    }

    /**
     * 电商商品SN更换SKU code
     *
     * @param productSnDto 电商商品SKU code信息
     * @return 成功
     */
    @PostMapping(value = "/client/productSn/productSkuCodeChange")
    public String productSnProductSkuCodeChange(@RequestBody @Validated(ExProductSnDto.ProductSkuCodeChange.class) ExProductSnDto productSnDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, productSnDto, ExProductSnDto.ProductSkuCodeChange.class);
        clientProductSnService.productSnProductSkuCodeChange(productSnDto);
        return "success";
    }

    /**
     * 电商商品SN更换quality
     *
     * @param productSnDto 电商商品SKU code信息
     * @return 成功
     */
    @PostMapping(value = "/client/productSn/qualityChange")
    public String productSnQualityChange(@RequestBody @Validated(ExProductSnDto.QualityChange.class) ExProductSnDto productSnDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, productSnDto, ExProductSnDto.QualityChange.class);
        clientProductSnService.productSnQualityChange(productSnDto);
        return "success";
    }

    /**
     * 电商商品SN更换quality
     *
     * @param productSnDto 电商商品SKU code信息
     * @return 成功
     */
    @PostMapping(value = "/client/productSn/reOnShelf")
    public String productSnReOnShelf(@RequestBody @Validated(ExProductSnDto.ReOnShelf.class) ExProductSnDto productSnDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, productSnDto, ExProductSnDto.ReOnShelf.class);
        clientProductSnService.productSnReOnShelf(productSnDto);
        return "success";
    }
}
