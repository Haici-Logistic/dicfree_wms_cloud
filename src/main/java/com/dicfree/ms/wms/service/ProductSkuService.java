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
import com.dicfree.ms.wms.common.dto.ex.ExProductSkuDto;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
public interface ProductSkuService {

    /**
     * 电商商品添加
     *
     * @param productSkuDto 电商商品信息
     */
    void productSkuAdd(ExProductSkuDto productSkuDto) throws BusinessException;

    /**
     * 电商商品添加
     *
     * @param productCode 产品编码
     * @param supplier    客户
     */
    void productSkuAddQuick(String productCode, String supplier);

    /**
     * 电商商品编码组装
     *
     * @param productCode 产品编码
     * @param supplier    客户
     * @return 电商商品编码
     */
    String getProductSkuCode(String productCode, String supplier);

    /**
     * 拆分电商商品编码
     *
     * @param productSkuCode 电商商品编码
     * @return 产品编码
     */
    String getProductSkuProductCode(String productSkuCode);

    /**
     * 电商商品信息
     *
     * @param productCode 产品编码
     * @return 电商商品信息
     */
    ExProductSkuDto productSkuInfo(String productCode) throws BusinessException;

    /**
     * 电商商品信息
     *
     * @param productSkuCode 商品编码
     * @return 电商商品信息
     */
    ExProductSkuDto productSkuInfoWithNull(String productSkuCode) ;
}
