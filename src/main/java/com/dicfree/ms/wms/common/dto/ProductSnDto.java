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
import com.dicfree.ms.wms.common.enums.ShelfStatus;
import com.dicfree.ms.wms.common.enums.StockStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author wiiyaya
 * @date 2023/10/25
 */
@Getter
@Setter
public class ProductSnDto extends BaseDto<String, Long> {
    @Serial
    private static final long serialVersionUID = 256563145382939780L;

    /**
     * 电商商品号
     */
    private String productSkuCode;

    /**
     * 序列商品号 -> 毫秒时间戳+序号
     */
    private String code;

    /**
     * 货架区域
     */
    private String shelfAreaCode;

    /**
     * 货架号
     */
    private String shelfNo;

    /**
     * 是否完整货物
     */
    private Boolean quality;

    /**
     * 货物库存状态
     */
    private StockStatus stockStatus;

    /**
     * 入库时间
     */
    private LocalDateTime inboundTime;

    /**
     * 出库时间
     */
    private LocalDateTime outboundTime;

    /**
     * 货物货架状态
     */
    private ShelfStatus shelfStatus;

    /**
     * 上架时间
     */
    private LocalDateTime onShelfTime;

    /**
     * 下架时间
     */
    private LocalDateTime offShelfTime;

    /**
     * 是否已分拣
     */
    private Boolean sorted;

    /**
     * 是否已检验
     */
    private Boolean verified;

    /**
     * 商品入库订单详情id
     */
    private Long arrivalOrderItemId;

    /**
     * 商品出库订单详情id
     */
    private Long deliveryOrderItemId;

    /**
     * 同一批数量
     */
    private Integer pcs;

    /**
     * 序号 -> 3位，数值
     */
    private String serialNo;

    /**
     * 备注
     */
    private String remark;
}
