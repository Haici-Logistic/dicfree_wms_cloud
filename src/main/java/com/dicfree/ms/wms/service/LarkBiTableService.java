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
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/9/18
 */
public interface LarkBiTableService {


    /**
     * 同步整装箱出库订单信息到飞书
     *
     * @param collectionNoVirtual    集货任务虚拟编号
     * @param boxDeliveryOrderIdList 发货订单ID列表
     */
    void boxDeliveryOrderSyncToLark(String collectionNoVirtual, List<Long> boxDeliveryOrderIdList) throws BusinessException;
}
