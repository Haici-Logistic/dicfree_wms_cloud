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
package com.dicfree.ms.wms.service.client;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.wms.common.dto.ex.ExTracingDto;
import com.dicfree.ms.wms.common.dto.ex.logistiq.LogistiqTraceCbReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/3
 */
@Component
@Slf4j
public class LogistiqClient implements CourierClient {

    @Override
    public List<ExTracingDto> trackOrder(String waybill, Object callbackParams) throws BusinessException {
        LogistiqTraceCbReq logistiqTraceCbReq = (LogistiqTraceCbReq) callbackParams;
        log.info("Logistiq trackOrderToNotice[{}] receive data [{}]", waybill, logistiqTraceCbReq);
        ExTracingDto tracingDto = new ExTracingDto();
        tracingDto.setTimeStamp(logistiqTraceCbReq.getTimeStamp());
        tracingDto.setScanLocation(logistiqTraceCbReq.getScanLocation());
        //tracingDto.setDeliveryTo(logistiqTraceCbReq.);
        tracingDto.setStatusCode(logistiqTraceCbReq.getStatusCode());
        tracingDto.setStatus(logistiqTraceCbReq.getStatus());
        return List.of(tracingDto);
    }
}
