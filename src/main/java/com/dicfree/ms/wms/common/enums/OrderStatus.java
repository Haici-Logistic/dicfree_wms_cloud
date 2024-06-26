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
package com.dicfree.ms.wms.common.enums;

import java.util.IntSummaryStatistics;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2023/9/2
 */
public enum OrderStatus {

    /**
     * 待处理
     */
    PENDING,

    /**
     * 处理中
     */
    IN_PROCESSING,

    /**
     * 已完成
     */
    DONE;

    public static OrderStatus determineStatus(Map<OrderStatus, IntSummaryStatistics> statusSummary, int totalCount){
        IntSummaryStatistics defaultSum = new IntSummaryStatistics();
        if (statusSummary.getOrDefault(DONE, defaultSum).getCount() == totalCount) {
            return DONE;
        } else if (statusSummary.getOrDefault(PENDING, defaultSum).getCount() == totalCount) {
            return PENDING;
        } else {
            return IN_PROCESSING;
        }
    };
}
