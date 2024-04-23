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
package com.dicfree.ms.wms.service;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductWaveTaskDto;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/11/06
 */
public interface ProductWaveTaskService {

    /**
     * 波次任务生成
     *
     * @param productWaveTaskDto 波次任务信息
     */
    void waveTaskGenerate(ExProductWaveTaskDto productWaveTaskDto) throws BusinessException;

    /**
     * 波次任务下架未完成列表
     *
     * @return 下架未完成订单列表
     */
    List<ExProductWaveTaskDto> productWaveTaskOffShelfUndoList();

    /**
     * 波次任务下架未完成详情
     *
     * @param productWaveTaskId 波次任务ID
     * @return 波次任务下架未完成详情
     */
    ExProductWaveTaskDto productWaveTaskOffShelfDetail(Long productWaveTaskId) throws BusinessException;

    /**
     * 下架
     *
     * @param productWaveTaskId 波次任务ID
     * @param shelfNo           货架号
     * @param productCode       商品编码
     * @param shelfAreaCode     货架区域编码
     */
    void productWaveTaskSnOffShelf(Long productWaveTaskId, String shelfNo, String productCode, String shelfAreaCode) throws BusinessException;

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
     *
     *  @param collectionAreaCodeOrUniqueNo 波次任务号或集货区域码
     */
    void productWaveTaskCollectionDone(String collectionAreaCodeOrUniqueNo) throws BusinessException;

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
     * 出库波次信息
     *
     * @param productWaveTaskNo 波次任务号
     * @return 波次信息
     */
    ExProductWaveTaskDto productWaveTaskInfoWithNull(String productWaveTaskNo) throws BusinessException;

    /**
     * 出库波次订单信息
     *
     * @param productWaveTaskId 波次任务ID
     * @return 出库波次订单信息
     */
    List<ExProductDeliveryOrderDto> productWaveTaskOrderUndoCalcList(Long productWaveTaskId) throws BusinessException;

    /**
     * 波次任务已完成的订单列表
     *
     * @param productWaveTaskId 波次任务ID
     * @return 波次任务未完成的订单列表
     */
    List<ExProductDeliveryOrderDto> productWaveTaskOrderDoneCalcList(Long productWaveTaskId) throws BusinessException;

    /**
     * 出库订单未完成的商品列表
     *
     * @param productWaveTaskId      波次任务ID
     * @param productDeliveryOrderId 电商商品出库订单ID
     * @return 商品列表
     */
    List<ExProductSnDto> productWaveTaskSnUndoList(Long productWaveTaskId, Long productDeliveryOrderId) throws BusinessException;

    /**
     * 商品分拣
     *
     * @param productWaveTaskId 波次任务ID
     * @param productCode       产品编号
     * @return 篮子编号
     */
    String productWaveTaskSnSorting(Long productWaveTaskId, String productCode) throws BusinessException;
}
