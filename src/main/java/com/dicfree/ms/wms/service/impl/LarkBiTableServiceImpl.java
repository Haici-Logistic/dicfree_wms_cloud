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
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.DateTimeUtilPlus;
import com.dicfree.ms.wms.common.dto.ex.ExBoxDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.lark.BiTableRowData;
import com.dicfree.ms.wms.service.BoxDeliveryOrderService;
import com.dicfree.ms.wms.service.LarkBiTableService;
import com.dicfree.ms.wms.service.client.LarkClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author wiiyaya
 * @date 2023/9/18
 */
@Slf4j
@Service("larkBiTableService")
public class LarkBiTableServiceImpl implements LarkBiTableService {

    @Resource
    private BoxDeliveryOrderService boxDeliveryOrderService;

    @Resource
    private LarkClient larkClient;

    @Override
    public void boxDeliveryOrderSyncToLark(String collectionNoVirtual, List<Long> boxDeliveryOrderIdList) throws BusinessException {
        List<ExBoxDeliveryOrderDto> boxDeliveryOrderList = boxDeliveryOrderService.boxDeliveryOrderDeliveryList(boxDeliveryOrderIdList);

        List<BiTableRowData> rowData = boxDeliveryOrderList.stream().flatMap(boxDeliveryOrderDto -> {
            boxDeliveryOrderDto.getBoxDeliveryOrderItemList().forEach(boxDeliveryOrderItemDto -> boxDeliveryOrderItemDto.setBoxDeliveryOrderDto(boxDeliveryOrderDto));
            return boxDeliveryOrderDto.getBoxDeliveryOrderItemList().stream();
        }).map(boxDeliveryOrderItemDto -> {
            //组装飞书数据
            BiTableRowData.Fields fields = new BiTableRowData.Fields();
            fields.setCollectionNoVirtual(collectionNoVirtual);
            fields.setPickUpCode(boxDeliveryOrderItemDto.getBoxSku().getPickUpCode());
            fields.setPcs(boxDeliveryOrderItemDto.getTotalCount());
            fields.setMember(boxDeliveryOrderItemDto.getBoxDeliveryOrderDto().getSortingMember());
            fields.setLocation(boxDeliveryOrderItemDto.getBoxSku().getLocation());
            fields.setSupplierBoxCode(boxDeliveryOrderItemDto.getBoxSkuCode());
            fields.setSortingDate(DateTimeUtilPlus.toEpochMilli(boxDeliveryOrderItemDto.getBoxDeliveryOrderDto().getDeliveryDate()));
            fields.setStatus(boxDeliveryOrderItemDto.getStatus().name());

            BiTableRowData biTableReqData = new BiTableRowData();
            biTableReqData.setFields(fields);
            return biTableReqData;
        }).toList();
        if (CollectionUtilPlus.Collection.isNotEmpty(rowData)) {
            int maxPreOnce = 500;
            List<List<BiTableRowData>> subLists = IntStream.range(0, (rowData.size() + maxPreOnce - 1) / maxPreOnce)
                    .mapToObj(i -> rowData.subList(i * maxPreOnce, Math.min((i + 1) * maxPreOnce, rowData.size())))
                    .toList();
            log.info("========boxDeliveryOrderSyncToLark with [{}] record split to [{}] batch!=========", rowData.size(), subLists.size());
            for (List<BiTableRowData> subList : subLists) {
                log.info("========start batch with [{}] record=========", subList.size());
                larkClient.addBiTableRecords(subList);
            }
        } else {
            log.warn("========boxDeliveryOrderSyncToLark without record!=========");
        }
    }
}
