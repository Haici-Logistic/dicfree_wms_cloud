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
import com.dicfree.ms.si.service.ClientProductSkuService;
import com.dicfree.ms.wms.common.dto.ex.ExProductSkuDto;
import com.dicfree.ms.wms.service.ProductSkuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author wiiyaya
 * @date 2023/10/26
 */
@Service("clientProductSkuService")
public class ClientProductSkuServiceImpl implements ClientProductSkuService {

    @Resource
    private ProductSkuService skuService;

    @Override
    public void skuAdd(ExProductSkuDto skuDto) throws BusinessException {
        skuService.productSkuAdd(skuDto);
    }
}
