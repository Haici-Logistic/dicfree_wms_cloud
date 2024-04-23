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
package com.dicfree.ms.pda.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.pda.common.dto.ex.ExSnWeightDto;
import com.dicfree.ms.pda.service.PdaProductArrivalOrderService;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExProductArrivalOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSkuDto;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ProductSkuWeightData;
import com.dicfree.ms.wms.repository.jpa.dao.ProductArrivalOrderItemDao;
import com.dicfree.ms.wms.repository.jpa.entity.ProductArrivalOrderItem;
import com.dicfree.ms.wms.service.ProductArrivalOrderService;
import com.dicfree.ms.wms.service.ProductSkuService;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/10/26
 */
@Service("pdaProductArrivalOrderService")
public class PdaProductArrivalOrderServiceImpl implements PdaProductArrivalOrderService {

    @Resource
    private ProductArrivalOrderService productArrivalOrderService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductArrivalOrderItemDao productArrivalOrderItemDao;

    @Resource
    private QingFlowClient qingFlowClient;

    @Override
    public ExSnWeightDto productArrivalOrderSnInboundInit(String productCode) throws BusinessException {
        //1. 查询商品信息
        ExProductSkuDto productSkuDto = productSkuService.productSkuInfo(productCode);

        //2. 查询到货单详情信息
        ProductArrivalOrderItem productArrivalOrderItem = productArrivalOrderItemDao.findUndoBySkuCode(productSkuDto.getCode())
                .orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_ARRIVAL_ORDER_ITEM_NOT_FOUND));

        //3. 从轻流查询数据
        ProductSkuWeightData productSkuWeightData = qingFlowClient.queryProductSkuWeight(productSkuDto.getCode()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_SKU_NOT_FOUND));

        //4. 组装数据
        ExSnWeightDto snWeightDto = new ExSnWeightDto();
        snWeightDto.setWeight(productSkuWeightData.getWeight() == null ? null : Float.valueOf(productSkuWeightData.getWeight()));
        snWeightDto.setLength(productSkuWeightData.getLength() == null ? null : Integer.valueOf(productSkuWeightData.getLength()));
        snWeightDto.setWidth(productSkuWeightData.getWidth() == null ? null : Integer.valueOf(productSkuWeightData.getWidth()));
        snWeightDto.setHeight(productSkuWeightData.getHeight() == null ? null : Integer.valueOf(productSkuWeightData.getHeight()));
        snWeightDto.setInboundCount(productArrivalOrderItem.getInboundCount());
        snWeightDto.setTotalCount(productArrivalOrderItem.getTotalCount());
        return snWeightDto;
    }

    @Override
    public Long productArrivalOrderSnInbound(String productCode, ExSnWeightDto snWeightDto) throws BusinessException {
        return productArrivalOrderService.productArrivalOrderSnInbound(null, productCode, snWeightDto);
    }

    @Override
    public Integer arrivalOrderOnShelfUndoListCount() throws BusinessException {
        return productArrivalOrderService.productArrivalOrderOnShelfUndoListCount();
    }

    @Override
    public List<ExProductArrivalOrderDto> arrivalOrderOnShelfUndoListAll() throws BusinessException {
        //根据设备区域获取未上架的订单
        return productArrivalOrderService.productArrivalOrderOnShelfUndoListAll();
    }

    @Override
    public void arrivalOrderSnOnShelf(String productCode, String shelfNo) throws BusinessException {
        productArrivalOrderService.productArrivalOrderSnOnShelf(productCode, shelfNo);
    }
}
