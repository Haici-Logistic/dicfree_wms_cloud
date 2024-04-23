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
import cn.jzyunqi.common.utils.CurrentUserUtils;
import cn.jzyunqi.ms.AdsRestBaseController;
import com.dicfree.ms.wms.common.dto.ex.ExBoxArrivalOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxArrivalOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import com.dicfree.ms.wms.common.dto.ex.query.ExBoxArrivalOrderQueryDto;
import com.dicfree.ms.wms.common.enums.StocktakeType;
import com.dicfree.ms.wms.service.BoxArrivalOrderService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Portal - 超级管理员 - 整装箱入库订单接口
 *
 * @author wiiyaya
 * @date 2023/09/02
 */
@RestController
@Validated
public class AdsBoxArrivalOrderController extends AdsRestBaseController {

    @Resource
    private BoxArrivalOrderService boxArrivalOrderService;

    /**
     * 未完成排库的订单列表
     *
     * @return 未完成排库的订单列表
     */
    @PostMapping(value = "/ads/boxArrivalOrder/locationUndoList")
    public List<ExBoxArrivalOrderDto> boxArrivalOrderLocationUndoList() throws BusinessException {
        return boxArrivalOrderService.boxArrivalOrderLocationUndoList();
    }

    /**
     * 排库锁定
     *
     * @param id            整装箱入库订单id
     * @param wholeLocation 整装箱库位
     * @param bulkLocation  散装箱库位
     */
    @PostMapping(value = "/ads/boxArrivalOrder/locationLock")
    public void boxArrivalOrderLocationLock(@RequestParam @NotNull Long id, @RequestParam(required = false, defaultValue = "") String wholeLocation, @RequestParam(required = false, defaultValue = "") String bulkLocation) throws BusinessException {
        boxArrivalOrderService.boxArrivalOrderLocationLock(id, wholeLocation, bulkLocation);
    }

    /**
     * 整装箱入库订单分页
     *
     * @param boxArrivalOrderQueryDto 查询条件
     * @param pageable                分页参数|cn.jzyunqi.common.model.PageRequestDto
     * @return 整装箱入库订单信息
     */
    @PostMapping(value = "/ads/boxArrivalOrder/page")
    public PageDto<ExBoxArrivalOrderDto> boxArrivalOrderPage(@ModelAttribute ExBoxArrivalOrderQueryDto boxArrivalOrderQueryDto, Pageable pageable) {
        return boxArrivalOrderService.boxArrivalOrderPage(boxArrivalOrderQueryDto, pageable);
    }

    /**
     * 整装箱入库订单明细列表
     *
     * @param id 整装箱入库订单id
     * @return 整装箱入库订单明细信息
     */
    @PostMapping(value = "/ads/boxArrivalOrder/itemList")
    public List<ExBoxArrivalOrderItemDto> boxArrivalOrderItemList(@RequestParam @NotNull Long id) {
        return boxArrivalOrderService.boxArrivalOrderItemList(id);
    }

    /**
     * 未完成整装箱入库订单列表
     *
     * @return 未完成整装箱入库订单信息
     */
    @PostMapping(value = "/ads/boxArrivalOrder/undoList")
    public List<ExBoxArrivalOrderDto> boxArrivalOrderUndoList() throws BusinessException {
        return boxArrivalOrderService.boxArrivalOrderUndoList();
    }

    /**
     * 整装箱入库订单未入库整装箱统计信息
     *
     * @param id 整装箱入库订单id
     * @return 未入库整装箱统计信息
     */
    @PostMapping(value = "/ads/boxArrivalOrder/itemUndoCalcList")
    public List<ExBoxArrivalOrderItemDto> boxArrivalOrderItemUndoCalcList(@RequestParam @NotNull Long id) throws BusinessException {
        return boxArrivalOrderService.boxArrivalOrderItemUndoCalcList(id);
    }

    /**
     * 整装箱入库订单已入库整装箱统计信息
     *
     * @param id 整装箱入库订单id
     * @return 已入库整装箱统计信息
     */
    @PostMapping(value = "/ads/boxArrivalOrder/itemDoneCalcList")
    public List<ExBoxArrivalOrderItemDto> boxArrivalOrderItemDoneCalcList(@RequestParam @NotNull Long id) throws BusinessException {
        return boxArrivalOrderService.boxArrivalOrderItemDoneCalcList(id);
    }

    /**
     * 整装箱入库订单指定整装箱中序列箱入库明细
     *
     * @param id                    整装箱入库订单id
     * @param boxArrivalOrderItemId 整装箱入库订单详情id
     * @return 序列箱明细
     */
    @PostMapping(value = "/ads/boxArrivalOrder/itemSnUndoList")
    public List<ExBoxSnDto> boxArrivalOrderItemSnUndoList(@RequestParam @NotNull Long id, @RequestParam @NotNull Long boxArrivalOrderItemId) throws BusinessException {
        return boxArrivalOrderService.boxArrivalOrderItemSnUndoList(id, boxArrivalOrderItemId);
    }

    /**
     * 整装箱入库订单指定整装箱中序列箱未入库打印
     *
     * @param boxSnId 序列箱id
     */
    @PostMapping(value = "/ads/boxArrivalOrder/boxSnUndoPrint")
    public void boxArrivalOrderBoxSnUndoPrint(@RequestParam @NotNull Long boxSnId) throws BusinessException {
        Long currentUserId = CurrentUserUtils.currentUserId();
        boxArrivalOrderService.boxArrivalOrderBoxSnUndoPrint(currentUserId, boxSnId);
    }

    /**
     * 整装箱入库订单指定整装箱中序列箱未入库打印
     *
     * @param id                    整装箱入库订单id
     * @param boxArrivalOrderItemId 整装箱入库订单详情id
     */
    @PostMapping(value = "/ads/boxArrivalOrder/itemSnUndoPrint")
    public void boxArrivalOrderItemSnUndoPrint(@RequestParam @NotNull Long id, @RequestParam @NotNull Long boxArrivalOrderItemId) throws BusinessException {
        Long currentUserId = CurrentUserUtils.currentUserId();
        boxArrivalOrderService.boxArrivalOrderItemSnUndoPrint(currentUserId, id, boxArrivalOrderItemId);
    }

    /**
     * 整装箱入库订单全部整装箱中序列箱未入库全量打印
     *
     * @param id 整装箱入库订单id
     */
    @PostMapping(value = "/ads/boxArrivalOrder/itemSnUndoPrintAll")
    public void boxArrivalOrderItemSnUndoPrintAll(@RequestParam @NotNull Long id) throws BusinessException {
        Long currentUserId = CurrentUserUtils.currentUserId();
        boxArrivalOrderService.boxArrivalOrderItemSnUndoPrintAll(currentUserId, id);
    }

    /**
     * 序列箱入库
     *
     * @param id     整装箱入库订单id
     * @param type   入库方式
     * @param first  第一个参数
     * @param second 第二个参数 SupplierBoxCode方式必填
     * @return 整装箱入库订单详情id
     */
    @PostMapping(value = "/ads/boxArrivalOrder/snInbound")
    @ParameterScriptAssert(script = "type != 'SUPPLIER_BOX_CODE' || type == 'SUPPLIER_BOX_CODE' && second != null && second != ''", lang = "javascript", message = "{NotBlank.boxArrivalOrderSnInbound.second}")
    public Long boxArrivalOrderSnInbound(@RequestParam @NotNull Long id, @RequestParam @NotNull StocktakeType type, @RequestParam @NotBlank String first, @RequestParam(required = false) String second) throws BusinessException {
        Long currentUserId = CurrentUserUtils.currentUserId();
        return boxArrivalOrderService.boxArrivalOrderSnInbound(currentUserId, id, type, first, second);
    }
}
