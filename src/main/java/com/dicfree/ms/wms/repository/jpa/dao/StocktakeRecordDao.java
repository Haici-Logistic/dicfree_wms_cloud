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
import com.dicfree.ms.wms.common.enums.StocktakeDirection;
import com.dicfree.ms.wms.repository.jpa.entity.StocktakeRecord;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
public interface StocktakeRecordDao extends BaseDao<StocktakeRecord, Long> {

    /**
     * 获取盘点数据
     *
     * @param direction 方向
     * @return 盘点数据
     */
    @Query("select sr from StocktakeRecord sr where sr.direction = ?1 order by sr.createTime desc limit 10000")
    List<StocktakeRecord> findByDirection(StocktakeDirection direction);
}
