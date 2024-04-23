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
import com.dicfree.ms.si.service.ClientProductSkuService;
import com.dicfree.ms.wms.common.dto.ex.ExProductSkuDto;
import jakarta.annotation.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端 - 电商商品管理接口
 *
 * @author wiiyaya
 * @date 2023/10/26
 */
@RestController
@Validated
public class ClientProductSkuController extends ClientRestBaseController {

    @Resource
    private ClientProductSkuService clientProductSkuService;

    /**
     * 电商商品新增
     *
     * @param productSkuDto 电商商品新增信息
     * @return 随机取件码
     */
    @PostMapping(value = "/client/productSku/add")
    public String productSkuAdd(@RequestBody @Validated(ExProductSkuDto.Add.class) ExProductSkuDto productSkuDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, productSkuDto, ExProductSkuDto.Add.class);
        clientProductSkuService.skuAdd(productSkuDto);
        return "success";
    }
}
