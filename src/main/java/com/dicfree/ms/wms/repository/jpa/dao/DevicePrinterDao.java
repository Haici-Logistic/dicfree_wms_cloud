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
import com.dicfree.ms.wms.repository.jpa.entity.DevicePrinter;

import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
public interface DevicePrinterDao extends BaseDao<DevicePrinter, Long> {

    /**
     * 查询用户绑定的打印机
     *
     * @param userId 用户id
     * @return 打印机
     */
    Optional<DevicePrinter> findByUserId(Long userId);

    /**
     * 查询用户绑定的打印机
     *
     * @param username 用户名
     * @return 打印机
     */
    Optional<DevicePrinter> findByUsername(String username);

    /**
     * 查询用户绑定的打印机
     *
     * @param sn 打印机SN
     * @return 打印机
     */
    Optional<DevicePrinter> findBySn(String sn);

}
