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
import com.dicfree.ms.wms.common.dto.ex.ExBoxSnDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/09/05
 */
public class BoxSnDeliveryExcelExport extends ExcelExportDto {
    /**
     * 导出文件头
     */
    private static final ArrayList<ExcelColumnHeader> COLUMN_HEADER;

    static {
        COLUMN_HEADER = new ArrayList<>();
        COLUMN_HEADER.add(new ExcelColumnHeader("Outbound Time", 14));
        COLUMN_HEADER.add(new ExcelColumnHeader("Supplier Box SN Code", 23));
        COLUMN_HEADER.add(new ExcelColumnHeader("Box SKU Code", 23));
        COLUMN_HEADER.add(new ExcelColumnHeader("Status", 14));
        COLUMN_HEADER.add(new ExcelColumnHeader("Location", 14));
        COLUMN_HEADER.add(new ExcelColumnHeader("AppointmentId", 14));
        COLUMN_HEADER.add(new ExcelColumnHeader("DeliveryTo", 14));
        COLUMN_HEADER.add(new ExcelColumnHeader("SortingMember", 14));
    }

    private final List<ExBoxSnDto> boxSnDtoList;

    public BoxSnDeliveryExcelExport(List<ExBoxSnDto> boxSnDtoList) {
        super();
        this.boxSnDtoList = boxSnDtoList;
    }

    @Override
    public List<ExcelColumnHeader> getColumnHeaders(int sheetIndex) {
        return COLUMN_HEADER;
    }

    @Override
    public String getFileName() {
        return "Delivery_Order_BOX_SN_Detail.xlsx";
    }

    @Override
    public void renderWorkbook() {
        int sheetNum = this.addSheet();
        for (ExBoxSnDto boxSnDto : boxSnDtoList) {
            this.fillOneRow(
                    sheetNum
                    , boxSnDto.getOutboundTime() == null ? null : boxSnDto.getOutboundTime().format(DateTimeUtilPlus.SYSTEM_DATE_TIME_FORMAT)
                    , boxSnDto.getSupplierBoxSnCode()
                    , boxSnDto.getBoxSkuCode()
                    , boxSnDto.getStatus()
                    , boxSnDto.getLocation()
                    , boxSnDto.getBoxDeliveryOrder() == null ? null : boxSnDto.getBoxDeliveryOrder().getAppointmentId()
                    , boxSnDto.getBoxDeliveryOrder() == null ? null : boxSnDto.getBoxDeliveryOrder().getAddressInfo()
                    , boxSnDto.getBoxDeliveryOrder() == null ? null : boxSnDto.getBoxDeliveryOrder().getSortingMember()
            );
        }
    }
}
