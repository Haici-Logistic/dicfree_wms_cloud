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
package com.dicfree.ms.wms.repository.jpa.dao.querydsl;

import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.dto.ex.query.ExProductDeliveryOrderQueryDto;
import com.dicfree.ms.wms.repository.jpa.entity.QProductDeliveryOrder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;

/**
 * @author wiiyaya
 * @date 2023/10/28
 */
public class ProductDeliveryOrderQry {

    /**
     * 电商商品出库订单表
     */
    private static final QProductDeliveryOrder PRODUCT_DELIVERY_ORDER = QProductDeliveryOrder.productDeliveryOrder;

    /**
     * 组装查询条件
     *
     * @param productDeliveryOrderQueryDto 查询条件
     * @return 查询条件
     */
    public static Predicate searchProductDeliveryOrder(ExProductDeliveryOrderQueryDto productDeliveryOrderQueryDto) {
        BooleanExpression expression = Expressions.TRUE;
        if(StringUtilPlus.isNotBlank(productDeliveryOrderQueryDto.getSupplier())){
            expression = expression.and(PRODUCT_DELIVERY_ORDER.supplier.eq(productDeliveryOrderQueryDto.getSupplier()));
        }
        if(StringUtilPlus.isNotBlank(productDeliveryOrderQueryDto.getThirdOrderNo())){
            expression = expression.and(PRODUCT_DELIVERY_ORDER.thirdOrderNo.eq(productDeliveryOrderQueryDto.getThirdOrderNo()));
        }
        if(StringUtilPlus.isNotBlank(productDeliveryOrderQueryDto.getWaybill())){
            expression = expression.and(PRODUCT_DELIVERY_ORDER.waybill.eq(productDeliveryOrderQueryDto.getWaybill()));
        }
        return expression;
    }

    /**
     * 组装排序条件
     *
     * @param pageable 排序条件
     * @return 排序条件
     */
    public static Pageable searchProductDeliveryOrderOrder(Pageable pageable) {
        return QPageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), QSort.by(PRODUCT_DELIVERY_ORDER.createTime.desc()));
    }
}
