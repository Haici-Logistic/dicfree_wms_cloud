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
package com.dicfree.ms.pda.service;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.pda.common.dto.ex.ExSnWeightDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductArrivalOrderDto;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/10/26
 */
public interface PdaProductArrivalOrderService {

    /**
     * 到货单序列商品入库初始化
     *
     * @param productCode 电商商品编码
     * @return 电商商品重量信息
     */
    ExSnWeightDto productArrivalOrderSnInboundInit(String productCode) throws BusinessException;

    /**
     * 到货单序列商品入库
     *
     * @param productCode 电商商品编码
     * @param snWeightDto 电商商品重量信息
     */
    Long productArrivalOrderSnInbound(String productCode, ExSnWeightDto snWeightDto) throws BusinessException;

    /**
     * 获取全部未完成的上架任务
     *
     * @return 到货单列表
     */
    Integer arrivalOrderOnShelfUndoListCount() throws BusinessException;

    /**
     * 获取全部未完成的上架任务
     *
     * @return 到货单列表
     */
    List<ExProductArrivalOrderDto> arrivalOrderOnShelfUndoListAll() throws BusinessException;

    /**
     * 序列商品上架
     *
     * @param productCode 商品编码
     * @param shelfNo     上架货位
     */
    void arrivalOrderSnOnShelf(String productCode, String shelfNo) throws BusinessException;
}
