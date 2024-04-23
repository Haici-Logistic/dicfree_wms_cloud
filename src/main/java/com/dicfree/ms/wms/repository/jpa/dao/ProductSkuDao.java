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
package com.dicfree.ms.wms.repository.jpa.dao;

import cn.jzyunqi.common.support.hibernate.persistence.dao.BaseDao;
import com.dicfree.ms.wms.repository.jpa.entity.ProductSku;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
public interface ProductSkuDao extends BaseDao<ProductSku, Long> {

    /**
     * 检查产品编码是否已经存在
     *
     * @param productCode 商品编码
     * @return 是否存在
     */
    @Query("select count(s)>0 from ProductSku s where s.productCode = ?1")
    boolean isProductCodeExists(String productCode);

    /**
     * 检查电商商品编码是否已经存在
     *
     * @param skuCode 电商商品编码
     * @return 是否存在
     */
    @Query("select count(s)>0 from ProductSku s where s.code = ?1")
    boolean isCodeExists(String skuCode);

    /**
     * 通过电商商品编码查询商品信息
     *
     * @param skuCode 电商商品编码
     * @return 商品信息
     */
    @Query("select s from ProductSku s where s.code = ?1")
    Optional<ProductSku> findByCode(String skuCode);

    /**
     * 过产品编码查询商品信息
     *
     * @param productCode 产品编码
     * @return 商品信息
     */
    @Query("select s from ProductSku s where s.productCode = ?1")
    Optional<ProductSku> findByProductCode(String productCode);
}
