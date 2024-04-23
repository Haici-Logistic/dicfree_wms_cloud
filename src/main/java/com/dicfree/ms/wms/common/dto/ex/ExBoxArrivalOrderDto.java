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
package com.dicfree.ms.wms.common.dto.ex;

import cn.jzyunqi.common.exception.ValidateException;
import cn.jzyunqi.common.model.ValidatorDto;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.dto.BoxArrivalOrderDto;
import com.dicfree.ms.wms.common.enums.LocationType;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import lombok.Setter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
@Setter
public class ExBoxArrivalOrderDto extends BoxArrivalOrderDto implements ValidatorDto {
    @Serial
    private static final long serialVersionUID = 846657895884134700L;

    public interface Add {
    }

    public interface StatusEdit {
    }

    public interface ThirdInfo {
    }

    public interface ThirdAdd {
    }

    /**
     * 订单整装箱信息
     */
    private List<ExBoxArrivalOrderItemDto> boxArrivalOrderItemList;

    /**
     * 箱号
     */
    private String boxSkuCode;

    @Override
    @NotEmpty(groups = Add.class)
    public String getSupplier() {
        return super.getSupplier();
    }

    @Override
    @NotEmpty(groups = {Add.class, ThirdInfo.class, ThirdAdd.class})
    public String getThirdOrderNo() {
        return super.getThirdOrderNo();
    }

    @Override
    @NotEmpty(groups = {StatusEdit.class})
    public String getUniqueNo() {
        return super.getUniqueNo();
    }

    @Override
    @NotNull(groups = StatusEdit.class)
    public OrderStatus getInboundStatus() {
        return super.getInboundStatus();
    }

    @NotBlank(groups = StatusEdit.class)
    public String getBoxSkuCode() {
        return boxSkuCode;
    }

    @NotNull(groups = {Add.class, ThirdAdd.class})
    @Size(min = 1, groups = {Add.class, ThirdAdd.class})
    @Valid
    @ConvertGroup.List({
            @ConvertGroup(from = Add.class, to = ExBoxArrivalOrderItemDto.Add.class),
            @ConvertGroup(from = ThirdAdd.class, to = ExBoxArrivalOrderItemDto.ThirdAdd.class),
    })
    public List<ExBoxArrivalOrderItemDto> getBoxArrivalOrderItemList() {
        return boxArrivalOrderItemList;
    }

    /**
     * 组装返回值给前端
     */
    @JsonProperty("containerNo")
    public String getContainerNo() {
        return StringUtilPlus.joinWith(StringUtilPlus.HYPHEN, super.getContainerNoVirtual(), super.getContainerNoReal());
    }

    @Override
    public void checkAndThrowErrors(Class<?> checkType) {
        List<String> codeList = new ArrayList<>();
        Map<String, Object[]> argumentsMap = new HashMap<>();
        Map<String, String> defaultMsgList = new HashMap<>();

        if (checkType == StatusEdit.class) {
            if (getInboundStatus() == OrderStatus.IN_PROCESSING) {
                String todayOrFeatureCode = "EnumOption.exBoxArrivalOrderDto.status";
                codeList.add(todayOrFeatureCode);
                argumentsMap.put(todayOrFeatureCode, new Object[]{OrderStatus.IN_PROCESSING});
                defaultMsgList.put(todayOrFeatureCode, "must not be [IN_PROCESSING]");
            }
        }
        if (checkType == Add.class || checkType == ThirdAdd.class) {
            //判断getBoxArrivalOrderItemList()里面的内容是否重复
            Set<String> supplierBoxCodeSet = new HashSet<>();
            for (ExBoxArrivalOrderItemDto boxArrivalOrderItemDto : this.getBoxArrivalOrderItemList()) {
                if (!supplierBoxCodeSet.add(boxArrivalOrderItemDto.getSupplierBoxCode())) {
                    String supplierBoxCodeDuplicateCode = "DuplicateRecord.exBoxArrivalOrderDto.boxArrivalOrderItemList";
                    codeList.add(supplierBoxCodeDuplicateCode);
                    argumentsMap.put(supplierBoxCodeDuplicateCode, new Object[]{boxArrivalOrderItemDto.getSupplierBoxCode()});
                    defaultMsgList.put(supplierBoxCodeDuplicateCode, "Duplicate supplierBoxCode[" + boxArrivalOrderItemDto.getSupplierBoxCode() + "] found");
                }
            }
        }
        if (checkType == Add.class) {
            //判断getBoxArrivalOrderItemList()里面的Whole和Bulk的数量与getWholeQuantity()和getBulkQuantity()的关系是否正确
            int wholeSkuCount = this.getBoxArrivalOrderItemList().stream()
                    .filter(boxArrivalOrderItemDto -> boxArrivalOrderItemDto.getLocationType() == LocationType.Whole)
                    .mapToInt(value -> 1)
                    .sum();
            if (wholeSkuCount > this.getTotalWholeCount()) {
                String wholeAndBulkQuantityNotEqualCode = "CountNotMatch.exBoxArrivalOrderDto.boxArrivalOrderItemList";
                codeList.add(wholeAndBulkQuantityNotEqualCode);
                argumentsMap.put(wholeAndBulkQuantityNotEqualCode, new Object[]{wholeSkuCount, this.getTotalWholeCount()});
                defaultMsgList.put(wholeAndBulkQuantityNotEqualCode, "Whole[sku:" + wholeSkuCount + "][location:" + this.getTotalWholeCount() + "] not match");
            }

            int bulkSnCount = this.getBoxArrivalOrderItemList().stream()
                    .filter(boxArrivalOrderItemDto -> boxArrivalOrderItemDto.getLocationType() == LocationType.Bulk)
                    .mapToInt(ExBoxArrivalOrderItemDto::getTotalCount)
                    .sum();
            if (bulkSnCount < this.getTotalBulkCount()) {
                String wholeAndBulkQuantityNotEqualCode = "CountNotMatch.exBoxArrivalOrderDto.boxArrivalOrderItemList";
                codeList.add(wholeAndBulkQuantityNotEqualCode);
                argumentsMap.put(wholeAndBulkQuantityNotEqualCode, new Object[]{bulkSnCount, this.getTotalBulkCount()});
                defaultMsgList.put(wholeAndBulkQuantityNotEqualCode, "Bulk [sn:" + bulkSnCount + "][location:" + this.getTotalBulkCount() + "] not match");
            }
        }
        //结果校验
        if (CollectionUtilPlus.Collection.isNotEmpty(codeList)) {
            throw new ValidateException(codeList, argumentsMap, defaultMsgList);
        }
    }
}
