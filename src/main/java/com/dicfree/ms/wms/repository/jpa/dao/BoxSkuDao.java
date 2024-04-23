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
import com.dicfree.ms.wms.repository.jpa.entity.BoxSku;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
public interface BoxSkuDao extends BaseDao<BoxSku, Long> {

    /**
     * 统计箱号数量
     *
     * @param code 箱号
     * @return 箱号数量
     */
    @Query("select count(sku)>0 from BoxSku sku where sku.code = ?1")
    boolean isCodeExists(String code);

    /**
     * 查询整装箱信息
     *
     * @param code 箱号
     * @return 整装箱信息
     */
    @Query("select sku from BoxSku sku where sku.code = ?1")
    Optional<BoxSku> findByCode(String code);

    /**
     * 通过客户箱号查询整装箱信息
     *
     * @param supplierBoxCode 客户箱号
     * @return 整装箱信息
     */
    @Query("select sku from BoxSku sku where sku.supplierBoxCode = ?1")
    List<BoxSku> findBySupplierBoxCode(String supplierBoxCode);
}
