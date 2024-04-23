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
import com.dicfree.ms.wms.repository.jpa.entity.CollectionArea;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/11/06
 */
public interface CollectionAreaDao extends BaseDao<CollectionArea, Long> {

    /**
     * 查找空闲的集货区
     */
    @Query("select ca from CollectionArea ca where ca.status = com.dicfree.ms.wms.common.enums.CollectionAreaStatus.EMPTY")
    List<CollectionArea> findEmptyArea();

    /**
     * 根据集货区编码查找
     *
     * @param collectionAreaCode 集货区编码
     * @return 集货区
     */
    @Query("select ca from CollectionArea ca where ca.code = ?1")
    Optional<CollectionArea> findByCode(String collectionAreaCode);
}
