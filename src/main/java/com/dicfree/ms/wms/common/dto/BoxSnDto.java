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
package com.dicfree.ms.wms.common.dto;

import cn.jzyunqi.common.model.BaseDto;
import com.dicfree.ms.wms.common.enums.StockStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author wiiyaya
 * @date 2023/07/17
 */
@Getter
@Setter
public class BoxSnDto extends BaseDto<String, Long> {
    @Serial
    private static final long serialVersionUID = -557385077623779200L;

    /**
     * 序列箱号 -> 毫秒时间戳+序号
     */
    private String code;

    /**
     * 整装箱入库订单详情id
     */
    private Long boxArrivalOrderItemId;

    /**
     * 入库时间
     */
    private LocalDateTime inboundTime;

    /**
     * 整装箱出库订单详情id
     */
    private Long boxDeliveryOrderItemId;

    /**
     * 出库时间
     */
    private LocalDateTime outboundTime;

    /**
     * 货物状态
     */
    private StockStatus status;

    /**
     * 箱号
     */
    private String boxSkuCode;

    /**
     * 库位
     */
    private String location;

    /**
     * 序号 -> 3位，数值
     */
    private String serialNo;

    /**
     * 同一批数量
     */
    private Integer pcs;

    /**
     * 客户序列箱号 -> 客户箱号+序号
     */
    private String supplierBoxSnCode;

    /**
     * 导入批次号
     */
    private String batchNo;

    /**
     * 是否已打印
     */
    private Boolean printed;
}
