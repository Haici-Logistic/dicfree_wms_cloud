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
package com.dicfree.ms.si.web;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.spring.security.LoginUserDto;
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import cn.jzyunqi.common.utils.CurrentUserUtils;
import cn.jzyunqi.ms.AdsRestBaseController;
import com.dicfree.ms.si.service.ClientProductWaveTaskService;
import com.dicfree.ms.wms.common.dto.ex.ExProductWaveTaskDto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端 - 电商商品出库波次接口
 *
 * @author wiiyaya
 * @date 2023/11/7
 */
@RestController
@Validated
@Slf4j
public class ClientProductWaveTaskController extends AdsRestBaseController {

    @Resource
    private ClientProductWaveTaskService clientProductWaveTaskService;

    /**
     * 出库波次生成
     *
     * @return 出库波次信息
     */
    @PostMapping(value = "/client/productWaveTask/generate")
    public String productWaveTaskGenerate(@RequestBody @Validated(ExProductWaveTaskDto.Add.class) ExProductWaveTaskDto productWaveTaskDto, BindingResult bindingResult) throws BusinessException {
        log.info("========start ProductWaveTaskJob no from client!=========");
        BindingResultHelper.checkAndThrowErrors(bindingResult, productWaveTaskDto, ExProductWaveTaskDto.Add.class);
        LoginUserDto currentClient = CurrentUserUtils.currentUser();
        clientProductWaveTaskService.waveTaskGenerate(currentClient.getUsername(), productWaveTaskDto);
        log.info("========ProductWaveTaskJob finished from client!=========");
        return "success";
    }

    /**
     * 波次集货完成
     *
     * @return 出库波次信息
     */
    @PostMapping(value = "/client/productWaveTask/collectionDone")
    public String productWaveTaskCollectionDone(@RequestBody @Validated(ExProductWaveTaskDto.CollectionDone.class) ExProductWaveTaskDto productWaveTaskDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, productWaveTaskDto, ExProductWaveTaskDto.CollectionDone.class);
        clientProductWaveTaskService.waveTaskCollectionDone(productWaveTaskDto);
        return "success";
    }
}
