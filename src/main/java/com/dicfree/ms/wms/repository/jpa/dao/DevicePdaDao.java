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
import com.dicfree.ms.wms.repository.jpa.entity.DevicePda;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
public interface DevicePdaDao extends BaseDao<DevicePda, Long> {

    /**
     * 根据用户id查找设备信息
     *
     * @param userId 用户id
     * @return 设备信息
     */
    @Query("select dp from DevicePda dp where dp.userId = ?1")
    Optional<DevicePda> findByUserId(Long userId);

    /**
     * 根据设备编码查找设备信息
     *
     * @param code 设备编码
     * @return 设备信息
     */
    @Query("select dp from DevicePda dp where dp.code = ?1")
    Optional<DevicePda> findByCode(String code);
}
