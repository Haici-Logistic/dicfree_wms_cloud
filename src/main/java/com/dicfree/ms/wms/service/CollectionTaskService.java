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
import cn.jzyunqi.common.model.PageDto;
import com.dicfree.ms.wms.common.dto.ex.ExCollectionTaskDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxDeliveryOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import com.dicfree.ms.wms.common.dto.ex.query.ExCollectionTaskQueryDto;
import com.dicfree.ms.wms.common.enums.StocktakeType;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
public interface CollectionTaskService {

    /**
     * 集货任务分页
     *
     * @param collectionTaskQueryDto 查询条件
     * @param pageable               分页条件
     * @return 结果
     */
    PageDto<ExCollectionTaskDto> collectionTaskPage(ExCollectionTaskQueryDto collectionTaskQueryDto, Pageable pageable);

    /**
     * 集货任务添加初始化
     *
     * @return 初始化信息
     */
    ExCollectionTaskDto collectionTaskAddInit();

    /**
     * 集货任务添加
     *
     * @param collectionTaskDto 集货任务信息
     */
    void collectionTaskAdd(ExCollectionTaskDto collectionTaskDto) throws BusinessException;

    /**
     * 集货任务编辑初始化
     *
     * @param collectionTaskId 集货任务id
     * @return 集货任务信息
     */
    ExCollectionTaskDto collectionTaskEditInit(Long collectionTaskId) throws BusinessException;

    /**
     * 集货任务编辑
     *
     * @param collectionTaskDto 集货任务信息
     */
    void collectionTaskEdit(ExCollectionTaskDto collectionTaskDto) throws BusinessException;

    /**
     * 集货任务详情
     *
     * @param collectionTaskId 集货任务id
     * @return 集货任务信息
     */
    ExCollectionTaskDto collectionTaskDetail(Long collectionTaskId) throws BusinessException;

    /**
     * 集货任务删除
     *
     * @param collectionTaskId 集货任务id
     */
    void collectionTaskDelete(Long collectionTaskId) throws BusinessException;

    /**
     * 未完成集货任务列表
     *
     * @return 集货任务列表
     */
    List<ExCollectionTaskDto> collectionTaskUndoList();

    /**
     * 集货任务明细列表
     *
     * @param collectionTaskId 集货任务id
     * @return 结果
     */
    List<ExBoxDeliveryOrderItemDto> collectionTaskItemList(Long collectionTaskId);

    /**
     * 集货任务未入库整装箱列表
     *
     * @param collectionTaskId 集货任务id
     * @return 未入库整装箱列表
     */
    List<ExBoxDeliveryOrderItemDto> collectionTaskItemUndoCalcList(Long collectionTaskId);

    /**
     * 集货任务已入库整装箱列表
     *
     * @param collectionTaskId 集货任务id
     * @return 已入库整装箱列表
     */
    List<ExBoxDeliveryOrderItemDto> collectionTaskItemDoneCalcList(Long collectionTaskId);

    /**
     * 整装箱出库订单未出库序列箱明细
     *
     * @param collectionTaskId    集货任务id
     * @param boxDeliveryOrderId     整装箱出库订单id
     * @param boxDeliveryOrderItemId 整装箱出库订单详情id
     * @return 序列箱明细
     */
    List<ExBoxSnDto> collectionTaskItemBoxSnUndoList(Long collectionTaskId, Long boxDeliveryOrderId, Long boxDeliveryOrderItemId) throws BusinessException;

    /**
     * 集货任务未出库序列箱明细
     *
     * @param collectionTaskId 集货任务id
     * @return 序列箱明细
     */
    List<ExBoxSnDto> collectionTaskBoxSnList(Long collectionTaskId);

    /**
     * 集货任务序列箱明细
     *
     * @param collectionTaskId    集货任务id
     * @param boxDeliveryOrderId     整装箱出库订单id
     * @param boxDeliveryOrderItemId 整装箱出库订单详情id
     * @return 序列箱明细
     */
    List<ExBoxSnDto> collectionTaskItemBoxSnList(Long collectionTaskId, Long boxDeliveryOrderId, Long boxDeliveryOrderItemId) throws BusinessException;

    /**
     * 集货任务序列箱出库
     *
     * @param collectionTaskId 集货任务id
     * @param type             查询方式
     * @param first            第一个参数
     * @param second           第二个参数
     */
    void collectionTaskBoxSnOutbound(Long collectionTaskId, StocktakeType type, String first, String second) throws BusinessException;
}
