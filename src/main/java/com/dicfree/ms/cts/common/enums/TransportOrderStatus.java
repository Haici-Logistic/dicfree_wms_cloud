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
package com.dicfree.ms.cts.common.enums;

/**
 * @author wiiyaya
 * @date 2023/11/28
 */
public enum TransportOrderStatus {

    /**
     * 待处理状态
     */
    pending,

    /**
     * 头程入库状态
     */
    firstInbound,

    /**
     * 头程发货状态
     */
    firstDelivery,

    /**
     * 尾程入库状态
     */
    lastInbound,

    /**
     * 尾程派送状态
     */
    lastDelivery,

    /**
     * 尾程签收状态
     */
    lastSigning
}
