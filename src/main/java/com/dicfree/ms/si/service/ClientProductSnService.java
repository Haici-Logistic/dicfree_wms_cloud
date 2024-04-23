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
package com.dicfree.ms.si.service;

import cn.jzyunqi.common.exception.BusinessException;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;

/**
 * @author wiiyaya
 * @date 2024/1/16
 */
public interface ClientProductSnService {

    /**
     * 电商商品SN更换货架
     *
     * @param productSnDto 电商商品SN信息
     */
    void snShelfChange(ExProductSnDto productSnDto) throws BusinessException;

    /**
     * 电商商品SN更换SKU code
     * @param productSnDto 电商商品SN信息
     */
    void productSnProductSkuCodeChange(ExProductSnDto productSnDto) throws BusinessException;

    /**
     * 电商商品SN更换quality
     * @param productSnDto 电商商品SN信息
     */
    void productSnQualityChange(ExProductSnDto productSnDto) throws BusinessException;

    /**
     * 电商商品SN重新上架
     * @param productSnDto 电商商品SN信息
     */
    void productSnReOnShelf(ExProductSnDto productSnDto) throws BusinessException;
}
