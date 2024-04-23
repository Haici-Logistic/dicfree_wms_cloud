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
package com.dicfree.ms.wms.service;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.query.ExProductDeliveryOrderItemQueryDto;
import org.springframework.data.domain.Pageable;

/**
 * @author wiiyaya
 * @date 2023/10/28
 */
public interface ProductDeliveryOrderItemService {

    /**
     * 电商商品出库订单详情分页
     *
     * @param productDeliveryOrderItemQueryDto 查询条件
     * @param pageable          分页条件
     * @return 结果
     */
    PageDto<ExProductDeliveryOrderItemDto> productDeliveryOrderItemPage(ExProductDeliveryOrderItemQueryDto productDeliveryOrderItemQueryDto, Pageable pageable);

    /**
     * 电商商品出库订单详情添加
     *
     * @param productDeliveryOrderItemDto 电商商品出库订单详情信息
     */
    void productDeliveryOrderItemAdd(ExProductDeliveryOrderItemDto productDeliveryOrderItemDto) throws BusinessException;

    /**
     * 电商商品出库订单详情编辑初始化
     *
     * @param productDeliveryOrderItemId 电商商品出库订单详情id
     * @return 电商商品出库订单详情信息
     */
    ExProductDeliveryOrderItemDto productDeliveryOrderItemEditInit(Long productDeliveryOrderItemId) throws BusinessException;

    /**
     * 电商商品出库订单详情编辑
     *
     * @param productDeliveryOrderItemDto 电商商品出库订单详情信息
     */
    void productDeliveryOrderItemEdit(ExProductDeliveryOrderItemDto productDeliveryOrderItemDto) throws BusinessException;

    /**
     * 电商商品出库订单详情详情
     *
     * @param productDeliveryOrderItemId 电商商品出库订单详情id
     * @return 电商商品出库订单详情信息
     */
    ExProductDeliveryOrderItemDto productDeliveryOrderItemDetail(Long productDeliveryOrderItemId) throws BusinessException;

    /**
     * 电商商品出库订单详情删除
     *
     * @param productDeliveryOrderItemId 电商商品出库订单详情id
     */
    void productDeliveryOrderItemDelete(Long productDeliveryOrderItemId) throws BusinessException;
}
