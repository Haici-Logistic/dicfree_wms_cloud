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
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductWaveTaskDto;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/6
 */
public interface PdaProductWaveTaskService {

    /**
     * 未完成的波次任务统计
     *
     * @param devicePdaCode PDA设备编码
     * @return 未完成的波次任务统计
     */
    Integer productWaveTaskOffShelfUndoCount(String devicePdaCode) throws BusinessException;

    /**
     * 未完成的波次任务列表
     *
     * @param devicePdaCode PDA设备编码
     * @return 未完成的波次任务列表
     */
    List<ExProductWaveTaskDto> productWaveTaskOffShelfUndoList(String devicePdaCode) throws BusinessException;

    /**
     * 最早未完成的波次任务
     *
     * @param devicePdaCode     PDA设备编码
     * @param productWaveTaskId 波次任务ID
     * @return 最早未完成的波次任务
     */
    ExProductWaveTaskDto productWaveTaskOffShelfUndoDetail(String devicePdaCode, Long productWaveTaskId) throws BusinessException;

    /**
     * SN下架
     *
     * @param devicePdaCode     PDA设备编码
     * @param productWaveTaskId 波次任务ID
     * @param shelfNo           货架号
     * @param productCode       商品编码
     */
    void productWaveTaskSnOffShelf(String devicePdaCode, Long productWaveTaskId, String shelfNo, String productCode) throws BusinessException;

    /**
     * 已完成下架但未集货的波次列表
     *
     * @return 未集货的波次列表
     */
    List<ExProductWaveTaskDto> productWaveTaskCollectionUndoList();

    /**
     * 未完成下架且未集货的波次列表
     *
     * @return 未集货的波次列表
     */
    List<ExProductWaveTaskDto> productWaveTaskCollectionOffShelfUndoList();

    /**
     * 未完成下架且未集货的波次列表统计
     *
     * @return 统计数量
     */
    Integer productWaveTaskCollectionOffShelfUndoCount();

    /**
     * 波次集货完成
     */
    void productWaveTaskCollectionDone(String collectionAreaCode) throws BusinessException;

    /**
     * 波次订单分篮初始化
     *
     * @param productWaveTaskNo 波次任务号
     * @return 波次订单分
     */
    List<ExProductDeliveryOrderDto> productWaveTaskBasketInit(String productWaveTaskNo) throws BusinessException;

    /**
     * 出库订单绑定篮子
     *
     * @param waybill  运单号
     * @param basketNo 篮子号
     */
    void productWaveTaskBasketBind(String waybill, String basketNo) throws BusinessException;

    /**
     * 出库订单包裹称重
     *
     * @param waybill 运单号
     * @param weight  重量
     */
    void productWaveTaskWeighing(String waybill, Float weight) throws BusinessException;
}
