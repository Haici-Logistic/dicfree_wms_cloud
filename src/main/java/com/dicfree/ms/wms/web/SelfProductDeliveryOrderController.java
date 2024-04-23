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
package com.dicfree.ms.wms.web;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.model.spring.security.LoginUserDto;
import cn.jzyunqi.common.utils.CurrentUserUtils;
import cn.jzyunqi.ms.SelfRestBaseController;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.excel.ProductDeliveryOrderExcelImport;
import com.dicfree.ms.wms.common.dto.ex.query.ExProductDeliveryOrderQueryDto;
import com.dicfree.ms.wms.service.ProductDeliveryOrderService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Portal - 个人用户 - 电商商品出库订单接口
 *
 * @author wiiyaya
 * @date 2023/10/28
 */
@RestController
@Validated
public class SelfProductDeliveryOrderController extends SelfRestBaseController {

    @Resource
    private ProductDeliveryOrderService productDeliveryOrderService;

    /**
     * 电商商品出库订单分页
     *
     * @param productDeliveryOrderQueryDto 查询条件
     * @param pageable                     分页参数|cn.jzyunqi.common.model.PageRequestDto
     * @return 电商商品出库订单信息
     */
    @PostMapping(value = "/self/productDeliveryOrder/page")
    public PageDto<ExProductDeliveryOrderDto> productDeliveryOrderPage(@ModelAttribute ExProductDeliveryOrderQueryDto productDeliveryOrderQueryDto, Pageable pageable) throws BusinessException {
        LoginUserDto currentUser = CurrentUserUtils.currentUser();
        productDeliveryOrderQueryDto.setSupplier(currentUser.getLoginUsername());
        PageDto<ExProductDeliveryOrderDto> productDeliveryOrderDtoPageDto = productDeliveryOrderService.productDeliveryOrderPage(productDeliveryOrderQueryDto, pageable);
        productDeliveryOrderDtoPageDto.getRows().forEach(productDeliveryOrderDto -> productDeliveryOrderDto.setCourier(null));
        return productDeliveryOrderDtoPageDto;
    }

    /**
     * 电商商品出库订单批量导入
     *
     * @param fileName 导入文件名
     */
    @PostMapping(value = "/self/productDeliveryOrder/addBatch")
    public List<Long> productDeliveryOrderAddBatch(@RequestParam @NotBlank String fileName) throws BusinessException {
        LoginUserDto currentUser = CurrentUserUtils.currentUser();
        List<ProductDeliveryOrderExcelImport> productDeliveryOrderList = productDeliveryOrderService.productDeliveryOrderAddBatchParse(currentUser.getLoginUsername(), fileName);
        return productDeliveryOrderService.productDeliveryOrderAddBatch(currentUser.getLoginUsername(), productDeliveryOrderList);
    }

    /**
     * 电商商品出库订单删除
     *
     * @param id 电商商品出库订单id
     */
    @PostMapping(value = "/self/productDeliveryOrder/delete")
    public void productDeliveryOrderDelete(@RequestParam @NotNull Long id) throws BusinessException {
        LoginUserDto currentUser = CurrentUserUtils.currentUser();
        productDeliveryOrderService.productDeliveryOrderDelete(currentUser.getLoginUsername(), id);
    }

    /**
     * 电商商品出库订单跟踪
     *
     * @param id 电商商品出库订单id
     * @return 电商商品出库订单信息
     */
    @PostMapping(value = "/self/productDeliveryOrder/trace")
    public ExProductDeliveryOrderDto productDeliveryOrderTrace(@RequestParam @NotNull Long id) throws BusinessException {
        LoginUserDto currentUser = CurrentUserUtils.currentUser();
        return productDeliveryOrderService.productDeliveryOrderTrace(currentUser.getLoginUsername(), id);
    }
}
