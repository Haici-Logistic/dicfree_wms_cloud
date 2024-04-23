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
package com.dicfree.ms.wms.service.impl;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExCollectionAreaDto;
import com.dicfree.ms.wms.common.dto.ex.query.ExCollectionAreaQueryDto;
import com.dicfree.ms.wms.repository.jpa.dao.CollectionAreaDao;
import com.dicfree.ms.wms.repository.jpa.dao.querydsl.CollectionAreaQry;
import com.dicfree.ms.wms.repository.jpa.entity.CollectionArea;
import com.dicfree.ms.wms.service.CollectionAreaService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wiiyaya
 * @date 2023/11/06
 */
@Service("collectionAreaService")
public class CollectionAreaServiceImpl implements CollectionAreaService {

    @Resource
    private CollectionAreaDao collectionAreaDao;

    @Override
    public PageDto<ExCollectionAreaDto> collectionAreaPage(ExCollectionAreaQueryDto collectionAreaQueryDto, Pageable pageable) {
        Page<CollectionArea> collectionAreaPage = collectionAreaDao.findAll(CollectionAreaQry.searchCollectionArea(collectionAreaQueryDto), CollectionAreaQry.searchCollectionAreaOrder(pageable));

        List<ExCollectionAreaDto> collectionAreaDtoList = collectionAreaPage.stream().map(collectionArea -> {
        ExCollectionAreaDto collectionAreaDto = new ExCollectionAreaDto();
        //TODO replace this
        collectionAreaDto = BeanUtilPlus.copyAs(collectionArea, ExCollectionAreaDto.class);
        return collectionAreaDto;
        }).collect(Collectors.toList());

        return new PageDto<>(collectionAreaDtoList, collectionAreaPage.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void collectionAreaAdd(ExCollectionAreaDto collectionAreaDto) throws BusinessException {
        CollectionArea collectionArea = new CollectionArea();

        //TODO replace this
        collectionArea = BeanUtilPlus.copyAs(collectionAreaDto, CollectionArea.class);

        collectionAreaDao.save(collectionArea);
    }

    @Override
    public ExCollectionAreaDto collectionAreaEditInit(Long collectionAreaId) throws BusinessException {
        CollectionArea collectionArea = collectionAreaDao.findById(collectionAreaId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_AREA_NOT_FOUND));

        ExCollectionAreaDto collectionAreaDto = new ExCollectionAreaDto();
        collectionAreaDto.setId(collectionArea.getId());
        return collectionAreaDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void collectionAreaEdit(ExCollectionAreaDto collectionAreaDto) throws BusinessException {
        CollectionArea collectionArea = collectionAreaDao.findById(collectionAreaDto.getId()).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_AREA_NOT_FOUND));

        //TODO replace this
        collectionArea = BeanUtilPlus.copyAs(collectionAreaDto, CollectionArea.class);

        collectionAreaDao.save(collectionArea);
    }

    @Override
    public ExCollectionAreaDto collectionAreaDetail(Long collectionAreaId) throws BusinessException {
        CollectionArea collectionArea = collectionAreaDao.findById(collectionAreaId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_AREA_NOT_FOUND));

        ExCollectionAreaDto collectionAreaDto = new ExCollectionAreaDto();
        //TODO replace this
        collectionAreaDto = BeanUtilPlus.copyAs(collectionArea, ExCollectionAreaDto.class);
        return collectionAreaDto;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void collectionAreaDelete(Long collectionAreaId) throws BusinessException {
        CollectionArea collectionArea = collectionAreaDao.findById(collectionAreaId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_COLLECTION_AREA_NOT_FOUND));
        collectionAreaDao.delete(collectionArea);
    }
}
