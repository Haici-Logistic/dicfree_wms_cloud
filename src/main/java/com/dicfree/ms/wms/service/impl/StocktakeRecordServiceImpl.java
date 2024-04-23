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
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.dto.ex.ExStocktakeRecordDto;
import com.dicfree.ms.wms.common.enums.StocktakeDirection;
import com.dicfree.ms.wms.common.enums.StocktakeType;
import com.dicfree.ms.wms.repository.jpa.dao.StocktakeRecordDao;
import com.dicfree.ms.wms.repository.jpa.entity.StocktakeRecord;
import com.dicfree.ms.wms.service.StocktakeRecordService;
import jakarta.annotation.Resource;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/7/27
 */
@Service("stocktakeRecordService")
public class StocktakeRecordServiceImpl implements StocktakeRecordService {

    @Resource
    private StocktakeRecordDao stocktakeRecordDao;

    @Resource
    private DateTimeProvider dateTimeProvider;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void stocktakeLog(StocktakeType stocktakeType, StocktakeDirection direction, String first, String second) throws BusinessException {
        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));
        StocktakeRecord stocktakeRecord = new StocktakeRecord();
        stocktakeRecord.setDirection(direction);
        stocktakeRecord.setTriggerTime(currTime);
        stocktakeRecord.setType(stocktakeType);
        stocktakeRecord.setHub("Dubai");
        switch (stocktakeType) {
            case BOX_SN_CODE -> stocktakeRecord.setBoxSnCode(first);
            case BOX_SKU_CODE -> stocktakeRecord.setBoxSkuCode(first);
            case SUPPLIER_BOX_CODE -> {
                stocktakeRecord.setSupplierBoxCode(first);
                stocktakeRecord.setSupplierBoxSnCode(StringUtilPlus.joinWith(StringUtilPlus.HYPHEN, first, second));
            }
            case SUPPLIER_BOX_SN_CODE -> stocktakeRecord.setSupplierBoxSnCode(first);
        }

        stocktakeRecordDao.save(stocktakeRecord);
    }

    @Override
    public List<ExStocktakeRecordDto> boxSnOutboundLog() {
        List<StocktakeRecord> stocktakeRecordList = stocktakeRecordDao.findByDirection(StocktakeDirection.OUT);
        return getExStocktakeRecordDtoList(stocktakeRecordList);
    }

    @Override
    public List<ExStocktakeRecordDto> boxSnInboundLog() {
        List<StocktakeRecord> stocktakeRecordList = stocktakeRecordDao.findByDirection(StocktakeDirection.IN);
        return getExStocktakeRecordDtoList(stocktakeRecordList);
    }

    /**
     * 数据转换
     *
     * @param stocktakeRecordList 日志信息
     * @return 日志信息
     */
    private List<ExStocktakeRecordDto> getExStocktakeRecordDtoList(List<StocktakeRecord> stocktakeRecordList) {
        return stocktakeRecordList.stream().map(stocktakeRecord -> {
            ExStocktakeRecordDto recordDto = new ExStocktakeRecordDto();
            recordDto.setTriggerTime(stocktakeRecord.getTriggerTime());
            recordDto.setBoxSkuCode(stocktakeRecord.getBoxSkuCode());
            recordDto.setBoxSnCode(stocktakeRecord.getBoxSnCode());
            recordDto.setType(stocktakeRecord.getType());
            recordDto.setSupplierBoxCode(stocktakeRecord.getSupplierBoxCode());
            recordDto.setSupplierBoxSnCode(stocktakeRecord.getSupplierBoxSnCode());
            return recordDto;
        }).toList();
    }
}
