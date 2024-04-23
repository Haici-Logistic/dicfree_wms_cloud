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
package com.dicfree.ms.si.common.constant;

/**
 * @author wiiyaya
 * @date 2023/8/27
 */
public class SiEmitConstant {

    /**
     * 整装箱到货订单新增事件
     */
    public static final String SI_CLIENT_BOX_ARRIVAL_ORDER_SERVICE_BOX_ARRIVAL_ORDER_ADD = "dfsi:clientBoxArrivalOrderService:boxArrivalOrderAdd";
    public static final String SI_CLIENT_BOX_ARRIVAL_ORDER_SERVICE_BOX_ARRIVAL_ORDER_ADD_CONDITION = "#event.getName().equals('"+ SI_CLIENT_BOX_ARRIVAL_ORDER_SERVICE_BOX_ARRIVAL_ORDER_ADD +"')";

    /**
     * 整装箱到货订单库位更新事件
     */
    public static final String SI_CLIENT_BOX_ARRIVAL_ORDER_SERVICE_LOCATION_EDIT = "dfsi:clientBoxArrivalOrderService:boxSkuLocationEdit";
    public static final String SI_CLIENT_BOX_ARRIVAL_ORDER_SERVICE_LOCATION_EDIT_CONDITION = "#event.getName().equals('"+ SI_CLIENT_BOX_ARRIVAL_ORDER_SERVICE_LOCATION_EDIT +"')";

    /**
     * 电商商品到货订单新增事件
     */
    public static final String SI_CLIENT_ARRIVAL_ORDER_SERVICE_ARRIVAL_ORDER_ADD = "dfsi:clientArrivalOrderService:arrivalOrderAdd";
    public static final String SI_CLIENT_ARRIVAL_ORDER_SERVICE_ARRIVAL_ORDER_ADD_CONDITION = "#event.getName().equals('"+ SI_CLIENT_ARRIVAL_ORDER_SERVICE_ARRIVAL_ORDER_ADD +"')";
}
