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
import cn.jzyunqi.common.support.spring.BindingResultHelper;
import com.dicfree.ms.ClientRestBaseController;
import com.dicfree.ms.mct.common.dto.ex.ExMemberAddressDto;
import com.dicfree.ms.si.service.ClientMemberAddressService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 外部系统 - 小程序用户地址接口
 *
 * @author wiiyaya
 * @date 2023/11/26
 */
@RestController
@Validated
public class ClientMemberAddressController extends ClientRestBaseController {

    @Resource
    private ClientMemberAddressService clientMemberAddressService;

    /**
     * 会员地址列表
     *
     * @param unionId 微信unionId
     * @return 地址列表
     */
    @PostMapping(value = "/client/memberAddress/list")
    public List<ExMemberAddressDto> memberAddressList(@RequestParam @NotBlank String unionId) throws BusinessException {
        return clientMemberAddressService.memberAddressList(unionId);
    }

    /**
     * 会员地址新增
     *
     * @param memberAddressDto 会员地址信息
     * @param bindingResult    校验信息
     */
    @PostMapping(value = "/client/memberAddress/add")
    public String memberAddressAdd(@RequestParam @NotBlank String unionId, @RequestBody @Validated(ExMemberAddressDto.ClientAdd.class) ExMemberAddressDto memberAddressDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, memberAddressDto, ExMemberAddressDto.ClientAdd.class);
        clientMemberAddressService.memberAddressAdd(unionId, memberAddressDto);
        return "success";
    }

    /**
     * 会员地址修改
     *
     * @param memberAddressDto 会员地址信息
     * @param bindingResult    校验信息
     */
    @PostMapping(value = "/client/memberAddress/edit")
    public String memberAddressEdit(@RequestParam @NotBlank String unionId, @RequestBody @Validated(ExMemberAddressDto.ClientEdit.class) ExMemberAddressDto memberAddressDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, memberAddressDto, ExMemberAddressDto.ClientEdit.class);
        clientMemberAddressService.memberAddressEdit(unionId, memberAddressDto);
        return "success";
    }

    /**
     * 会员地址信息
     *
     * @param unionId   微信unionId
     * @param id 地址id
     * @return 地址信息
     */
    @PostMapping(value = "/client/memberAddress/info")
    public ExMemberAddressDto memberAddressEdit(@RequestParam @NotBlank String unionId, @RequestParam @NotNull Long id) throws BusinessException {
        return clientMemberAddressService.memberAddressInfo(unionId, id);
    }

    /**
     * 查询是否有默认地址
     *
     * @param unionId 微信unionId
     */
    @PostMapping(value = "/client/memberAddress/hasDefaultOne")
    public Boolean memberAddressHasDefaultOne(@RequestParam String unionId) throws BusinessException {
        return clientMemberAddressService.memberAddressHasDefaultOne(unionId);
    }

    /**
     * 设置默认地址
     *
     * @param unionId 微信unionId
     * @param id 地址id
     */
    @PostMapping(value = "/client/memberAddress/setDefaultOne")
    public String memberAddressSetDefaultOne(@RequestParam @NotBlank String unionId, @RequestParam @NotNull Long id) throws BusinessException {
        clientMemberAddressService.memberAddressSetDefaultOne(unionId, id);
        return "success";
    }

    /**
     * 删除会员地址
     *
     * @param unionId   微信unionId
     * @param id 地址id
     */
    @PostMapping(value = "/client/memberAddress/delete")
    public String memberAddressDelete(@RequestParam @NotBlank String unionId, @RequestParam @NotNull Long id) throws BusinessException {
        clientMemberAddressService.memberAddressDelete(unionId, id);
        return "success";
    }
}
