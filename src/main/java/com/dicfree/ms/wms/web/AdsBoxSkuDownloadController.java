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
import cn.jzyunqi.ms.AdsRestBaseController;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import com.dicfree.ms.wms.common.dto.ex.excel.BoxSnExcelExport;
import com.dicfree.ms.wms.service.BoxSkuService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Portal - 个人用户 - 整装箱信息下载接口
 *
 * @author wiiyaya
 * @date 2023/07/17
 */
@Controller
@Validated
public class AdsBoxSkuDownloadController extends AdsRestBaseController {

    @Resource
    private BoxSkuService boxSkuService;

    /**
     * 整装箱导出
     *
     * @param batchNo 导入批次号
     * @return 整装箱批次下载excel文件
     * @download
     */
    @GetMapping(value = "/ads/boxSku/download")
    public ModelAndView boxSkuDownload(@RequestParam @NotNull String batchNo) throws BusinessException {
        List<ExBoxSnDto> boxSnDtoList = boxSkuService.boxSkuDownload(batchNo);
        return new ModelAndView(BoxSnExcelExport.VIEW_NAME, BoxSnExcelExport.MODEL_NAME, new BoxSnExcelExport(boxSnDtoList));
    }

    /**
     * 整装箱导出全部
     *
     * @return 整装箱全量下载excel文件
     * @download
     */
    @GetMapping(value = "/ads/boxSku/downloadAll")
    public ModelAndView boxSkuDownloadAll() throws BusinessException {
        List<ExBoxSnDto> boxSnDtoList = boxSkuService.boxSkuDownloadAll();
        return new ModelAndView(BoxSnExcelExport.VIEW_NAME, BoxSnExcelExport.MODEL_NAME, new BoxSnExcelExport(boxSnDtoList));
    }
}
