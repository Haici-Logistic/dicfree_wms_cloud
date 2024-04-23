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
import com.dicfree.ms.wms.common.dto.ex.ExCollectionAreaDto;
import com.dicfree.ms.wms.common.dto.ex.query.ExCollectionAreaQueryDto;
import org.springframework.data.domain.Pageable;

/**
 * @author wiiyaya
 * @date 2023/11/06
 */
public interface CollectionAreaService {

    /**
     * 集货区分页
     *
     * @param collectionAreaQueryDto 查询条件
     * @param pageable          分页条件
     * @return 结果
     */
    PageDto<ExCollectionAreaDto> collectionAreaPage(ExCollectionAreaQueryDto collectionAreaQueryDto, Pageable pageable);

    /**
     * 集货区添加
     *
     * @param collectionAreaDto 集货区信息
     */
    void collectionAreaAdd(ExCollectionAreaDto collectionAreaDto) throws BusinessException;

    /**
     * 集货区编辑初始化
     *
     * @param collectionAreaId 集货区id
     * @return 集货区信息
     */
    ExCollectionAreaDto collectionAreaEditInit(Long collectionAreaId) throws BusinessException;

    /**
     * 集货区编辑
     *
     * @param collectionAreaDto 集货区信息
     */
    void collectionAreaEdit(ExCollectionAreaDto collectionAreaDto) throws BusinessException;

    /**
     * 集货区详情
     *
     * @param collectionAreaId 集货区id
     * @return 集货区信息
     */
    ExCollectionAreaDto collectionAreaDetail(Long collectionAreaId) throws BusinessException;

    /**
     * 集货区删除
     *
     * @param collectionAreaId 集货区id
     */
    void collectionAreaDelete(Long collectionAreaId) throws BusinessException;
}
