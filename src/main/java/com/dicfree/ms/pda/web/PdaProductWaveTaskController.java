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
package com.dicfree.ms.pda.web;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.spring.security.LoginUserDto;
import cn.jzyunqi.common.utils.CurrentUserUtils;
import com.dicfree.ms.PdaRestBaseController;
import com.dicfree.ms.pda.common.constant.PdaConstant;
import com.dicfree.ms.pda.service.PdaProductWaveTaskService;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductWaveTaskDto;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * PDA - 波次任务接口
 *
 * @author wiiyaya
 * @date 2023/11/06
 */
@RestController
@Validated
public class PdaProductWaveTaskController extends PdaRestBaseController {

    @Resource
    private PdaProductWaveTaskService pdaProductWaveTaskService;

    /**
     * 未完成的集货任务统计
     *
     * @return 未完成的集货任务统计
     */
    @PostMapping(value = "/pda/productWaveTask/offShelfUndoCount")
    public Integer productWaveTaskOffShelfUndoCount() throws BusinessException {
        String devicePdaCode = getDevicePdaCode();
        return pdaProductWaveTaskService.productWaveTaskOffShelfUndoCount(devicePdaCode);
    }

    /**
     * 未完成的集货任务统计
     *
     * @return 未完成的集货任务统计
     */
    @PostMapping(value = "/pda/productWaveTask/offShelfUndoList")
    public List<ExProductWaveTaskDto> productWaveTaskOffShelfUndoList() throws BusinessException {
        String devicePdaCode = getDevicePdaCode();
        return pdaProductWaveTaskService.productWaveTaskOffShelfUndoList(devicePdaCode);
    }

    /**
     * 最早未完成的波次任务
     *
     * @return 最早未完成的波次任务
     */
    @PostMapping(value = "/pda/productWaveTask/offShelfUndoDetail")
    public ExProductWaveTaskDto productWaveTaskOffShelfUndoDetail(@RequestParam @NotNull Long id) throws BusinessException {
        String devicePdaCode = getDevicePdaCode();
        return pdaProductWaveTaskService.productWaveTaskOffShelfUndoDetail(devicePdaCode, id);
    }

    /**
     * 序列商品下架
     *
     * @param id          波次任务ID
     * @param shelfNo     货架号
     * @param productCode 商品编码
     * @return 结果
     */
    @PostMapping(value = "/pda/productWaveTask/snOffShelf")
    public String productWaveTaskSnOffShelf(@RequestParam @NotNull Long id, @RequestParam @NotBlank String shelfNo, @RequestParam @NotBlank String productCode) throws BusinessException {
        String devicePdaCode = getDevicePdaCode();
        pdaProductWaveTaskService.productWaveTaskSnOffShelf(devicePdaCode, id, shelfNo, productCode);
        return "success";
    }

    /**
     * 已完成下架但未集货的波次列表
     *
     * @return 未集货的波次列表
     */
    @PostMapping(value = "/pda/productWaveTask/collectionUndoList")
    public List<ExProductWaveTaskDto> productWaveTaskCollectionUndoList() throws BusinessException {
        return pdaProductWaveTaskService.productWaveTaskCollectionUndoList();
    }

    /**
     * 未完成下架且未集货的波次列表
     *
     * @return 未集货的波次列表
     */
    @PostMapping(value = "/pda/productWaveTask/collectionOffShelfUndoList")
    public List<ExProductWaveTaskDto> productWaveTaskCollectionOffShelfUndoList() throws BusinessException {
        return pdaProductWaveTaskService.productWaveTaskCollectionOffShelfUndoList();
    }

    /**
     * 未完成下架且未集货的波次列表
     *
     * @return 未集货的波次列表
     */
    @PostMapping(value = "/pda/productWaveTask/collectionOffShelfUndoCount")
    public Integer productWaveTaskCollectionOffShelfUndoCount() throws BusinessException {
        return pdaProductWaveTaskService.productWaveTaskCollectionOffShelfUndoCount();
    }

    /**
     * 波次集货完成
     *
     * @param collectionAreaCode 波次任务号
     */
    @PostMapping(value = "/pda/productWaveTask/collectionDone")
    public void productWaveTaskCollectionDone(@RequestParam @NotBlank String collectionAreaCode) throws BusinessException {
        pdaProductWaveTaskService.productWaveTaskCollectionDone(collectionAreaCode);
    }

    /**
     * 按顺序获取波次订单列表
     *
     * @param uniqueNo 波次任务号
     * @return 波次订单列表
     */
    @PostMapping(value = "/pda/productWaveTask/basketInit")
    public List<ExProductDeliveryOrderDto> productWaveTaskBasketInit(@RequestParam @NotBlank String uniqueNo) throws BusinessException {
        return pdaProductWaveTaskService.productWaveTaskBasketInit(uniqueNo);
    }

    /**
     * 出库订单绑定篮子
     *
     * @param waybill  运单号
     * @param basketNo 篮子号
     */
    @PostMapping(value = "/pda/productWaveTask/basketBind")
    public void productWaveTaskBasketBind(@RequestParam String waybill, @RequestParam String basketNo) throws BusinessException {
        pdaProductWaveTaskService.productWaveTaskBasketBind(waybill, basketNo);
    }

    /**
     * 包裹称重
     *
     * @param waybill 运单号
     * @param weight  包裹重量
     */
    @PostMapping(value = "/pda/productWaveTask/weighing")
    public void productWaveTaskWeighing(@RequestParam String waybill, @RequestParam Float weight) throws BusinessException {
        pdaProductWaveTaskService.productWaveTaskWeighing(waybill, weight);
    }

    @SuppressWarnings("unchecked")
    private static String getDevicePdaCode() throws BusinessException {
        LoginUserDto currentUser = CurrentUserUtils.currentUser();
        return ((Map<String, Object>) currentUser.getExtInfo()).get(PdaConstant.LOGIN_ATTR_DEVICE_PDA_CODE).toString();
    }
}
