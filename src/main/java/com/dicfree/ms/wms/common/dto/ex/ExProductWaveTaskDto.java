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
import com.dicfree.ms.wms.common.dto.ProductWaveTaskDto;
import com.dicfree.ms.wms.common.enums.WaveTaskType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import lombok.Getter;
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
 * @date 2023/11/06
 */
@Setter
public class ExProductWaveTaskDto extends ProductWaveTaskDto implements ValidatorDto {
    @Serial
    private static final long serialVersionUID = -106494111043007550L;

    public interface Add {
    }

    public interface CollectionDone {
    }

    private List<ExProductDeliveryOrderDto> productDeliveryOrderList;

    @Getter
    private List<ExShelfDto> shelfList;

    @Override
    @NotEmpty(groups = {Add.class, CollectionDone.class})
    public String getUniqueNo() {
        return super.getUniqueNo();
    }

    @Override
    @NotNull(groups = Add.class)
    public WaveTaskType getType() {
        return super.getType();
    }

    @NotNull(groups = Add.class)
    @Size(min = 1, groups = Add.class)
    @Valid
    @ConvertGroup(from = Add.class, to = ExProductDeliveryOrderDto.WaveTaskAdd.class)
    public List<ExProductDeliveryOrderDto> getProductDeliveryOrderList() {
        return productDeliveryOrderList;
    }

    @Override
    public void checkAndThrowErrors(Class<?> checkType) {
        List<String> codeList = new ArrayList<>();
        Map<String, Object[]> argumentsMap = new HashMap<>();
        Map<String, String> defaultMsgList = new HashMap<>();

        if (checkType == Add.class) {
            Set<String> wayBillSet = new HashSet<>();
            Set<String> courierSet = new HashSet<>();
            String courier = this.getProductDeliveryOrderList().get(0).getCourier();
            for (ExProductDeliveryOrderDto productDeliveryOrderDto : this.getProductDeliveryOrderList()) {
                if (this.getType() == WaveTaskType.C) {
                    if (StringUtilPlus.isEmpty(productDeliveryOrderDto.getCourier())) {
                        String courierEmptyCode = "NotBlank.exProductDeliveryOrderDto.courier";
                        codeList.add(courierEmptyCode);
                        argumentsMap.put(courierEmptyCode, new Object[]{});
                        defaultMsgList.put(courierEmptyCode, "[" + productDeliveryOrderDto.getThirdOrderNo() + "]'s Courier is empty");
                    }
                    if (StringUtilPlus.isEmpty(productDeliveryOrderDto.getWaybill())) {
                        String wayBillEmptyCode = "NotBlank.exProductDeliveryOrderDto.waybill";
                        codeList.add(wayBillEmptyCode);
                        argumentsMap.put(wayBillEmptyCode, new Object[]{});
                        defaultMsgList.put(wayBillEmptyCode, "[" + productDeliveryOrderDto.getThirdOrderNo() + "]'s WayBill is empty");
                    }
                    break;
                }
                //判断wayBill是否重复
                if (StringUtilPlus.isNotBlank(productDeliveryOrderDto.getWaybill()) && !wayBillSet.add(productDeliveryOrderDto.getWaybill())) {
                    String wayBillDuplicateCode = "DuplicateRecord.exProductWaveTaskDto.productDeliveryOrderList";
                    codeList.add(wayBillDuplicateCode);
                    argumentsMap.put(wayBillDuplicateCode, new Object[]{productDeliveryOrderDto.getWaybill()});
                    defaultMsgList.put(wayBillDuplicateCode, "Duplicate wayBill[" + productDeliveryOrderDto.getWaybill() + "] found");
                }
                if (StringUtilPlus.isNotBlank(productDeliveryOrderDto.getWaybill())) {
                    courierSet.add(productDeliveryOrderDto.getCourier());
                }
            }

            //判断courier是否出现了多次
            if (courierSet.size() > 1) {
                String courierNotSameCode = "CourierMustSame.exProductWaveTaskDto.productDeliveryOrderList";
                codeList.add(courierNotSameCode);
                argumentsMap.put(courierNotSameCode, new Object[]{});
                defaultMsgList.put(courierNotSameCode, "Courier must same one!");
            }
        }

        //结果校验
        if (CollectionUtilPlus.Collection.isNotEmpty(codeList)) {
            throw new ValidateException(codeList, argumentsMap, defaultMsgList);
        }
    }
}
