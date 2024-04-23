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
import com.dicfree.ms.wms.common.dto.ProductDeliveryOrderDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
 * @date 2023/10/28
 */
@Setter
public class ExProductDeliveryOrderDto extends ProductDeliveryOrderDto implements ValidatorDto{
    @Serial
    private static final long serialVersionUID = -973853215612845400L;

    public interface Add {
    }

    public interface Edit {
    }

    public interface WaveTaskAdd {
    }

    public interface Trace {
    }

    /**
     * 订单详情信息
     */
    @Getter
    private List<ExProductDeliveryOrderItemDto> productDeliveryOrderItemList;

    /**
     * 物流信息
     */
    @Getter
    private List<ExTracingDto> tracingList;

    /**
     * pdf内容
     */
    @Getter
    private String content;

    /**
     * 固定为pdf
     */
    @Getter
    private String contentType;

    /**
     * 内容是base654还是链接
     */
    @Getter
    private Boolean contentBase64;

    @Override
    @NotBlank(groups = {Add.class})
    public String getSupplier() {
        return super.getSupplier();
    }

    @Override
    @NotNull(groups = {Add.class})
    public Boolean getProxy() {
        return super.getProxy();
    }

    @Override
    @NotBlank(groups = {Add.class, WaveTaskAdd.class})
    public String getThirdOrderNo() {
        return super.getThirdOrderNo();
    }

    @Override
    @NotBlank(groups = {Trace.class})
    public String getWaybill() {
        return super.getWaybill();
    }

    @Override
    @NotNull(groups = {Trace.class})
    public String getCourier() {
        return super.getCourier();
    }

    @Override
    @NotBlank(groups = {Add.class})
    public String getName() {
        return super.getName();
    }

    @Override
    @NotBlank(groups = {Add.class})
    public String getPhone1() {
        return super.getPhone1();
    }

    @Override
    @NotBlank(groups = {Add.class})
    public String getPhone2() {
        return super.getPhone2();
    }

    @Override
    @NotBlank(groups = {Add.class})
    public String getCity() {
        return super.getCity();
    }

    @Override
    @NotBlank(groups = {Add.class})
    public String getProvince() {
        return super.getProvince();
    }

    @Override
    @NotBlank(groups = {Add.class})
    public String getAddress() {
        return super.getAddress();
    }

    //@Override
    //@NotNull(groups = {Add.class})
    //@DecimalMin("0.00")
    //public BigDecimal getCod() {
    //    return super.getCod();
    //}

    @Override
    public void checkAndThrowErrors(Class<?> checkType) {
        List<String> codeList = new ArrayList<>();
        Map<String, Object[]> argumentsMap = new HashMap<>();
        Map<String, String> defaultMsgList = new HashMap<>();

        if (checkType == Add.class) {
            //判断getProductDeliveryOrderItemList()里面的内容是否重复
            Set<String> productCodeSet = new HashSet<>();
            for (ExProductDeliveryOrderItemDto productDeliveryOrderItemDto : this.getProductDeliveryOrderItemList()) {
                if (!productCodeSet.add(productDeliveryOrderItemDto.getProductCode())) {
                    String productCodeDuplicateCode = "DuplicateRecord.exProductDeliveryOrderDto.productDeliveryOrderItemList";
                    codeList.add(productCodeDuplicateCode);
                    argumentsMap.put(productCodeDuplicateCode, new Object[]{productDeliveryOrderItemDto.getProductCode()});
                    defaultMsgList.put(productCodeDuplicateCode, "Duplicate productCode[" + productDeliveryOrderItemDto.getProductCode() + "] found");
                }
            }
        }
        //结果校验
        if (CollectionUtilPlus.Collection.isNotEmpty(codeList)) {
            throw new ValidateException(codeList, argumentsMap, defaultMsgList);
        }
    }
}
