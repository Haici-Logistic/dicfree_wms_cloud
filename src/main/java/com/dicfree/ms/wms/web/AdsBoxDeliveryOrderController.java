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
package com.dicfree.ms.wms.web;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.ms.AdsRestBaseController;
import com.dicfree.ms.wms.common.dto.ex.ExBoxDeliveryOrderDto;
import com.dicfree.ms.wms.service.BoxDeliveryOrderService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Portal - 超级管理员 - 整装箱出库订单接口
 *
 * @author wiiyaya
 * @date 2023/09/02
 */
@RestController
@Validated
public class AdsBoxDeliveryOrderController extends AdsRestBaseController {

    @Resource
    private BoxDeliveryOrderService boxDeliveryOrderService;

    /**
     * 同一目的地整装箱出库订单列表
     *
     * @param collectionTaskId 集货任务id
     * @return 整装箱出库订单信息
     */
    @PostMapping(value = "/ads/boxDeliveryOrder/undoList")
    public List<ExBoxDeliveryOrderDto> boxDeliveryOrderUndoList(@RequestParam(required = false) Long collectionTaskId) {
        return boxDeliveryOrderService.boxDeliveryOrderUndoList(collectionTaskId);
    }
}
