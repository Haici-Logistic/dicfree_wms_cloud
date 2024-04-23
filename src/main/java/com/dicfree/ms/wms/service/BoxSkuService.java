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
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSkuDto;
import com.dicfree.ms.wms.common.dto.ex.excel.BoxSkuEditExcelImport;
import com.dicfree.ms.wms.common.dto.ex.excel.BoxSkuAddExcelImport;
import com.dicfree.ms.wms.common.dto.ex.query.ExBoxSkuQueryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
public interface BoxSkuService {

    /**
     * 整装箱表分页
     *
     * @param boxSkuQueryDto 查询条件
     * @param pageable       分页条件
     * @return 结果
     */
    PageDto<ExBoxSkuDto> boxSkuPage(ExBoxSkuQueryDto boxSkuQueryDto, Pageable pageable);

    /**
     * 序列箱表分页
     *
     * @param boxSkuCode 箱号
     * @param pageable   分页条件
     * @return 序列箱列表
     */
    PageDto<ExBoxSnDto> boxSnPage(String boxSkuCode, Pageable pageable);

    /**
     * 整装箱批量导入文件解析
     *
     * @param fileKey 文件名
     * @return 解析数据列表
     */
    List<BoxSkuAddExcelImport> boxSkuAddBatchParse(String fileKey) throws BusinessException;

    /**
     * 整装箱批量导入
     *
     * @param boxSkuInboundDtoList 导入的文件名
     * @return 全部新增数据
     */
    List<ExBoxSkuDto> boxSkuAddBatch(List<BoxSkuAddExcelImport> boxSkuInboundDtoList) throws BusinessException;

    /**
     * 整装箱新增
     *
     * @param boxSkuDto 整装箱
     */
    ExBoxSkuDto boxSkuAdd(ExBoxSkuDto boxSkuDto) throws BusinessException;

    /**
     * 整装箱更新发货地址
     *
     * @param boxSkuCode 箱号
     * @param sortingTo  中转
     * @param deliveryTo 目的地
     */
    void boxSkuDeliveryEdit(String boxSkuCode, String sortingTo, String deliveryTo) throws BusinessException;

    /**
     * 整装箱更新库位
     *
     * @param boxSkuCode 箱号
     * @param location   库位
     */
    void boxSkuLocationEdit(String boxSkuCode, String location) throws BusinessException;

    /**
     * 整装箱信息
     *
     * @param boxSkuCode 箱号
     * @return 整装箱信息
     */
    ExBoxSkuDto boxSkuInfo(String boxSkuCode) throws BusinessException;

    /**
     * 整装箱信息
     *
     * @param boxSkuCode 箱号
     * @return 整装箱信息
     */
    ExBoxSkuDto boxSkuInfoWithNull(String boxSkuCode);

    /**
     * 整装箱批量修改文件解析
     *
     * @param fileKey 文件名
     * @return 解析数据列表
     */
    List<BoxSkuEditExcelImport> boxSkuEditBatchParse(String fileKey) throws BusinessException;

    /**
     * 整装箱批量修改
     *
     * @param boxSkuEditDtoList 导入的文件名
     */
    void boxSkuEditBatch(List<BoxSkuEditExcelImport> boxSkuEditDtoList) throws BusinessException;

    /**
     * 整装箱导入信息下载
     *
     * @param batchNo 批次号
     * @return 列表数据
     */
    List<ExBoxSnDto> boxSkuDownload(String batchNo);

    /**
     * 整装箱导入信息全量下载
     *
     * @return 列表数据
     */
    List<ExBoxSnDto> boxSkuDownloadAll();

    /**
     * 根据客户箱号获取客户列表
     *
     * @param supplierBoxCode 客户箱号
     * @return 客户列表
     */
    List<ExBoxSkuDto> boxSkuSupplierList(String supplierBoxCode);

    /**
     * 整装箱号
     *
     * @param supplierBoxCode 客户箱号
     * @param supplier        客户
     * @return 箱号
     */
    String getBoxSkuCode(String supplierBoxCode, String supplier);

    /**
     * 根据箱号获取客户箱号
     *
     * @param boxSkuCode 箱号
     * @return 客户箱号
     */
    String getSupplierBoxCode(String boxSkuCode);
}
