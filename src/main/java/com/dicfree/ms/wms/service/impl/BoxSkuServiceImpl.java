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
package com.dicfree.ms.wms.service.impl;

import cn.jzyunqi.autoconfigure.feature.properties.OssProperties;
import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.utils.POIUtilPlus;
import cn.jzyunqi.common.utils.RandomUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSkuDto;
import com.dicfree.ms.wms.common.dto.ex.excel.BoxSkuEditExcelImport;
import com.dicfree.ms.wms.common.dto.ex.excel.BoxSkuAddExcelImport;
import com.dicfree.ms.wms.common.dto.ex.query.ExBoxSkuQueryDto;
import com.dicfree.ms.wms.repository.jpa.dao.BoxSnDao;
import com.dicfree.ms.wms.repository.jpa.dao.BoxSkuDao;
import com.dicfree.ms.wms.repository.jpa.dao.querydsl.BoxSkuQry;
import com.dicfree.ms.wms.repository.jpa.entity.BoxSn;
import com.dicfree.ms.wms.repository.jpa.entity.BoxSku;
import com.dicfree.ms.wms.service.BoxSkuService;
import com.dicfree.ms.wms.service.BoxSnService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
@Service("boxSkuService")
public class BoxSkuServiceImpl implements BoxSkuService {

    @Resource
    private BoxSkuDao boxSkuDao;

    @Resource
    private BoxSnDao boxSnDao;

    @Resource
    private BoxSnService boxSnService;

    @Resource
    private OssProperties ossProperties;

    @Override
    public PageDto<ExBoxSkuDto> boxSkuPage(ExBoxSkuQueryDto boxSkuQueryDto, Pageable pageable) {
        Page<BoxSku> boxSkuPage = boxSkuDao.findAll(BoxSkuQry.searchBoxSku(boxSkuQueryDto), BoxSkuQry.searchBoxSkuOrder(pageable));
        List<ExBoxSkuDto> boxSkuDtoList = boxSkuPage.stream().map(this::toExBoxSkuFullDto).collect(Collectors.toList());
        return new PageDto<>(boxSkuDtoList, boxSkuPage.getTotalElements());
    }

    @Override
    public PageDto<ExBoxSnDto> boxSnPage(String boxSkuCode, Pageable pageable) {
        Page<BoxSn> boxSnPage = boxSnDao.findAllByBoxSkuCode(boxSkuCode, pageable);
        List<ExBoxSnDto> boxSnDtoList = boxSnPage.stream().map(boxSn -> {
            ExBoxSnDto boxSnDto = new ExBoxSnDto();
            boxSnDto.setId(boxSn.getId());
            boxSnDto.setCode(boxSn.getCode());
            boxSnDto.setBoxSkuCode(boxSn.getBoxSkuCode());
            boxSnDto.setSerialNo(boxSn.getSerialNo());
            boxSnDto.setPcs(boxSn.getPcs());
            boxSnDto.setSupplierBoxSnCode(boxSn.getSupplierBoxSnCode());
            boxSnDto.setBatchNo(boxSn.getBatchNo());
            return boxSnDto;
        }).collect(Collectors.toList());

        return new PageDto<>(boxSnDtoList, boxSnPage.getTotalElements());
    }

    @Override
    public List<BoxSkuAddExcelImport> boxSkuAddBatchParse(String fileKey) throws BusinessException {
        String url = ossProperties.getAccessDomain() + "/" + fileKey;
        List<BoxSkuAddExcelImport> excelImportList;
        try {
            excelImportList = POIUtilPlus.Excel.readExcel(url, 0, BoxSkuAddExcelImport.class);
        } catch (Exception e) {
            throw new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_FILE_PARSER_ERROR);
        }

        //过滤掉空行
        excelImportList = excelImportList.stream().filter(excelImport -> StringUtilPlus.isNotBlank(excelImport.getSupplierBoxCode())).toList();

        Set<String> boxSkuCodeSet = new HashSet<>();
        for (BoxSkuAddExcelImport excelImport : excelImportList) {
            String boxSkuCode = getBoxSkuCode(excelImport.getSupplierBoxCode(), excelImport.getSupplier());
            //这里校验数据是否合法
            //if (excelImport.getPcs() == null || excelImport.getPcs() > 999 || excelImport.getPcs() < 1) {
            //    throw new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_FILE_PCS_INVALID, excelImport.getRowNum(), boxSkuCode);
            //}
            //这里校验文件里的数据是否重复
            if (!boxSkuCodeSet.add(boxSkuCode)) {
                throw new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_FILE_DUPLICATE_RECORD, excelImport.getRowNum(), boxSkuCode);
            }
            //这里校验是否和数据库数据重复
            if (boxSkuDao.isCodeExists(boxSkuCode)) {
                throw new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_FILE_EXISTS_RECORD, excelImport.getRowNum(), boxSkuCode);
            }
        }
        return excelImportList;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public List<ExBoxSkuDto> boxSkuAddBatch(List<BoxSkuAddExcelImport> boxSkuInboundDtoList) throws BusinessException {
        String batchNo = RandomUtilPlus.String.randomAlphanumeric(12).toUpperCase();
        List<ExBoxSkuDto> boxSkuDtoList = new ArrayList<>();
        for (BoxSkuAddExcelImport excelImport : boxSkuInboundDtoList) {
            ExBoxSkuDto boxSkuDto = addOneBoxSku(
                    excelImport.getSupplierBoxCode(),
                    excelImport.getSupplier(),
                    excelImport.getLocation(),
                    excelImport.getDeliveryTo(),
                    excelImport.getSortingTo(),
                    excelImport.getPcs(),
                    batchNo);
            boxSkuDtoList.add(boxSkuDto);
        }
        return boxSkuDtoList;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public ExBoxSkuDto boxSkuAdd(ExBoxSkuDto boxSkuDto) throws BusinessException {
        String boxSkuCode = getBoxSkuCode(boxSkuDto.getSupplierBoxCode(), boxSkuDto.getSupplier());

        //这里校验是否和数据库数据重复
        if (boxSkuDao.isCodeExists(boxSkuCode)) {
            throw new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_EXISTS, boxSkuCode);
        }

        return addOneBoxSku(
                boxSkuDto.getSupplierBoxCode(),
                boxSkuDto.getSupplier(),
                boxSkuDto.getLocation(),
                boxSkuDto.getDeliveryTo(),
                boxSkuDto.getSortingTo(),
                0,
                null);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void boxSkuDeliveryEdit(String boxSkuCode, String sortingTo, String deliveryTo) throws BusinessException {
        BoxSku boxSku = boxSkuDao.findByCode(boxSkuCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_NOT_FOUND, boxSkuCode));
        boxSku.setSortingTo(sortingTo);
        boxSku.setDeliveryTo(deliveryTo);
        boxSkuDao.save(boxSku);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void boxSkuLocationEdit(String boxSkuCode, String location) throws BusinessException {
        BoxSku boxSku = boxSkuDao.findByCode(boxSkuCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_NOT_FOUND, boxSkuCode));
        boxSku.setLocation(location);
        boxSkuDao.save(boxSku);
    }

    @Override
    public ExBoxSkuDto boxSkuInfo(String boxSkuCode) throws BusinessException {
        BoxSku boxSku = boxSkuDao.findByCode(boxSkuCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_NOT_FOUND, boxSkuCode));
        return toExBoxSkuFullDto(boxSku);
    }

    @Override
    public ExBoxSkuDto boxSkuInfoWithNull(String boxSkuCode) {
        BoxSku boxSku = boxSkuDao.findByCode(boxSkuCode).orElse(new BoxSku());
        return toExBoxSkuFullDto(boxSku);
    }

    @Override
    public List<BoxSkuEditExcelImport> boxSkuEditBatchParse(String fileKey) throws BusinessException {
        String url = ossProperties.getAccessDomain() + "/" + fileKey;
        List<BoxSkuEditExcelImport> testExcelImportList;
        try {
            testExcelImportList = POIUtilPlus.Excel.readExcel(url, 0, BoxSkuEditExcelImport.class);
        } catch (Exception e) {
            throw new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_FILE_PARSER_ERROR);
        }

        Set<String> boxSkuCodeSet = new HashSet<>();
        for (BoxSkuEditExcelImport boxSkuEditExcelImport : testExcelImportList) {
            String boxSkuCode = boxSkuEditExcelImport.getBoxSkuCode();
            //这里校验文件里的数据是否重复
            if (!boxSkuCodeSet.add(boxSkuCode)) {
                throw new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_FILE_DUPLICATE_RECORD);
            }
            //这里校验是否和数据库数据是否存在
            if (!boxSkuDao.isCodeExists(boxSkuCode)) {
                throw new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_NOT_FOUND, boxSkuCode);
            }
        }
        return testExcelImportList;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void boxSkuEditBatch(List<BoxSkuEditExcelImport> boxSkuEditDtoList) throws BusinessException {
        for (BoxSkuEditExcelImport boxSkuEditExcelImport : boxSkuEditDtoList) {
            BoxSku boxSku = boxSkuDao.findByCode(boxSkuEditExcelImport.getBoxSkuCode()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_NOT_FOUND, boxSkuEditExcelImport.getBoxSkuCode()));
            boxSku.setLocation(boxSkuEditExcelImport.getLocation());
            boxSku.setDeliveryTo(boxSkuEditExcelImport.getDeliveryTo());
            boxSku.setSortingTo(boxSkuEditExcelImport.getSortingTo());
            boxSkuDao.save(boxSku);
        }
    }

    @Override
    public List<ExBoxSnDto> boxSkuDownload(String batchNo) {
        List<Object> boxSnList = boxSnDao.findByBatchNo(batchNo);
        return toExBoxSnDownloadDtoList(boxSnList);
    }

    @Override
    public List<ExBoxSnDto> boxSkuDownloadAll() {
        List<Object> boxSnList = boxSnDao.findLimitAll();
        return toExBoxSnDownloadDtoList(boxSnList);
    }

    @Override
    public List<ExBoxSkuDto> boxSkuSupplierList(String supplierBoxCode) {
        List<BoxSku> boxSkuList = boxSkuDao.findBySupplierBoxCode(supplierBoxCode);
        return boxSkuList.stream().map(boxSku -> {
            ExBoxSkuDto boxSkuDto = new ExBoxSkuDto();
            boxSkuDto.setId(boxSku.getId());
            boxSkuDto.setSupplier(boxSku.getSupplier());
            return boxSkuDto;
        }).toList();
    }

    @Override
    public String getBoxSkuCode(String supplierBoxCode, String supplier) {
        return StringUtilPlus.join(supplierBoxCode, StringUtilPlus.HYPHEN, supplier);
    }

    @Override
    public String getSupplierBoxCode(String boxSkuCode) {
        return StringUtilPlus.split(boxSkuCode, StringUtilPlus.HYPHEN)[0];
    }

    /**
     * DB数据转DTO数据
     *
     * @param boxSku DB数据
     * @return DTO数据
     */
    private ExBoxSkuDto toExBoxSkuFullDto(BoxSku boxSku) {
        ExBoxSkuDto boxSkuDto = new ExBoxSkuDto();
        boxSkuDto.setId(boxSku.getId());
        boxSkuDto.setCode(boxSku.getCode());
        boxSkuDto.setSupplierBoxCode(boxSku.getSupplierBoxCode());
        boxSkuDto.setSupplier(boxSku.getSupplier());
        boxSkuDto.setPickUpCode(boxSku.getPickUpCode());
        boxSkuDto.setLocation(boxSku.getLocation());
        boxSkuDto.setSortingTo(boxSku.getSortingTo());
        boxSkuDto.setDeliveryTo(boxSku.getDeliveryTo());
        return boxSkuDto;
    }

    /**
     * 组装返回数据
     *
     * @param boxSnList 序列箱信息
     * @return 返回序列箱列表
     */
    private List<ExBoxSnDto> toExBoxSnDownloadDtoList(List<Object> boxSnList) {
        return boxSnList.stream().map(object -> {
            BoxSn boxSn = (BoxSn) ((Object[]) object)[0];
            ExBoxSnDto boxSnDto = new ExBoxSnDto();
            boxSnDto.setBoxSkuCode(boxSn.getBoxSkuCode());
            boxSnDto.setCode(boxSn.getCode());
            boxSnDto.setSerialNo(boxSn.getSerialNo());
            boxSnDto.setPcs(boxSn.getPcs());
            boxSnDto.setSupplierBoxSnCode(boxSn.getSupplierBoxSnCode());
            boxSnDto.setCreateTime(boxSn.getCreateTime());

            BoxSku boxSku = (BoxSku) ((Object[]) object)[1];
            ExBoxSkuDto boxSkuDto = new ExBoxSkuDto();
            boxSkuDto.setSupplierBoxCode(boxSku.getSupplierBoxCode());
            boxSkuDto.setLocation(boxSku.getLocation());
            boxSkuDto.setPickUpCode(boxSku.getPickUpCode());
            boxSkuDto.setSupplier(boxSku.getSupplier());
            boxSnDto.setBoxSku(boxSkuDto);
            return boxSnDto;
        }).toList();
    }

    /**
     * 创建一个整裝箱
     *
     * @param supplierBoxCode 客户箱号
     * @param supplier        客户
     * @param location        位置
     * @param deliveryTo      目的地
     * @param sortingTo       中转
     * @param pcs
     * @param batchNo
     * @return 整装箱信息
     */
    private ExBoxSkuDto addOneBoxSku(String supplierBoxCode, String supplier, String location, String deliveryTo, String sortingTo, Integer pcs, String batchNo) throws BusinessException {
        BoxSku boxSku = new BoxSku();
        boxSku.setCode(getBoxSkuCode(supplierBoxCode, supplier));
        boxSku.setSupplierBoxCode(supplierBoxCode);
        boxSku.setSupplier(supplier.toUpperCase());
        boxSku.setPickUpCode(RandomUtilPlus.String.randomAlphanumeric(4).toUpperCase());
        boxSku.setLocation(location);
        boxSku.setDeliveryTo(deliveryTo);
        boxSku.setSortingTo(sortingTo);
        boxSkuDao.save(boxSku);

        ExBoxSkuDto boxSkuDto = toExBoxSkuFullDto(boxSku);
        if(pcs != null && pcs > 0){
            //List<ExBoxSnDto> boxSnDtoList = boxSnService.boxSnAdd(null, boxSku.getBoxSkuCode(), pcs, batchNo);
            //boxSkuDto.setBoxSnList(boxSnDtoList);
        }
        return boxSkuDto;
    }
}
