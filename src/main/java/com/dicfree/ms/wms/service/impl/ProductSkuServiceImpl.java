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
package com.dicfree.ms.wms.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExProductSkuDto;
import com.dicfree.ms.wms.repository.jpa.dao.ProductSkuDao;
import com.dicfree.ms.wms.repository.jpa.entity.ProductSku;
import com.dicfree.ms.wms.service.ProductSkuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
@Service("productSkuService")
public class ProductSkuServiceImpl implements ProductSkuService {

    @Resource
    private ProductSkuDao productSkuDao;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void productSkuAdd(ExProductSkuDto productSkuDto) throws BusinessException {
        //这里校验是否和数据库数据重复
        if (productSkuDao.isProductCodeExists(productSkuDto.getProductCode())) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SKU_EXISTS, productSkuDto.getProductCode());
        }

        String productSkuCode = getProductSkuCode(productSkuDto.getProductCode(), productSkuDto.getSupplier());
        //这里校验是否和数据库数据重复
        if (productSkuDao.isCodeExists(productSkuCode)) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SKU_EXISTS, productSkuCode);
        }

        ProductSku productSku = new ProductSku();
        productSku.setCode(productSkuCode);
        productSku.setProductCode(productSkuDto.getProductCode());
        productSku.setSupplier(productSkuDto.getSupplier());
        productSkuDao.save(productSku);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void productSkuAddQuick(String productCode, String supplier) {
        String productSkuCode = getProductSkuCode(productCode, supplier);
        ProductSku productSku = new ProductSku();
        productSku.setCode(productSkuCode);
        productSku.setProductCode(productCode);
        productSku.setSupplier(supplier);
        productSkuDao.save(productSku);
    }

    @Override
    public String getProductSkuCode(String productCode, String supplier) {
        return StringUtilPlus.join(productCode, StringUtilPlus.HYPHEN, supplier);
    }

    @Override
    public String getProductSkuProductCode(String productSkuCode) {
        return StringUtilPlus.split(productSkuCode, StringUtilPlus.HYPHEN)[0];
    }

    @Override
    public ExProductSkuDto productSkuInfo(String productCode) throws BusinessException {
        ProductSku productSku = productSkuDao.findByProductCode(productCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SKU_NOT_FOUND));

        ExProductSkuDto productSkuDto = new ExProductSkuDto();
        productSkuDto.setId(productSku.getId());
        productSkuDto.setCode(productSku.getCode());
        productSkuDto.setProductCode(productSku.getProductCode());
        productSkuDto.setSupplier(productSku.getSupplier());
        return productSkuDto;
    }

    @Override
    public ExProductSkuDto productSkuInfoWithNull(String productSkuCode) {
        return productSkuDao.findByCode(productSkuCode).map(productSku -> {
            ExProductSkuDto productSkuDto = new ExProductSkuDto();
            productSkuDto.setId(productSku.getId());
            productSkuDto.setCode(productSku.getCode());
            productSkuDto.setProductCode(productSku.getProductCode());
            productSkuDto.setSupplier(productSku.getSupplier());
            return productSkuDto;
        }).orElse(null);
    }
}
