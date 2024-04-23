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
import com.dicfree.ms.wms.repository.jpa.entity.ProductWaveTask;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/11/06
 */
public interface ProductWaveTaskDao extends BaseDao<ProductWaveTask, Long> {

    /**
     * 查找未处理的波次任务数量
     *
     * @return 未处理的波次任务数量
     */
    @Query("select count(mdo) from ProductWaveTask mdo where mdo.offShelfStatus != com.dicfree.ms.wms.common.enums.OrderStatus.DONE")
    Integer countUnOffShelf();

    /**
     * 查找未处理的波次任务
     *
     * @return 未处理的波次任务
     */
    @Query("select mdo from ProductWaveTask mdo where mdo.offShelfStatus != com.dicfree.ms.wms.common.enums.OrderStatus.DONE order by mdo.createTime asc")
    List<ProductWaveTask> findAllUnOffShelf();

    /**
     * 查找未集货的波次任务
     *
     * @return 未集货的波次任务
     */
    @Query("select mdo from ProductWaveTask mdo where mdo.offShelfStatus = com.dicfree.ms.wms.common.enums.OrderStatus.DONE and mdo.collection = false order by mdo.createTime asc")
    List<ProductWaveTask> findAllUnCollection();

    /**
     * 查找波次任务
     *
     * @param uniqueNo 波次任务号
     * @return 波次任务
     */
    @Query("select mdo from ProductWaveTask mdo where mdo.uniqueNo = ?1")
    Optional<ProductWaveTask> findByUniqueNo(String uniqueNo);

    /**
     * 查找波次任务
     *
     * @param collectionAreaCodeOrUniqueNo 集货区编码或波次任务号
     * @return 波次任务
     */
    @Query("select mdo from ProductWaveTask mdo where (mdo.uniqueNo = ?1 or mdo.collectionAreaCode = ?1) and mdo.collection = false")
    Optional<ProductWaveTask> findByCollectionAreaCodeOrUniqueNo(String collectionAreaCodeOrUniqueNo);
}
