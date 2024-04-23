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
import cn.jzyunqi.common.support.SpringContextUtils;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.DateTimeUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.dto.CollectionTaskDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.auditing.DateTimeProvider;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
@Setter
public class ExCollectionTaskDto extends CollectionTaskDto implements ValidatorDto{
    @Serial
    private static final long serialVersionUID = -233329862913497900L;

    public interface Add {
    }

    public interface Edit {
    }

    /**
     * 发货订单id列表
     */
    private List<Long> boxDeliveryOrderIdList;

    /**
     * 发货订单第三方单号列表
     */
    @Getter
    private List<ExBoxDeliveryOrderDto> boxDeliveryOrderList;

    @Override
    @NotNull(groups = Edit.class)
    public Long getId() {
        return super.getId();
    }

    @Override
    @NotBlank(groups = {Add.class})
    public String getCollectionNoVirtual() {
        return super.getCollectionNoVirtual();
    }

    @Override
    @NotBlank(groups = {Add.class, Edit.class})
    public String getCollectionNoReal() {
        return super.getCollectionNoReal();
    }

    @Override
    @NotNull(groups = {Add.class, Edit.class})
    //@Future(groups = {Add.class, Edit.class}) 这个校验能力太差
    public LocalDateTime getDepartureDate() {
        return super.getDepartureDate();
    }

    @NotNull(groups = {Add.class, Edit.class})
    @Size(min = 1, groups = {Add.class, Edit.class})
    public List<Long> getBoxDeliveryOrderIdList() {
        return boxDeliveryOrderIdList;
    }

    /**
     * 组装返回值给前端
     */
    @JsonProperty("collectionNo")
    public String getCollectionNo(){
        return StringUtilPlus.joinWith(StringUtilPlus.HYPHEN, super.getCollectionNoVirtual(), super.getCollectionNoReal());
    }

    @Override
    public void checkAndThrowErrors(Class<?> checkType) {
        List<String> codeList = new ArrayList<>();
        Map<String, Object[]> argumentsMap = new HashMap<>();
        Map<String, String> defaultMsgList = new HashMap<>();

        DateTimeProvider dateTimeProvider = SpringContextUtils.getBean(DateTimeProvider.class);
        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));
        if(getDepartureDate().isBefore(DateTimeUtilPlus.dayStart(currTime))){
            String todayOrFeatureCode = "TodayOrFeature.exCollectionTaskDto.departureDate";
            codeList.add(todayOrFeatureCode);
            argumentsMap.put(todayOrFeatureCode, new Object[]{getDepartureDate()});
            defaultMsgList.put(todayOrFeatureCode, "must be today or a future date");
        }
        //结果校验
        if (CollectionUtilPlus.Collection.isNotEmpty(codeList)) {
            throw new ValidateException(codeList, argumentsMap, defaultMsgList);
        }
    }
}
