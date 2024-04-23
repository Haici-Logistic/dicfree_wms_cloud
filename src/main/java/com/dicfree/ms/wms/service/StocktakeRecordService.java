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
import com.dicfree.ms.wms.common.dto.ex.ExStocktakeRecordDto;
import com.dicfree.ms.wms.common.enums.StocktakeDirection;
import com.dicfree.ms.wms.common.enums.StocktakeType;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/7/27
 */
public interface StocktakeRecordService {

    /**
     * 盘点日志记录
     *
     * @param stocktakeType 盘点查询方式
     * @param first         第一个参数
     * @param second        第二个参数
     */
    void stocktakeLog(StocktakeType stocktakeType, StocktakeDirection direction, String first, String second) throws BusinessException;

    /**
     * 序列箱出库日志
     *
     * @return 序列箱出库日志列表
     */
    List<ExStocktakeRecordDto> boxSnOutboundLog();

    /**
     * 序列箱入库日志
     *
     * @return 序列箱出库日志列表
     */
    List<ExStocktakeRecordDto> boxSnInboundLog();
}
