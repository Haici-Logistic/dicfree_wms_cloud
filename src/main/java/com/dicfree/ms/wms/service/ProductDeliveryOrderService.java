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
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import com.dicfree.ms.wms.common.dto.ex.excel.ProductDeliveryOrderExcelImport;
import com.dicfree.ms.wms.common.dto.ex.query.ExProductDeliveryOrderQueryDto;
import com.dicfree.ms.wms.common.enums.CourierType;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2023/10/28
 */
public interface ProductDeliveryOrderService {

    /**
     * 电商商品出库订单分页
     *
     * @param productDeliveryOrderQueryDto 查询条件
     * @param pageable                     分页条件
     * @return 结果
     */
    PageDto<ExProductDeliveryOrderDto> productDeliveryOrderPage(ExProductDeliveryOrderQueryDto productDeliveryOrderQueryDto, Pageable pageable);

    /**
     * 电商商品出库订单删除
     *
     * @param supplier               客户
     * @param productDeliveryOrderId 电商商品出库订单id
     */
    void productDeliveryOrderDelete(String supplier, Long productDeliveryOrderId) throws BusinessException;

    /**
     * 出库订单新增
     *
     * @param productDeliveryOrderDto 出库订单信息
     * @return 出库订单
     */
    Long productDeliveryOrderAdd(ExProductDeliveryOrderDto productDeliveryOrderDto) throws BusinessException;

    /**
     * 电商商品出库订单批量导入解析
     *
     * @param supplier 供应商
     * @param fileName 文件名
     * @return 电商商品出库订单信息
     */
    List<ProductDeliveryOrderExcelImport> productDeliveryOrderAddBatchParse(String supplier, String fileName) throws BusinessException;

    /**
     * 电商商品出库订单批量导入
     *
     * @param productDeliveryOrderList 电商商品出库订单列表
     */
    List<Long> productDeliveryOrderAddBatch(String supplier, List<ProductDeliveryOrderExcelImport> productDeliveryOrderList) throws BusinessException;

    /**
     * 电商商品出库订单跟踪
     *
     * @param productDeliveryOrderId 电商商品出库订单id
     * @return 电商商品出库订单跟踪信息
     */
    ExProductDeliveryOrderDto productDeliveryOrderTrace(Long productDeliveryOrderId) throws BusinessException;

    /**
     * 电商商品出库订单跟踪
     *
     * @param supplier               客户
     * @param productDeliveryOrderId 电商商品出库订单id
     * @return 电商商品出库订单跟踪信息
     */
    ExProductDeliveryOrderDto productDeliveryOrderTrace(String supplier, Long productDeliveryOrderId) throws BusinessException;

    /**
     * 电商商品出库订单跟踪
     *
     * @param courier 快递公司
     * @param waybill 运单号
     */
    void productDeliveryOrderTraceCallback(String courier, String waybill, Object callbackParams) throws BusinessException;

    /**
     * 电商商品出库订单批量同步至轻流
     *
     * @param productDeliveryOrderIdList 电商商品出库订单id列表
     */
    void productDeliveryOrderAddSync(List<Long> productDeliveryOrderIdList) throws BusinessException;

    /**
     * 发货单物流信息更新
     *
     * @param thirdOrderNo 三方订单号
     * @param courier      快递公司
     * @param waybill      运单号
     * @param waybillUrl   运单PDF地址
     */
    void productDeliveryOrderWaybillEdit(String thirdOrderNo, String courier, String waybill, String waybillUrl) throws BusinessException;

    /**
     * 电商商品出库订单信息
     *
     * @param waybill 运单号
     */
    ExProductDeliveryOrderDto productDeliveryOrderInfoWithNull(String waybill);

    /**
     * 出库订单未完成明细列表
     *
     * @param productDeliveryOrderId 电商商品出库订单id
     * @return 电商商品出库订单明细信息
     */
    List<ExProductDeliveryOrderItemDto> productDeliveryOrderItemUndoCalcList(Long productDeliveryOrderId);

    /**
     * 出库订单已完成明细列表
     *
     * @param productDeliveryOrderId 电商商品出库订单id
     * @return 电商商品出库订单明细信息
     */
    List<ExProductDeliveryOrderItemDto> productDeliveryOrderItemDoneCalcList(Long productDeliveryOrderId);

    /**
     * 出库订单商品明细
     *
     * @param productDeliveryOrderId     电商商品出库订单id
     * @param productDeliveryOrderItemId 电商商品出库订单明细id
     * @return 商品明细
     */
    List<ExProductSnDto> productDeliveryOrderItemProductSnUndoList(Long productDeliveryOrderId, Long productDeliveryOrderItemId) throws BusinessException;

    /**
     * 商品出库校验
     *
     * @param productDeliveryOrderId 电商商品出库订单id
     * @param productCode            商品编码
     */
    void productDeliveryOrderProductSnVerify(Long productDeliveryOrderId, String productCode) throws BusinessException;

    /**
     * 出库订单运单PDF地址
     *
     * @param waybill 运单号
     * @return 运单PDF地址
     */
    ExProductDeliveryOrderDto productDeliveryOrderWaybillPrint(String waybill) throws BusinessException;
}
