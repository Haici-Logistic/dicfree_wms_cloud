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
import com.dicfree.ms.wms.common.dto.BoxDeliveryOrderDto;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;
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
public class ExBoxDeliveryOrderDto extends BoxDeliveryOrderDto implements ValidatorDto {
    @Serial
    private static final long serialVersionUID = -468808997474775360L;

    public interface Add {
    }

    public interface StatusEdit {
    }

    public interface ThirdPublicAdd {
    }

    public interface ThirdPrivateAdd {
    }

    public interface ThirdInfo {
    }

    /**
     * 订单整装箱信息
     */
    private List<ExBoxDeliveryOrderItemDto> boxDeliveryOrderItemList;

    /**
     * 箱号
     */
    private String boxSkuCode;

    private String deliveryTo;
    private String name;
    private String phone1;
    @Getter
    private String phone2;
    private String country;
    private String province;
    private String city;
    private String address;

    @Override
    @NotEmpty(groups = Add.class)
    public String getSupplier() {
        return super.getSupplier();
    }

    @Override
    @NotEmpty(groups = {Add.class, ThirdInfo.class, ThirdPublicAdd.class, ThirdPrivateAdd.class})
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
    public OrderStatus getStatus() {
        return super.getStatus();
    }

    @Override
    @NotNull(groups = {Add.class, ThirdPublicAdd.class, ThirdPrivateAdd.class})
    public LocalDateTime getDeliveryDate() {
        return super.getDeliveryDate();
    }

    @Override
    @NotEmpty(groups = {Add.class})
    public String getAddressInfo() {
        return super.getAddressInfo();
    }

    @NotEmpty(groups = {ThirdPublicAdd.class})
    public String getDeliveryTo() {
        return deliveryTo;
    }

    @NotEmpty(groups = {ThirdPrivateAdd.class})
    public String getName() {
        return name;
    }

    @NotEmpty(groups = {ThirdPrivateAdd.class})
    public String getPhone1() {
        return phone1;
    }

    @NotEmpty(groups = {ThirdPrivateAdd.class})
    public String getCountry() {
        return country;
    }

    @NotEmpty(groups = {ThirdPrivateAdd.class})
    public String getProvince() {
        return province;
    }

    @NotEmpty(groups = {ThirdPrivateAdd.class})
    public String getCity() {
        return city;
    }

    @NotEmpty(groups = {ThirdPrivateAdd.class})
    public String getAddress() {
        return address;
    }

    @Override
    public String getAppointmentId() {
        return super.getAppointmentId();
    }

    @NotBlank(groups = StatusEdit.class)
    public String getBoxSkuCode() {
        return boxSkuCode;
    }

    @NotNull(groups = {Add.class, ThirdPublicAdd.class})
    @Size(min = 1, groups = {Add.class, ThirdPublicAdd.class})
    @Valid
    @ConvertGroup.List({
            @ConvertGroup(from = Add.class, to = ExBoxDeliveryOrderItemDto.Add.class),
            @ConvertGroup(from = ThirdPublicAdd.class, to = ExBoxDeliveryOrderItemDto.Add.class),
            @ConvertGroup(from = ThirdPrivateAdd.class, to = ExBoxDeliveryOrderItemDto.Add.class),
    })
    public List<ExBoxDeliveryOrderItemDto> getBoxDeliveryOrderItemList() {
        return boxDeliveryOrderItemList;
    }

    @Override
    public void checkAndThrowErrors(Class<?> checkType) {
        List<String> codeList = new ArrayList<>();
        Map<String, Object[]> argumentsMap = new HashMap<>();
        Map<String, String> defaultMsgList = new HashMap<>();

        if (checkType == StatusEdit.class) {
            if (getStatus() == OrderStatus.IN_PROCESSING) {
                String todayOrFeatureCode = "EnumOption.exBoxDeliveryOrderDto.status";
                codeList.add(todayOrFeatureCode);
                argumentsMap.put(todayOrFeatureCode, new Object[]{OrderStatus.IN_PROCESSING});
                defaultMsgList.put(todayOrFeatureCode, "must not be [IN_PROCESSING]");
            }
        }
        if (checkType == Add.class) {
            //判断getBoxDeliveryOrderItemList()里面的内容是否重复
            Set<String> supplierBoxCodeSet = new HashSet<>();
            for (ExBoxDeliveryOrderItemDto boxDeliveryOrderItemDto : this.getBoxDeliveryOrderItemList()) {
                if (!supplierBoxCodeSet.add(boxDeliveryOrderItemDto.getSupplierBoxCode())) {
                    String supplierBoxCodeDuplicateCode = "DuplicateRecord.exBoxDeliveryOrderDto.boxDeliveryOrderItemList";
                    codeList.add(supplierBoxCodeDuplicateCode);
                    argumentsMap.put(supplierBoxCodeDuplicateCode, new Object[]{boxDeliveryOrderItemDto.getSupplierBoxCode()});
                    defaultMsgList.put(supplierBoxCodeDuplicateCode, "Duplicate supplierBoxCode[" + boxDeliveryOrderItemDto.getSupplierBoxCode() + "] found");
                }
            }
        }
        //结果校验
        if (CollectionUtilPlus.Collection.isNotEmpty(codeList)) {
            throw new ValidateException(codeList, argumentsMap, defaultMsgList);
        }
    }
}
