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
package com.dicfree.ms.wms.common.dto.ex.excel;

import cn.jzyunqi.common.model.poi.ExcelColumnHeader;
import cn.jzyunqi.common.model.poi.ExcelExportDto;
import cn.jzyunqi.common.utils.DateTimeUtilPlus;
import com.dicfree.ms.wms.common.dto.ex.ExStocktakeRecordDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/7/24
 */
public class StocktakeRecordExcelExport extends ExcelExportDto {

    /**
     * 导出文件头
     */
    private static final ArrayList<ExcelColumnHeader> COLUMN_HEADER;

    static {
        COLUMN_HEADER = new ArrayList<>();
        COLUMN_HEADER.add(new ExcelColumnHeader("Date", 14));
        COLUMN_HEADER.add(new ExcelColumnHeader("Arriving Order", 14));
        COLUMN_HEADER.add(new ExcelColumnHeader("Box SN Code", 14));
        COLUMN_HEADER.add(new ExcelColumnHeader("Supplier Box SN Code", 14));
        COLUMN_HEADER.add(new ExcelColumnHeader("Supplier Box Code", 14));
        COLUMN_HEADER.add(new ExcelColumnHeader("Box SKU Code", 14));
        COLUMN_HEADER.add(new ExcelColumnHeader("Type", 23));
    }

    private final List<ExStocktakeRecordDto> stocktakeRecordDtoList;

    private final String fileName;

    public StocktakeRecordExcelExport(String fileName, List<ExStocktakeRecordDto> stocktakeRecordDtoList) {
        super();
        this.stocktakeRecordDtoList = stocktakeRecordDtoList;
        this.fileName = fileName;
    }

    @Override
    public List<ExcelColumnHeader> getColumnHeaders(int sheetIndex) {
        return COLUMN_HEADER;
    }

    @Override
    public String getFileName() {
        return fileName + LocalDate.now() + ".xlsx";
    }

    @Override
    public void renderWorkbook() {
        int sheetNum = this.addSheet();
        for (ExStocktakeRecordDto recordDto : stocktakeRecordDtoList) {
            this.fillOneRow(
                    sheetNum
                    , recordDto.getTriggerTime().format(DateTimeUtilPlus.SYSTEM_DATE_FORMAT)
                    , "Coding..."
                    , recordDto.getBoxSnCode()
                    , recordDto.getSupplierBoxSnCode()
                    , recordDto.getSupplierBoxCode()
                    , recordDto.getBoxSkuCode()
                    , recordDto.getType()
            );
        }
    }
}
