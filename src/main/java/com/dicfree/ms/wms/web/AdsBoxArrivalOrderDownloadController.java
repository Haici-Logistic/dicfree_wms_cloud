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
import cn.jzyunqi.common.utils.CurrentUserUtils;
import cn.jzyunqi.ms.AdsRestBaseController;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import com.dicfree.ms.wms.common.dto.ex.ExStocktakeRecordDto;
import com.dicfree.ms.wms.common.dto.ex.excel.BoxSnArrivalExcelExport;
import com.dicfree.ms.wms.common.dto.ex.excel.BoxSnExcelExport;
import com.dicfree.ms.wms.common.dto.ex.excel.StocktakeRecordExcelExport;
import com.dicfree.ms.wms.service.BoxArrivalOrderService;
import com.dicfree.ms.wms.service.StocktakeRecordService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Portal - 超级管理员  - 整装箱入库订单信息下载接口
 *
 * @author wiiyaya
 * @date 2023/07/17
 */
@Controller
@Validated
public class AdsBoxArrivalOrderDownloadController extends AdsRestBaseController {

    @Resource
    private BoxArrivalOrderService boxArrivalOrderService;

    @Resource
    private StocktakeRecordService stocktakeRecordService;

    /**
     * 导出入库序列箱详情
     *
     * @param id 整装箱入库订单id
     * @param boxArrivalOrderItemId 整装箱入库订单详情id
     * @return 序列箱入库详情
     * @download
     */
    @GetMapping(value = "/ads/boxArrivalOrder/itemSnDownload")
    public ModelAndView boxArrivalOrderItemSnDownload(@RequestParam @NotNull Long id, @RequestParam @NotNull Long boxArrivalOrderItemId) throws BusinessException {
        List<ExBoxSnDto> boxSnDtoList = boxArrivalOrderService.boxArrivalOrderItemSnList(id, boxArrivalOrderItemId);
        return new ModelAndView(BoxSnExcelExport.VIEW_NAME, BoxSnExcelExport.MODEL_NAME, new BoxSnArrivalExcelExport(boxSnDtoList));
    }

    /**
     * 导出入库日志
     *
     * @return 整装箱入库日志下载excel文件
     * @download
     */
    @GetMapping(value = "/ads/boxArrivalOrder/snInboundLog")
    public ModelAndView boxSkuInboundLog() throws BusinessException {
        List<ExStocktakeRecordDto> stocktakeRecordDtoList = stocktakeRecordService.boxSnInboundLog();
        return new ModelAndView(BoxSnExcelExport.VIEW_NAME, BoxSnExcelExport.MODEL_NAME, new StocktakeRecordExcelExport("PrintLog_", stocktakeRecordDtoList));
    }

    /**
     * 导出整装箱入库订单未到货明细
     *
     * @param id 整装箱入库订单id
     * @return 整装箱入库订单未到货明细
     * @download
     */
    @GetMapping(value = "/ads/boxArrivalOrder/snDownload")
    public ModelAndView boxArrivalOrderBoxSnDownload(@RequestParam @NotNull Long id) throws BusinessException {
        List<ExBoxSnDto> boxSnDtoList = boxArrivalOrderService.boxArrivalOrderBoxSnList(id);
        return new ModelAndView(BoxSnExcelExport.VIEW_NAME, BoxSnExcelExport.MODEL_NAME, new BoxSnArrivalExcelExport(boxSnDtoList));
    }
}
