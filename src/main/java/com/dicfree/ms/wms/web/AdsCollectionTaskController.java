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
package com.dicfree.ms.wms.web;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import cn.jzyunqi.common.utils.CurrentUserUtils;
import cn.jzyunqi.ms.AdsRestBaseController;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import com.dicfree.ms.wms.common.dto.ex.ExCollectionTaskDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxDeliveryOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.query.ExCollectionTaskQueryDto;
import com.dicfree.ms.wms.common.enums.StocktakeType;
import com.dicfree.ms.wms.service.CollectionTaskService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Portal - 超级管理员 - 集货任务接口
 *
 * @author wiiyaya
 * @date 2023/09/02
 */
@RestController
@Validated
public class AdsCollectionTaskController extends AdsRestBaseController {

    @Resource
    private CollectionTaskService collectionTaskService;

    /**
     * 集货任务分页
     *
     * @param collectionTaskQueryDto 查询条件
     * @param pageable              分页参数|cn.jzyunqi.common.model.PageRequestDto
     * @return 集货任务信息
     */
    @PostMapping(value = "/ads/collectionTask/page")
    public PageDto<ExCollectionTaskDto> collectionTaskPage(@ModelAttribute ExCollectionTaskQueryDto collectionTaskQueryDto, Pageable pageable) {
        return collectionTaskService.collectionTaskPage(collectionTaskQueryDto, pageable);
    }

    /**
     * 集货任务新增初始化
     *
     * @return 集货任务信息
     */
    @PostMapping(value = "/ads/collectionTask/addInit")
    public ExCollectionTaskDto collectionTaskAddInit() throws BusinessException {
        return collectionTaskService.collectionTaskAddInit();
    }

    /**
     * 集货任务新增
     *
     * @param collectionTaskDto 集货任务信息
     * @param bindingResult    校验信息
     */
    @PostMapping(value = "/ads/collectionTask/add")
    public void collectionTaskAdd(@RequestBody @Validated(ExCollectionTaskDto.Add.class) ExCollectionTaskDto collectionTaskDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, collectionTaskDto, ExCollectionTaskDto.Add.class);
        collectionTaskService.collectionTaskAdd(collectionTaskDto);
    }

    /**
     * 集货任务修改初始化
     *
     * @param id 集货任务id
     * @return 集货任务信息
     */
    @PostMapping(value = "/ads/collectionTask/editInit")
    public ExCollectionTaskDto collectionTaskEditInit(@RequestParam @NotNull Long id) throws BusinessException {
        return collectionTaskService.collectionTaskEditInit(id);
    }

    /**
     * 集货任务修改
     *
     * @param collectionTaskDto 集货任务信息
     * @param bindingResult    校验信息
     */
    @PostMapping(value = "/ads/collectionTask/edit")
    public void collectionTaskEdit(@RequestBody @Validated(ExCollectionTaskDto.Edit.class) ExCollectionTaskDto collectionTaskDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, collectionTaskDto, ExCollectionTaskDto.Edit.class);
        collectionTaskService.collectionTaskEdit(collectionTaskDto);
    }

    /**
     * 集货任务详情
     *
     * @param id 集货任务id
     * @return 集货任务信息
     */
    @PostMapping(value = "/ads/collectionTask/detail")
    public ExCollectionTaskDto collectionTaskDetail(@RequestParam @NotNull Long id) throws BusinessException {
        return collectionTaskService.collectionTaskDetail(id);
    }

    /**
     * 集货任务删除
     *
     * @param id 集货任务id
     */
    @PostMapping(value = "/ads/collectionTask/delete")
    public void collectionTaskDelete(@RequestParam @NotNull Long id) throws BusinessException {
        collectionTaskService.collectionTaskDelete(id);
    }

    /**
     * 集货任务明细列表
     *
     * @param id 集货任务id
     * @return 集货任务明细信息
     */
    @PostMapping(value = "/ads/collectionTask/itemList")
    public List<ExBoxDeliveryOrderItemDto> collectionTaskItemList(@RequestParam @NotNull Long id) {
        return collectionTaskService.collectionTaskItemList(id);
    }

    /**
     * 未完成集货任务列表
     *
     * @return 集货任务信息
     */
    @PostMapping(value = "/ads/collectionTask/undoList")
    public List<ExCollectionTaskDto> collectionTaskUndoList() throws BusinessException {
        return collectionTaskService.collectionTaskUndoList();
    }

    /**
     * 集货任务未出库整装箱统计信息
     *
     * @param id 集货任务id
     * @return 未入库整装箱统计信息
     */
    @PostMapping(value = "/ads/collectionTask/itemUndoCalcList")
    public List<ExBoxDeliveryOrderItemDto> collectionTaskItemUndoCalcList(@RequestParam @NotNull Long id) throws BusinessException {
        return collectionTaskService.collectionTaskItemUndoCalcList(id);
    }

    /**
     * 集货任务已出库整装箱统计信息
     *
     * @param id 集货任务id
     * @return 已入库整装箱统计信息
     */
    @PostMapping(value = "/ads/collectionTask/itemDoneCalcList")
    public List<ExBoxDeliveryOrderItemDto> collectionTaskItemDoneCalcList(@RequestParam @NotNull Long id) throws BusinessException {
        return collectionTaskService.collectionTaskItemDoneCalcList(id);
    }

    /**
     * 集货任务指定整裝箱中序列箱出库明细
     *
     * @param id                  集货任务id
     * @param boxDeliveryOrderId     整装箱出库订单id
     * @param boxDeliveryOrderItemId 整装箱出库订单详情id
     * @return 序列箱明细
     */
    @PostMapping(value = "/ads/collectionTask/itemSnUndoList")
    public List<ExBoxSnDto> collectionTaskItemSnUndoList(@RequestParam @NotNull Long id, @RequestParam @NotNull Long boxDeliveryOrderId, @RequestParam @NotNull Long boxDeliveryOrderItemId) throws BusinessException {
        return collectionTaskService.collectionTaskItemBoxSnUndoList(id, boxDeliveryOrderId, boxDeliveryOrderItemId);
    }

    /**
     * 序列箱出库
     *
     * @param id     出库任务id
     * @param type   出库方式
     * @param first  第一个参数
     * @param second 第二个参数 SupplierBoxCode方式必填
     */
    @PostMapping(value = "/ads/collectionTask/snOutbound")
    @ParameterScriptAssert(script = "type != 'SUPPLIER_BOX_CODE' || type == 'SUPPLIER_BOX_CODE' && second != null && second != ''", lang = "javascript", message = "{NotBlank.collectionTaskSnOutbound.second}")
    public void collectionTaskSnOutbound(@RequestParam @NotNull Long id, @RequestParam @NotNull StocktakeType type, @RequestParam @NotBlank String first, @RequestParam(required = false) String second) throws BusinessException {
        collectionTaskService.collectionTaskBoxSnOutbound(id, type, first, second);
    }
}
