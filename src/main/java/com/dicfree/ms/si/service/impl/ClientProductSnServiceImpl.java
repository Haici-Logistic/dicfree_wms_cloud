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
package com.dicfree.ms.si.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.si.service.ClientProductSnService;
import com.dicfree.ms.wms.common.dto.ex.ExProductSkuDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import com.dicfree.ms.wms.common.enums.ShelfStatus;
import com.dicfree.ms.wms.common.enums.StockStatus;
import com.dicfree.ms.wms.service.ProductSkuService;
import com.dicfree.ms.wms.service.ProductSnService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author wiiyaya
 * @date 2024/1/16
 */
@Service("clientProductSnService")
public class ClientProductSnServiceImpl implements ClientProductSnService {

    @Resource
    private ProductSnService productSnService;

    @Override
    public void snShelfChange(ExProductSnDto productSnDto) throws BusinessException {
        productSnService.productSnShelfChange(productSnDto.getCode(), productSnDto.getShelfNo());
    }

    @Override
    public void productSnProductSkuCodeChange(ExProductSnDto productSnDto) throws BusinessException {
        productSnService.productSnProductSkuCodeChange(productSnDto.getCode(), productSnDto.getProductSkuCode());
    }

    @Override
    public void productSnQualityChange(ExProductSnDto productSnDto) throws BusinessException {
        productSnService.productSnQualityChange(productSnDto.getCode(), productSnDto.getQuality());
    }

    @Override
    public void productSnReOnShelf(ExProductSnDto productSnDto) throws BusinessException {
        productSnService.productSnReOnShelf(productSnDto.getCode(), productSnDto.getShelfNo());
    }
}
