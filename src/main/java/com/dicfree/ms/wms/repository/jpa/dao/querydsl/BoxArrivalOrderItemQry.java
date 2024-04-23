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

import com.dicfree.ms.wms.common.dto.ex.query.ExBoxArrivalOrderItemQueryDto;
import com.dicfree.ms.wms.repository.jpa.entity.QBoxArrivalOrderItem;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;

/**
 * @author wiiyaya
 * @date 2023/09/02
 */
public class BoxArrivalOrderItemQry {

    /**
     * 整装箱入库订单详情表
     */
    private static final QBoxArrivalOrderItem ARRIVAL_ORDER_ITEM = QBoxArrivalOrderItem.boxArrivalOrderItem;

    /**
     * 组装查询条件
     *
     * @param boxArrivalOrderItemQueryDto 查询条件
     * @return 查询条件
     */
    public static Predicate searchBoxArrivalOrderItem(ExBoxArrivalOrderItemQueryDto boxArrivalOrderItemQueryDto) {
        return Expressions.TRUE;
    }

    /**
     * 组装排序条件
     *
     * @param pageable 排序条件
     * @return 排序条件
     */
    public static Pageable searchBoxArrivalOrderItemOrder(Pageable pageable) {
        return QPageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), QSort.by(ARRIVAL_ORDER_ITEM.createTime.desc()));
    }
}
