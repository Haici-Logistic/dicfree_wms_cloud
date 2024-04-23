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
import cn.jzyunqi.ms.AdsRestBaseController;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSkuDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import com.dicfree.ms.wms.common.dto.ex.excel.BoxSkuEditExcelImport;
import com.dicfree.ms.wms.common.dto.ex.excel.BoxSkuAddExcelImport;
import com.dicfree.ms.wms.common.dto.ex.query.ExBoxSkuQueryDto;
import com.dicfree.ms.wms.service.BoxSkuService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Portal - 个人用户 - 整装箱接口
 *
 * @author wiiyaya
 * @date 2023/07/17
 */
@RestController
@Validated
public class AdsBoxSkuController extends AdsRestBaseController {

    @Resource
    private BoxSkuService boxSkuService;

    /**
     * 整装箱表分页
     *
     * @param boxSkuQueryDto 查询条件
     * @param pageable            分页参数|cn.jzyunqi.common.model.PageRequestDto
     * @return 整装箱表信息
     */
    @PostMapping(value = "/ads/boxSku/page")
    public PageDto<ExBoxSkuDto> boxSkuPage(@ModelAttribute ExBoxSkuQueryDto boxSkuQueryDto, Pageable pageable) {
        return boxSkuService.boxSkuPage(boxSkuQueryDto, pageable);
    }

    /**
     * 整装箱表分页
     *
     * @param boxSkuCode  查询条件
     * @param pageable 分页参数|cn.jzyunqi.common.model.PageRequestDto
     * @return 整装箱表信息
     */
    @PostMapping(value = "/ads/boxSn/page")
    public PageDto<ExBoxSnDto> boxSnPage(@RequestParam @NotBlank String boxSkuCode, Pageable pageable) {
        return boxSkuService.boxSnPage(boxSkuCode, pageable);
    }

    /**
     * 整装箱批量导入
     *
     * @param fileName 导入文件名
     * @return 导入批次号
     */
    @PostMapping(value = "/ads/boxSku/addBatch")
    public String boxSkuAddBatch(@RequestParam @NotBlank String fileName) throws BusinessException {
        List<BoxSkuAddExcelImport> boxSkuInboundDtoList = boxSkuService.boxSkuAddBatchParse(fileName);
        List<ExBoxSkuDto> boxSkuDtoList = boxSkuService.boxSkuAddBatch(boxSkuInboundDtoList);
        return boxSkuDtoList.get(0).getBoxSnList().get(0).getBatchNo();
    }

    /**
     * 整装箱批量修改
     *
     * @param fileName 导入文件名
     */
    @PostMapping(value = "/ads/boxSku/editBatch")
    public void boxSkuEditBatch(@RequestParam @NotBlank String fileName) throws BusinessException {
        List<BoxSkuEditExcelImport> boxSkuEditDtoList = boxSkuService.boxSkuEditBatchParse(fileName);
        boxSkuService.boxSkuEditBatch(boxSkuEditDtoList);
    }

    /**
     * 整装箱客户列表
     *
     * @param supplierBoxCode 客户箱号
     * @return 客户列表
     */
    @PostMapping(value = "/ads/boxSku/supplierList")
    public List<ExBoxSkuDto> boxSkuSupplierList(@RequestParam @NotBlank String supplierBoxCode) throws BusinessException {
        return boxSkuService.boxSkuSupplierList(supplierBoxCode);
    }
}
