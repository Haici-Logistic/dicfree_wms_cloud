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
package com.dicfree.ms.si.web.listener;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.support.spring.aop.event.AopEvent;
import com.dicfree.ms.wms.common.constant.WmsEmitConstant;
import com.dicfree.ms.wms.common.dto.ex.ExProductArrivalOrderDto;
import com.dicfree.ms.wms.service.BoxArrivalOrderService;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import com.dicfree.ms.si.common.constant.SiEmitConstant;
import com.dicfree.ms.wms.common.dto.ex.ExBoxArrivalOrderDto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author wiiyaya
 * @date 2023/8/27.
 */
@Slf4j
@Component("siMessageListener")
public class SiMessageListener {

    @Resource
    private QingFlowClient qingFlowClient;

    @Resource
    private BoxArrivalOrderService boxArrivalOrderService;

    @Async
    @EventListener(condition = SiEmitConstant.SI_CLIENT_BOX_ARRIVAL_ORDER_SERVICE_BOX_ARRIVAL_ORDER_ADD_CONDITION)
    public void boxArrivalOrderAdd(AopEvent event) throws BusinessException {
        log.info("==========boxArrivalOrderAdd message receive===========");
        ExBoxArrivalOrderDto boxArrivalOrderDto = (ExBoxArrivalOrderDto) event.getResult();
        boxArrivalOrderDto.getBoxArrivalOrderItemList().forEach(boxArrivalOrderItemDto -> {
            boxArrivalOrderItemDto.getBoxSnList().forEach(boxSnDto -> {
                try {
                    qingFlowClient.boxSnCreationNotice(boxArrivalOrderDto.getUniqueNo(), boxSnDto.getBoxSkuCode(), boxSnDto.getCode(), boxSnDto.getSerialNo(), boxSnDto.getPcs(), boxSnDto.getSupplierBoxSnCode());
                    Thread.sleep(30);
                } catch (Exception e) {
                    log.error("===qingflow boxSnNotices failed and boxSnCode is: [{}]", boxSnDto.getCode(), e);
                }
            });
        });
    }

    @Async
    @EventListener(condition = SiEmitConstant.SI_CLIENT_BOX_ARRIVAL_ORDER_SERVICE_LOCATION_EDIT_CONDITION)
    public void boxArrivalOrderLocationEdit(AopEvent event) throws BusinessException {
        log.info("==========boxArrivalOrderLocationEdit message receive===========");
        String boxSkuCode = (String) event.getArgs()[0];
        String location = (String) event.getArgs()[1];
        boxArrivalOrderService.boxArrivalOrderLocationAssign(boxSkuCode, location);
    }

    @Async
    @EventListener(condition = SiEmitConstant.SI_CLIENT_ARRIVAL_ORDER_SERVICE_ARRIVAL_ORDER_ADD_CONDITION)
    public void productArrivalOrderAdd(AopEvent event) throws BusinessException {
        log.info("==========productArrivalOrderAdd message receive===========");
        ExProductArrivalOrderDto arrivalOrderDto = (ExProductArrivalOrderDto) event.getResult();
        arrivalOrderDto.getProductArrivalOrderItemList().forEach(productArrivalOrderItemDto -> {
            productArrivalOrderItemDto.getProductSnList().forEach(productSnDto -> {
                try {
                    qingFlowClient.productSnCreationNotice(arrivalOrderDto.getThirdOrderNo(), productSnDto.getProductSkuCode(), productSnDto.getCode(), productSnDto.getSerialNo(), productSnDto.getPcs());
                    Thread.sleep(30);
                } catch (Exception e) {
                    log.error("===qingflow productSnNotices failed and productSn is: [{}]", productSnDto.getCode(), e);
                }
            });
        });
    }
}
