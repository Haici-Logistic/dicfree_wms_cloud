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
package com.dicfree.ms.wms.common.constant;

/**
 * @author wiiyaya
 * @date 2023/8/27
 */
public class WmsEmitConstant {

    /**
     * 电商商品出库订单批量派单事件
     */
    public static final String WMS_PRODUCT_DELIVERY_ORDER_SERVICE_ADD_BATCH = "dfwms:productDeliveryOrderService:addBatch";
    public static final String WMS_PRODUCT_DELIVERY_ORDER_SERVICE_ADD_BATCH_CONDITION = "#event.getName().equals('"+ WMS_PRODUCT_DELIVERY_ORDER_SERVICE_ADD_BATCH +"')";

    /**
     * 入库订单锁库事件
     */
    public static final String WMS_BOX_ARRIVAL_ORDER_SERVICE_LOCATION_LOCK = "dfwms:boxArrivalOrderService:locationLock";
    public static final String WMS_BOX_ARRIVAL_ORDER_SERVICE_LOCATION_LOCK_CONDITION = "#event.getName().equals('"+ WMS_BOX_ARRIVAL_ORDER_SERVICE_LOCATION_LOCK +"')";

    /**
     * 集货任务新增事件
     */
    public static final String WMS_COLLECTION_TASK_SERVICE_TASK_ADD = "dfwms:collectionTaskService:taskAdd";
    public static final String WMS_COLLECTION_TASK_SERVICE_TASK_ADD_CONDITION = "#event.getName().equals('"+ WMS_COLLECTION_TASK_SERVICE_TASK_ADD +"')";
}
