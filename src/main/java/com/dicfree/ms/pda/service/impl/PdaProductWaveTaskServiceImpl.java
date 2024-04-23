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
package com.dicfree.ms.pda.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.pda.service.PdaProductWaveTaskService;
import com.dicfree.ms.wms.common.dto.ex.ExDevicePdaDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductWaveTaskDto;
import com.dicfree.ms.wms.common.dto.ex.ExShelfDto;
import com.dicfree.ms.wms.common.enums.OrderType;
import com.dicfree.ms.wms.common.enums.ShelfStatus;
import com.dicfree.ms.wms.service.DevicePdaService;
import com.dicfree.ms.wms.service.ProductWaveTaskService;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wiiyaya
 * @date 2023/11/6
 */
@Service("pdaProductWaveTaskService")
public class PdaProductWaveTaskServiceImpl implements PdaProductWaveTaskService {

    @Resource
    private ProductWaveTaskService productWaveTaskService;

    @Resource
    private DevicePdaService devicePdaService;

    @Resource
    private QingFlowClient qingFlowClient;

    @Override
    public Integer productWaveTaskOffShelfUndoCount(String devicePdaCode) throws BusinessException {
        ExDevicePdaDto devicePdaDto = devicePdaService.devicePdaInfo(devicePdaCode);
        List<ExProductWaveTaskDto> productWaveTaskDtoList = productWaveTaskService.productWaveTaskOffShelfUndoList();
        //计算这些单下指定区域需要出的货物数量
        removeNotMatchSn(devicePdaDto.getShelfAreaCode(), productWaveTaskDtoList);
        //返回波次的数量
        return productWaveTaskDtoList.size();
    }

    @Override
    public List<ExProductWaveTaskDto> productWaveTaskOffShelfUndoList(String devicePdaCode) throws BusinessException {
        ExDevicePdaDto devicePdaDto = devicePdaService.devicePdaInfo(devicePdaCode);
        List<ExProductWaveTaskDto> productWaveTaskDtoList = productWaveTaskService.productWaveTaskOffShelfUndoList();
        //计算这些单下指定区域需要出的货物数量
        removeNotMatchSn(devicePdaDto.getShelfAreaCode(), productWaveTaskDtoList);

        //并去掉不需要返回的数据
        productWaveTaskDtoList.forEach(productWaveTaskDto -> productWaveTaskDto.setProductDeliveryOrderList(null));
        return productWaveTaskDtoList;
    }

    @Override
    public ExProductWaveTaskDto productWaveTaskOffShelfUndoDetail(String devicePdaCode, Long productWaveTaskId) throws BusinessException {
        ExDevicePdaDto devicePdaDto = devicePdaService.devicePdaInfo(devicePdaCode);
        ExProductWaveTaskDto productWaveTaskDto = productWaveTaskService.productWaveTaskOffShelfDetail(productWaveTaskId);
        //计算这些单下指定区域需要出的货物数量
        removeNotMatchSn(devicePdaDto.getShelfAreaCode(), productWaveTaskDto);
        //波次里面的所有货物按照货架分组展示
        List<ExShelfDto> shelfDtoList = productWaveTaskDto.getProductDeliveryOrderList()
                .stream()
                .flatMap(productDeliveryOrderDto -> productDeliveryOrderDto.getProductDeliveryOrderItemList().stream())
                .flatMap(productDeliveryOrderItemDto -> productDeliveryOrderItemDto.getProductSnList().stream())
                .collect(Collectors.groupingBy(ExProductSnDto::getShelfNo))
                .entrySet()
                .stream()
                //shelfEntry.getKey()是货架号，shelfEntry.getValue()是货架上的货物
                .map(shelfEntry -> {
                    ExShelfDto shelfDto = new ExShelfDto();
                    shelfDto.setShelfNo(shelfEntry.getKey());

                    Map<String, List<ExProductSnDto>> proudtcMap = shelfEntry.getValue().stream().collect(Collectors.groupingBy(ExProductSnDto::getProductSkuCode));
                    //productEntry.getKey()是商品编码，productEntry.getValue()是商品编码对应的货物
                    shelfDto.setProductDeliveryOrderItemList(proudtcMap.entrySet().stream().map(productEntry -> {
                        ExProductDeliveryOrderItemDto productDeliveryOrderItemDto = new ExProductDeliveryOrderItemDto();
                        productDeliveryOrderItemDto.setProductSkuCode(productEntry.getKey());

                        //重新计算已下架数量
                        long offShelfCount = productEntry.getValue()
                                .stream()
                                .filter(productSnDto -> productSnDto.getShelfStatus() == ShelfStatus.OFF_SHELF)
                                .count();
                        productDeliveryOrderItemDto.setOffShelfCount((int) offShelfCount);
                        productDeliveryOrderItemDto.setTotalCount(productEntry.getValue().size());
                        return productDeliveryOrderItemDto;
                    }).toList());
                    return shelfDto;
                })
                .toList();
        productWaveTaskDto.setShelfList(shelfDtoList);
        //去掉不需要返回的数据
        productWaveTaskDto.setProductDeliveryOrderList(null);
        return productWaveTaskDto;
    }

    @Override
    public void productWaveTaskSnOffShelf(String devicePdaCode, Long productWaveTaskId, String shelfNo, String productCode) throws BusinessException {
        ExDevicePdaDto devicePdaDto = devicePdaService.devicePdaInfo(devicePdaCode);
        productWaveTaskService.productWaveTaskSnOffShelf(productWaveTaskId, shelfNo, productCode, devicePdaDto.getShelfAreaCode());
    }

    @Override
    public List<ExProductWaveTaskDto> productWaveTaskCollectionUndoList() {
        return productWaveTaskService.productWaveTaskCollectionUndoList();
    }

    @Override
    public List<ExProductWaveTaskDto> productWaveTaskCollectionOffShelfUndoList() {
        return productWaveTaskService.productWaveTaskCollectionOffShelfUndoList();
    }

    @Override
    public Integer productWaveTaskCollectionOffShelfUndoCount() {
        return productWaveTaskService.productWaveTaskCollectionOffShelfUndoCount();
    }

    @Override
    public void productWaveTaskCollectionDone(String collectionAreaCode) throws BusinessException {
        productWaveTaskService.productWaveTaskCollectionDone(collectionAreaCode);
    }

    @Override
    public List<ExProductDeliveryOrderDto> productWaveTaskBasketInit(String productWaveTaskNo) throws BusinessException {
        return productWaveTaskService.productWaveTaskBasketInit(productWaveTaskNo);
    }

    @Override
    public void productWaveTaskBasketBind(String waybill, String basketNo) throws BusinessException {
        productWaveTaskService.productWaveTaskBasketBind(waybill, basketNo);
    }

    @Override
    public void productWaveTaskWeighing(String waybill, Float weight) throws BusinessException {
        qingFlowClient.productWaveTaskWeighingNotice(waybill, weight);
    }

    private void removeNotMatchSn(String shelfAreaCode, List<ExProductWaveTaskDto> productWaveTaskDtoList) {
        productWaveTaskDtoList.removeIf(productWaveTaskDto -> {
            removeNotMatchSn(shelfAreaCode, productWaveTaskDto);
            //没有订单的波次删掉
            return productWaveTaskDto.getProductDeliveryOrderList().isEmpty();
        });
    }

    private static void removeNotMatchSn(String shelfAreaCode, ExProductWaveTaskDto productWaveTaskDto) {
        productWaveTaskDto.getProductDeliveryOrderList().removeIf(productDeliveryOrderDto -> {
            productDeliveryOrderDto.getProductDeliveryOrderItemList().removeIf(productDeliveryOrderItemDto -> {
                productDeliveryOrderItemDto.getProductSnList().removeIf(productSnDto -> {
                    //不是区域的货物删掉
                    boolean notMatch = !StringUtilPlus.contains(shelfAreaCode, productSnDto.getShelfAreaCode());
                    //重新计算总数和已下架数量
                    if (notMatch) {
                        //货物不是区域的，已下架数量减1,有可能还一个都没有下架，防止负数，这里取0
                        int offShelfCount = Math.max(productDeliveryOrderDto.getOffShelfCount() - 1, 0);
                        productDeliveryOrderDto.setOffShelfCount(offShelfCount);
                        productDeliveryOrderDto.setTotalCount(productDeliveryOrderDto.getTotalCount() - 1);
                    }
                    return notMatch;
                });
                //没有货物的订单详情删掉
                return productDeliveryOrderItemDto.getProductSnList().isEmpty();
            });
            //没有订单详情的订单删掉
            return productDeliveryOrderDto.getProductDeliveryOrderItemList().isEmpty();
        });
    }
}
