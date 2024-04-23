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
package com.dicfree.ms.wms.web.listener;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.support.spring.aop.event.AopEvent;
import com.dicfree.ms.wms.common.constant.WmsEmitConstant;
import com.dicfree.ms.wms.common.dto.ex.ExCollectionTaskDto;
import com.dicfree.ms.wms.service.BoxArrivalOrderService;
import com.dicfree.ms.wms.service.CollectionTaskService;
import com.dicfree.ms.wms.service.LarkBiTableService;
import com.dicfree.ms.wms.service.ProductDeliveryOrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/1
 */
@Slf4j
@Component
public class WmsMessageListener {

    @Resource
    private BoxArrivalOrderService boxArrivalOrderService;

    @Resource
    private ProductDeliveryOrderService productDeliveryOrderService;

    @Resource
    private LarkBiTableService larkBiTableService;

    @Async
    @EventListener(condition = WmsEmitConstant.WMS_BOX_ARRIVAL_ORDER_SERVICE_LOCATION_LOCK_CONDITION)
    public void boxArrivalOrderLocationLock(AopEvent event) throws BusinessException {
        log.info("==========boxArrivalOrderLocationLock message receive===========");
        boxArrivalOrderService.boxArrivalOrderLocationAssign(null, null);
    }

    @Async
    @EventListener(condition = WmsEmitConstant.WMS_PRODUCT_DELIVERY_ORDER_SERVICE_ADD_BATCH_CONDITION)
    @SuppressWarnings("unchecked")
    public void productDeliveryOrderAddBatch(AopEvent event) throws BusinessException {
        log.info("==========productDeliveryOrderAddBatch message receive===========");
        List<Long> productDeliveryOrderIdList = (List<Long>) event.getResult();
        productDeliveryOrderService.productDeliveryOrderAddSync(productDeliveryOrderIdList);
    }

    @Async
    @EventListener(condition = WmsEmitConstant.WMS_COLLECTION_TASK_SERVICE_TASK_ADD_CONDITION)
    @SuppressWarnings("unchecked")
    public void collectionTaskAdd(AopEvent event) throws BusinessException {
        log.info("==========collectionTaskAdd message receive===========");
        ExCollectionTaskDto collectionTaskDto = (ExCollectionTaskDto) event.getArgs()[0];
        larkBiTableService.boxDeliveryOrderSyncToLark(collectionTaskDto.getCollectionNoVirtual(), collectionTaskDto.getBoxDeliveryOrderIdList());
    }
}
