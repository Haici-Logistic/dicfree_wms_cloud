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

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.support.NumberGenHelper;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSkuDto;
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;
import com.dicfree.ms.wms.common.enums.StockStatus;
import com.dicfree.ms.wms.repository.jpa.dao.BoxSkuDao;
import com.dicfree.ms.wms.repository.jpa.dao.BoxSnDao;
import com.dicfree.ms.wms.repository.jpa.entity.BoxSku;
import com.dicfree.ms.wms.repository.jpa.entity.BoxSn;
import com.dicfree.ms.wms.service.BoxSnService;
import jakarta.annotation.Resource;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
@Service("boxSnService")
public class BoxSnServiceImpl implements BoxSnService {

    @Resource
    private BoxSnDao boxSnDao;

    @Resource
    private BoxSkuDao boxSkuDao;

    @Resource
    private DateTimeProvider dateTimeProvider;

    @Resource
    private NumberGenHelper numberGenHelper;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public List<ExBoxSnDto> boxSnAdd(Long boxArrivalOrderId, String boxSkuCode, Integer totalCount, String batchNo) throws BusinessException {
        BoxSku boxSku = boxSkuDao.findByCode(boxSkuCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_NOT_FOUND, boxSkuCode));

        List<ExBoxSnDto> boxSnDtoList = new ArrayList<>();
        for (int i = 1; i <= totalCount; i++) {
            String serialNo = StringUtilPlus.leftPad(i, 3, '0');
            String boxSnCode = numberGenHelper.incubateHexNumber(hexId -> StringUtilPlus.leftPad(hexId, 14, '0') + serialNo);

            BoxSn boxSn = new BoxSn();
            boxSn.setSerialNo(serialNo);
            boxSn.setCode(boxSnCode);
            boxSn.setBoxArrivalOrderItemId(boxArrivalOrderId);
            boxSn.setInboundTime(null);
            boxSn.setBoxDeliveryOrderItemId(null);
            boxSn.setOutboundTime(null);
            boxSn.setStatus(StockStatus.WAITING);
            boxSn.setBoxSkuCode(boxSkuCode);
            boxSn.setPcs(totalCount);
            boxSn.setSupplierBoxSnCode(boxSku.getSupplierBoxCode() + boxSn.getSerialNo());
            boxSn.setBatchNo(batchNo);
            boxSn.setPrinted(Boolean.FALSE);
            boxSnDao.save(boxSn);

            ExBoxSnDto boxSnDto = toExBoxSnFullDto(boxSn);
            boxSnDtoList.add(boxSnDto);
        }
        return boxSnDtoList;
    }

    @Override
    public List<ExBoxSnDto> boxSnArrivalList(Long boxArrivalOrderItemId) {
        List<BoxSn> boxSnList = boxSnDao.findAllByBoxArrivalOrderItemId(boxArrivalOrderItemId);
        return boxSnList.stream().map(this::toExBoxSnStatusDto).toList();
    }

    @Override
    public List<ExBoxSnDto> boxSnArrivalList(List<Long> boxArrivalOrderItemIdList) {
        List<BoxSn> boxSnList = boxSnDao.findAllByBoxArrivalOrderItemIdIn(boxArrivalOrderItemIdList);
        return boxSnList.stream().map(this::toExBoxSnUndoDto).toList();
    }

    @Override
    public List<ExBoxSnDto> boxSnDeliveryList(Long boxDeliveryOrderItemId) {
        List<BoxSn> boxSnList = boxSnDao.findAllDeliveryByBoxDeliveryOrderItemId(boxDeliveryOrderItemId);
        return boxSnList.stream().map(this::toExBoxSnStatusDto).toList();
    }

    @Override
    public List<ExBoxSnDto> boxSnDeliveryList(List<Long> boxDeliveryOrderItemIdList) {
        List<BoxSn> boxSnList = boxSnDao.findAllDeliveryByBoxDeliveryOrderItemIdIn(boxDeliveryOrderItemIdList);
        return boxSnList.stream().map(this::toExBoxSnStatusDto).toList();
    }

    @Override
    public List<ExBoxSnDto> boxSnUnArrivalList(Long boxArrivalOrderItemId) {
        List<BoxSn> boxSnList = boxSnDao.findAllUnArrivalByBoxArrivalOrderItemId(boxArrivalOrderItemId);
        return boxSnList.stream().map(this::toExBoxSnUndoDto).toList();
    }

    @Override
    public List<ExBoxSnDto> boxSnUnArrivalPrintList(Long boxArrivalOrderItemId) {
        List<Object> boxSnList = boxSnDao.findAllUnArrivalDetailByBoxArrivalOrderItemId(boxArrivalOrderItemId);
        return boxSnList.stream().map(this::toExBoxSnTemplateDto).toList();
    }

    @Override
    public List<ExBoxSnDto> boxSnUnArrivalPrintList(List<Long> boxArrivalOrderItemIdList) {
        List<Object> boxSnList = boxSnDao.findAllUnArrivalDetailByBoxArrivalOrderItemIdIn(boxArrivalOrderItemIdList);
        return boxSnList.stream().map(this::toExBoxSnTemplateDto).toList();
    }

    @Override
    public List<ExBoxSnDto> boxSnUnDeliveryList(String boxSkuCode) {
        List<BoxSn> boxSnList = boxSnDao.findAllUnDeliveryByBoxSkuCode(boxSkuCode);
        return boxSnList.stream().map(this::toExBoxSnUndoDto).toList();
    }

    @Override
    public List<ExBoxSnDto> boxSnUnDeliveryList(List<String> boxSkuCodeList) {
        List<BoxSn> boxSnList = boxSnDao.findAllUnDeliveryByBoxSkuCodeIn(boxSkuCodeList);
        return boxSnList.stream().map(this::toExBoxSnStatusDto).toList();
    }

    @Override
    public ExBoxSnDto skuDetailByBoxSnCode(String boxSnCode) throws BusinessException {
        Object fullInfo = boxSnDao.findByCodeWithSku(boxSnCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_SN_NOT_FOUND));
        return toExBoxSnTemplateDto(fullInfo);
    }

    @Override
    public ExBoxSnDto skuDetailById(Long boxSnId) throws BusinessException {
        Object fullInfo = boxSnDao.findFullInfoById(boxSnId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_SN_NOT_FOUND));
        return toExBoxSnTemplateDto(fullInfo);
    }

    @Override
    public ExBoxSnDto skuInfoBySupplierBoxSnCode(String supplierBoxSnCode) throws BusinessException {
        Object fullInfo = boxSnDao.findBySupplierBoxSnCodeWithSku(supplierBoxSnCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_SN_NOT_FOUND));
        return toExBoxSnTemplateDto(fullInfo);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public String boxSnInbound(Long boxArrivalOrderItemId, String boxSnCode) throws BusinessException {
        BoxSn boxSn;
        if (StringUtilPlus.isBlank(boxSnCode)) {
            boxSn = boxSnDao.findAllUnArrivalByBoxArrivalOrderItemId(boxArrivalOrderItemId, PageRequest.ofSize(1)).getContent().stream().findFirst().orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_ITEM_SN_INBOUND_NOT_MATCH));
        } else {
            boxSn = boxSnDao.findByCode(boxSnCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_ARRIVAL_ORDER_ITEM_SN_INBOUND_NOT_MATCH));
        }
        if(boxSn.getStatus() != StockStatus.WAITING){
            throw new BusinessException(WmsMessageConstant.ERROR_BOX_SN_ALREADY_INBOUND);
        }
        BoxSku sku = boxSkuDao.findByCode(boxSn.getBoxSkuCode()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_SKU_NOT_FOUND, boxSn.getBoxSkuCode()));
        //if(StringUtilPlus.isBlank(sku.getLocation())){
        //    throw new BusinessException(WmsMessageConstant.ERROR_SKU_SUPPLIER_LOCATION_NOT_ALLOCATE, boxSn.getBoxSkuCode());
        //}

        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));
        //更新sku入库信息以及库位
        boxSn.setLocation(sku.getLocation());
        boxSn.setInboundTime(currTime);
        boxSn.setStatus(StockStatus.INBOUND);
        boxSnDao.save(boxSn);

        return boxSn.getCode();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public String boxSnOutbound(Long boxDeliveryOrderItemId, String boxSkuCode, String boxSnCode) throws BusinessException {
        BoxSn boxSn;
        if (StringUtilPlus.isBlank(boxSnCode)) {
            boxSn = boxSnDao.findAllUnDeliveryByBoxSkuCode(boxSkuCode, PageRequest.ofSize(1)).getContent().stream().findFirst().orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_TASK_ITEM_SN_OUTBOUND_NOT_MATCH));
        } else {
            boxSn = boxSnDao.findByCode(boxSnCode).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_TASK_ITEM_SN_OUTBOUND_NOT_MATCH));
        }
        if(boxSn.getStatus() != StockStatus.INBOUND){
            throw new BusinessException(WmsMessageConstant.ERROR_BOX_SN_ALREADY_OUTBOUND);
        }
        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));
        boxSn.setBoxDeliveryOrderItemId(boxDeliveryOrderItemId);
        boxSn.setOutboundTime(currTime);
        boxSn.setStatus(StockStatus.OUTBOUND);
        boxSnDao.save(boxSn);

        return boxSn.getCode();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void boxSnMarkPrinted(Long boxSnId) throws BusinessException {
        BoxSn boxSn = boxSnDao.findById(boxSnId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_BOX_SN_NOT_FOUND));
        boxSn.setPrinted(Boolean.TRUE);
        boxSnDao.save(boxSn);
    }

    /**
     * 获取打印数据
     *
     * @param fullInfo 打印数据
     * @return 组装数据
     */
    private ExBoxSnDto toExBoxSnTemplateDto(Object fullInfo) {
        BoxSn boxSn = (BoxSn) ((Object[]) fullInfo)[0];
        ExBoxSnDto boxSnDto = new ExBoxSnDto();
        boxSnDto.setBoxArrivalOrderItemId(boxSn.getBoxArrivalOrderItemId());
        boxSnDto.setSerialNo(boxSn.getSerialNo());
        boxSnDto.setPcs(boxSn.getPcs());
        boxSnDto.setSupplierBoxSnCode(boxSn.getSupplierBoxSnCode());
        boxSnDto.setCode(boxSn.getCode());
        boxSnDto.setBoxSkuCode(boxSn.getBoxSkuCode());

        BoxSku boxSku = (BoxSku) ((Object[]) fullInfo)[1];
        ExBoxSkuDto boxSkuDto = new ExBoxSkuDto();
        boxSkuDto.setLocation(boxSku.getLocation());
        boxSkuDto.setSortingTo(boxSku.getSortingTo());
        boxSkuDto.setPickUpCode(boxSku.getPickUpCode());
        boxSkuDto.setDeliveryTo(boxSku.getDeliveryTo());

        boxSnDto.setBoxSku(boxSkuDto);
        return boxSnDto;
    }

    /**
     * DB数据转DTO数据
     *
     * @param boxSn DB数据
     * @return DTO数据
     */
    private ExBoxSnDto toExBoxSnStatusDto(BoxSn boxSn) {
        ExBoxSnDto boxSnDto = new ExBoxSnDto();
        boxSnDto.setBoxSkuCode(boxSn.getBoxSkuCode());
        boxSnDto.setSupplierBoxSnCode(boxSn.getSupplierBoxSnCode());
        boxSnDto.setInboundTime(boxSn.getInboundTime());
        boxSnDto.setOutboundTime(boxSn.getOutboundTime());
        boxSnDto.setLocation(boxSn.getLocation());
        boxSnDto.setStatus(boxSn.getStatus());
        return boxSnDto;
    }

    /**
     * DB数据转DTO数据
     *
     * @param boxSn DB数据
     * @return DTO数据
     */
    private ExBoxSnDto toExBoxSnUndoDto(BoxSn boxSn) {
        ExBoxSnDto boxSnDto = new ExBoxSnDto();
        boxSnDto.setId(boxSn.getId());
        boxSnDto.setSupplierBoxSnCode(boxSn.getSupplierBoxSnCode());
        boxSnDto.setBoxSkuCode(boxSn.getBoxSkuCode());
        boxSnDto.setCode(boxSn.getCode());
        boxSnDto.setPrinted(boxSn.getPrinted());
        return boxSnDto;
    }

    /**
     * DB数据转DTO数据
     *
     * @param boxSn DB数据
     * @return DTO数据
     */
    private ExBoxSnDto toExBoxSnFullDto(BoxSn boxSn) {
        ExBoxSnDto boxSnDto = new ExBoxSnDto();
        boxSnDto.setId(boxSn.getId());
        boxSnDto.setSerialNo(boxSn.getSerialNo());
        boxSnDto.setPcs(boxSn.getPcs());
        boxSnDto.setSupplierBoxSnCode(boxSn.getSupplierBoxSnCode());
        boxSnDto.setCode(boxSn.getCode());
        boxSnDto.setBoxSkuCode(boxSn.getBoxSkuCode());
        boxSnDto.setBatchNo(boxSn.getBatchNo());
        return boxSnDto;
    }
}
