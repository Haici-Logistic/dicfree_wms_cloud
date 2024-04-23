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
import com.dicfree.ms.wms.common.dto.ProductArrivalOrderDto;
import com.dicfree.ms.wms.common.enums.OrderStatus;
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
 * @date 2023/10/25
 */
@Setter
public class ExProductArrivalOrderDto extends ProductArrivalOrderDto implements ValidatorDto{
    @Serial
    private static final long serialVersionUID = -135147623783626660L;

    public interface Add {
    }

    public interface Edit {
    }

    public interface StatusEdit {
    }

    /**
     * 订单详情信息
     */
    private List<ExProductArrivalOrderItemDto> productArrivalOrderItemList;

    /**
     * 电商商品编号
     */
    private String productSkuCode;

    @Override
    @NotEmpty(groups = Add.class)
    public String getSupplier() {
        return super.getSupplier();
    }

    @Override
    @NotEmpty(groups = {Add.class, StatusEdit.class})
    public String getThirdOrderNo() {
        return super.getThirdOrderNo();
    }

    @NotNull(groups = Add.class)
    @Size(min = 1, groups = Add.class)
    @Valid
    @ConvertGroup(from = Add.class, to = ExProductArrivalOrderItemDto.Add.class)
    public List<ExProductArrivalOrderItemDto> getProductArrivalOrderItemList() {
        return productArrivalOrderItemList;
    }

    @Override
    @NotNull(groups = StatusEdit.class)
    public OrderStatus getInboundStatus() {
        return super.getInboundStatus();
    }

    @NotBlank(groups = StatusEdit.class)
    public String getProductSkuCode() {
        return productSkuCode;
    }

    @Override
    public void checkAndThrowErrors(Class<?> checkType) {
        List<String> codeList = new ArrayList<>();
        Map<String, Object[]> argumentsMap = new HashMap<>();
        Map<String, String> defaultMsgList = new HashMap<>();

        if (checkType == StatusEdit.class) {
            if (getInboundStatus() == OrderStatus.IN_PROCESSING) {
                String todayOrFeatureCode = "EnumOption.exProductArrivalOrderDto.inboundStatus";
                codeList.add(todayOrFeatureCode);
                argumentsMap.put(todayOrFeatureCode, new Object[]{OrderStatus.IN_PROCESSING});
                defaultMsgList.put(todayOrFeatureCode, "must not be [IN_PROCESSING]");
            }
        }
        if (checkType == Add.class) {
            //判断getProductArrivalOrderItemList()里面的内容是否重复
            Set<String> supplierProductCodeSet = new HashSet<>();
            for (ExProductArrivalOrderItemDto productArrivalOrderItemDto : this.getProductArrivalOrderItemList()) {
                if (!supplierProductCodeSet.add(productArrivalOrderItemDto.getProductCode())) {
                    String supplierBoxCodeDuplicateCode = "DuplicateRecord.exProductArrivalOrderDto.productArrivalOrderItemList";
                    codeList.add(supplierBoxCodeDuplicateCode);
                    argumentsMap.put(supplierBoxCodeDuplicateCode, new Object[]{productArrivalOrderItemDto.getProductCode()});
                    defaultMsgList.put(supplierBoxCodeDuplicateCode, "Duplicate supplierBoxCode[" + productArrivalOrderItemDto.getProductCode() + "] found");
                }
            }
        }
        //结果校验
        if (CollectionUtilPlus.Collection.isNotEmpty(codeList)) {
            throw new ValidateException(codeList, argumentsMap, defaultMsgList);
        }
    }
}
