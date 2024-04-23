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
import com.dicfree.ms.wms.repository.jpa.entity.CollectionTask;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
public interface CollectionTaskDao extends BaseDao<CollectionTask, Long> {

    /**
     * 查找未完成的集货订单（待处理/处理中）
     *
     * @return 未完成的集货订单
     */
    @Query("select ct from CollectionTask ct where ct.status in (com.dicfree.ms.wms.common.enums.OrderStatus.PENDING, com.dicfree.ms.wms.common.enums.OrderStatus.IN_PROCESSING)")
    List<CollectionTask> findUndoCollectionTask();
}
