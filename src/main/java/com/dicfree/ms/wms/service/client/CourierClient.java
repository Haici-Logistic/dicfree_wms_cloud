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

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/1
 */
public interface CourierClient {

    /**
     * 跟踪快递单号情况
     *
     * @param waybill 运单号
     * @return 快递信息
     */
    List<ExTracingDto> trackOrder(String waybill, Object callbackParams) throws BusinessException;

    /**
     * 生成面单PDF
     *
     * @param waybill 运单号
     * @return PDF
     */
    default String genWaybillPdf(String waybill) throws BusinessException{
        throw new UnsupportedOperationException("This courier dose not support online generate waybill pdf!");
    }
}
