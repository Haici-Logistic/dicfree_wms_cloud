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
import com.dicfree.ms.mct.common.dto.ex.ExMemberDto;
import com.dicfree.ms.si.service.ClientMemberService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 外部系统 - 小程序用户接口
 *
 * @author wiiyaya
 * @date 2023/11/14
 */
@RestController
@Validated
public class ClientMemberController extends ClientRestBaseController {

    @Resource
    private ClientMemberService clientMemberService;

    /**
     * 直接创建小程序用户
     *
     * @param unionId 微信unionId
     * @param openId  微信openId
     * @return 成功
     */
    @PostMapping(value = "/client/member/add")
    public String memberAdd(@RequestParam @NotBlank String unionId, @RequestParam @NotBlank String openId) throws BusinessException {
        clientMemberService.memberAdd(unionId, openId);
        return "success";
    }

    /**
     * 修改会员信息
     *
     * @param memberDto 会员信息
     * @return 成功
     */
    @PostMapping(value = "/client/member/edit")
    public String memberEdit(@RequestParam @NotBlank String unionId, @RequestBody @Validated(ExMemberDto.ClientEdit.class) ExMemberDto memberDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult, memberDto, ExMemberDto.ClientEdit.class);
        clientMemberService.memberEdit(unionId, memberDto);
        return "success";
    }

    /**
     * 查看会员信息
     *
     * @param unionId 微信unionId
     * @return 成功
     */
    @PostMapping(value = "/client/member/info")
    public ExMemberDto memberInfo(@RequestParam @NotBlank String unionId) throws BusinessException {
        return clientMemberService.memberInfo(unionId);
    }
}
