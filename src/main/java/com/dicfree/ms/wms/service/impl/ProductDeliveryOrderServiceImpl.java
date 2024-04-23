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

import cn.jzyunqi.autoconfigure.feature.properties.OssProperties;
import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.support.SpringContextUtils;
import cn.jzyunqi.common.support.spring.aop.event.EmitEvent;
import cn.jzyunqi.common.utils.POIUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.constant.WmsEmitConstant;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductDeliveryOrderItemDto;
import com.dicfree.ms.wms.common.dto.ex.ExProductSnDto;
import com.dicfree.ms.wms.common.dto.ex.ExTracingDto;
import com.dicfree.ms.wms.common.dto.ex.excel.ProductDeliveryOrderExcelImport;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ProductDeliveryOrderAddReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ProductDeliveryOrderTraceReq;
import com.dicfree.ms.wms.common.dto.ex.query.ExProductDeliveryOrderQueryDto;
import com.dicfree.ms.wms.common.enums.CourierType;
import com.dicfree.ms.wms.common.enums.OrderStatus;
import com.dicfree.ms.wms.common.enums.OrderType;
import com.dicfree.ms.wms.repository.jpa.dao.ProductDeliveryOrderDao;
import com.dicfree.ms.wms.repository.jpa.dao.ProductDeliveryOrderItemDao;
import com.dicfree.ms.wms.repository.jpa.dao.querydsl.ProductDeliveryOrderQry;
import com.dicfree.ms.wms.repository.jpa.entity.ProductDeliveryOrder;
import com.dicfree.ms.wms.repository.jpa.entity.ProductDeliveryOrderItem;
import com.dicfree.ms.wms.service.ProductDeliveryOrderService;
import com.dicfree.ms.wms.service.ProductSkuService;
import com.dicfree.ms.wms.service.ProductSnService;
import com.dicfree.ms.wms.service.client.CourierClient;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import jakarta.annotation.Resource;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wiiyaya
 * @date 2023/10/28
 */
@Service("productDeliveryOrderService")
public class ProductDeliveryOrderServiceImpl implements ProductDeliveryOrderService {

    @Resource
    private ProductDeliveryOrderDao productDeliveryOrderDao;

    @Resource
    private ProductDeliveryOrderItemDao productDeliveryOrderItemDao;

    @Resource
    private OssProperties ossProperties;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductSnService productSnService;

    @Resource
    private QingFlowClient qingFlowClient;

    @Resource
    private DateTimeProvider dateTimeProvider;

    @Override
    public PageDto<ExProductDeliveryOrderDto> productDeliveryOrderPage(ExProductDeliveryOrderQueryDto productDeliveryOrderQueryDto, Pageable pageable) {
        Page<ProductDeliveryOrder> productDeliveryOrderPage = productDeliveryOrderDao.findAll(ProductDeliveryOrderQry.searchProductDeliveryOrder(productDeliveryOrderQueryDto), ProductDeliveryOrderQry.searchProductDeliveryOrderOrder(pageable));

        List<ExProductDeliveryOrderDto> productDeliveryOrderDtoList = productDeliveryOrderPage.stream().map(productDeliveryOrder -> {
            ExProductDeliveryOrderDto productDeliveryOrderDto = new ExProductDeliveryOrderDto();
            productDeliveryOrderDto.setId(productDeliveryOrder.getId());
            productDeliveryOrderDto.setSupplier(productDeliveryOrder.getSupplier());
            productDeliveryOrderDto.setCreateTime(productDeliveryOrder.getCreateTime());
            productDeliveryOrderDto.setThirdOrderNo(productDeliveryOrder.getThirdOrderNo());
            productDeliveryOrderDto.setName(productDeliveryOrder.getName());
            productDeliveryOrderDto.setPhone1(productDeliveryOrder.getPhone1());
            productDeliveryOrderDto.setPhone2(productDeliveryOrder.getPhone2());
            productDeliveryOrderDto.setProvince(productDeliveryOrder.getProvince());
            productDeliveryOrderDto.setCity(productDeliveryOrder.getCity());
            productDeliveryOrderDto.setAddress(productDeliveryOrder.getAddress());
            productDeliveryOrderDto.setCourier(productDeliveryOrder.getCourier());
            productDeliveryOrderDto.setWaybill(productDeliveryOrder.getWaybill());
            productDeliveryOrderDto.setCod(productDeliveryOrder.getCod());
            productDeliveryOrderDto.setRemark(productDeliveryOrder.getRemark());
            productDeliveryOrderDto.setDispatched(productDeliveryOrder.getDispatched());

            List<ProductDeliveryOrderItem> productDeliveryOrderItemList = productDeliveryOrderItemDao.findAllByProductDeliveryOrderId(productDeliveryOrder.getId());
            List<ExProductDeliveryOrderItemDto> productDeliveryOrderItemDtoList = productDeliveryOrderItemList.stream().map(productDeliveryOrderItem -> {
                ExProductDeliveryOrderItemDto productDeliveryOrderItemDto = new ExProductDeliveryOrderItemDto();
                productDeliveryOrderItemDto.setProductSkuCode(productDeliveryOrderItem.getProductSkuCode());
                productDeliveryOrderItemDto.setTotalCount(productDeliveryOrderItem.getTotalCount());
                return productDeliveryOrderItemDto;
            }).toList();

            productDeliveryOrderDto.setProductDeliveryOrderItemList(productDeliveryOrderItemDtoList);

            return productDeliveryOrderDto;
        }).collect(Collectors.toList());

        return new PageDto<>(productDeliveryOrderDtoList, productDeliveryOrderPage.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void productDeliveryOrderDelete(String supplier, Long productDeliveryOrderId) throws BusinessException {
        ProductDeliveryOrder productDeliveryOrder = productDeliveryOrderDao.findByIdAndSupplier(productDeliveryOrderId, supplier).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_NOT_FOUND));
        if (productDeliveryOrder.getDispatched()) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_ALREADY_DISPATCH);
        }

        //先删除轻流数据
        qingFlowClient.deleteProductDeliveryOrder(productDeliveryOrder.getThirdOrderNo());

        //再删除本地数据
        productDeliveryOrderDao.delete(productDeliveryOrder);
        productDeliveryOrderItemDao.deleteByProductDeliveryOrderId(productDeliveryOrder.getId());
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Long productDeliveryOrderAdd(ExProductDeliveryOrderDto productDeliveryOrderDto) throws BusinessException {
        if (productDeliveryOrderDao.isThirdOrderNoExists(productDeliveryOrderDto.getThirdOrderNo())) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_THIRD_ORDER_NO_EXISTS, productDeliveryOrderDto.getThirdOrderNo());
        }
        int itemCount = productDeliveryOrderDto.getProductDeliveryOrderItemList().size();
        int totalCount = productDeliveryOrderDto.getProductDeliveryOrderItemList().stream().mapToInt(ExProductDeliveryOrderItemDto::getTotalCount).sum();
        OrderType orderType = itemCount == 1 ? (totalCount == 1 ? OrderType.OO : OrderType.OM) : OrderType.MM;

        ProductDeliveryOrder productDeliveryOrder = this.toProductDeliveryOrder(productDeliveryOrderDto.getSupplier(), productDeliveryOrderDto.getThirdOrderNo(), productDeliveryOrderDto.getProxy(), orderType);
        this.prepareOrderStatus(productDeliveryOrder, totalCount);
        this.prepareAddressInfo(productDeliveryOrder, productDeliveryOrderDto.getName(), productDeliveryOrderDto.getPhone1(), productDeliveryOrderDto.getPhone2(), productDeliveryOrderDto.getProvince(), productDeliveryOrderDto.getCity(), productDeliveryOrderDto.getAddress(), productDeliveryOrderDto.getCod(), productDeliveryOrderDto.getRemark());
        productDeliveryOrderDao.save(productDeliveryOrder);

        for (ExProductDeliveryOrderItemDto productDeliveryOrderItemDto : productDeliveryOrderDto.getProductDeliveryOrderItemList()) {
            String productSkuCode = productSkuService.getProductSkuCode(productDeliveryOrderItemDto.getProductCode(), productDeliveryOrder.getSupplier());
            ProductDeliveryOrderItem productDeliveryOrderItem = toProductDeliveryOrderItem(productDeliveryOrder.getId(), productDeliveryOrder.getProxy(), productSkuCode, productDeliveryOrderItemDto.getTotalCount());
            //存储前检查数量是否足够出库
            if (!productDeliveryOrder.getProxy()) {
                this.checkInboundCount(productDeliveryOrder.getThirdOrderNo(), productDeliveryOrderItem.getProductSkuCode(), productDeliveryOrderItem.getTotalCount());
            }

            productDeliveryOrderItemDao.save(productDeliveryOrderItem);
        }
        return productDeliveryOrder.getId();
    }

    @Override
    public List<ProductDeliveryOrderExcelImport> productDeliveryOrderAddBatchParse(String supplier, String fileName) throws BusinessException {
        String url = ossProperties.getAccessDomain() + "/" + fileName;
        List<ProductDeliveryOrderExcelImport> excelImportList;
        try {
            excelImportList = POIUtilPlus.Excel.readExcel(url, 0, 2, ProductDeliveryOrderExcelImport.class);
        } catch (Exception e) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_FILE_PARSER_ERROR, e);
        }

        //过滤掉空行
        excelImportList = excelImportList.stream().filter(excelImport -> StringUtilPlus.isNotBlank(excelImport.getProductCode())).toList();

        Set<String> productSkuCodeSet = new HashSet<>();
        Set<String> thirdOrderNoSet = new HashSet<>();
        for (ProductDeliveryOrderExcelImport excelImport : excelImportList) {
            if (StringUtilPlus.isNotBlank(excelImport.getThirdOrderNo())) {
                productSkuCodeSet.clear();
                if (!thirdOrderNoSet.add(excelImport.getThirdOrderNo())) {
                    throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_FILE_DUPLICATE_RECORD, excelImport.getRowNum(), excelImport.getThirdOrderNo(), excelImport.getProductCode());
                }
            }
            String productSkuCode = productSkuService.getProductSkuCode(excelImport.getProductCode(), supplier);
            //这里校验数据是否合法
            if (excelImport.getPcs() == null || excelImport.getPcs() > 999 || excelImport.getPcs() < 1) {
                throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_FILE_PCS_INVALID, excelImport.getRowNum(), excelImport.getThirdOrderNo(), excelImport.getProductCode());
            }
            //这里校验文件里的数据是否重复
            if (!productSkuCodeSet.add(productSkuCode)) {
                throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_FILE_DUPLICATE_RECORD, excelImport.getRowNum(), excelImport.getThirdOrderNo(), excelImport.getProductCode());
            }
            //这里校验productSkuCode在数据库是否存在
            if (productSkuService.productSkuInfoWithNull(productSkuCode) == null) {
                if (excelImport.getProductCode().equals(excelImport.getThirdOrderNo())) {
                    productSkuService.productSkuAddQuick(excelImport.getProductCode(), supplier);
                } else {
                    throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_FILE_SKU_NOT_FOUND, excelImport.getRowNum(), excelImport.getThirdOrderNo(), excelImport.getProductCode());
                }
            }
            //这里校验是否和数据库数据重复，如果ThirdOrderNo是空的，肯定就不会抛错
            if (productDeliveryOrderDao.isThirdOrderNoExists(excelImport.getThirdOrderNo())) {
                throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_FILE_EXISTS_RECORD, excelImport.getRowNum(), excelImport.getThirdOrderNo());
            }
        }
        return excelImportList;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    @EmitEvent(WmsEmitConstant.WMS_PRODUCT_DELIVERY_ORDER_SERVICE_ADD_BATCH)
    public List<Long> productDeliveryOrderAddBatch(String supplier, List<ProductDeliveryOrderExcelImport> productDeliveryOrderList) throws BusinessException {
        ProductDeliveryOrder preProductDeliveryOrder = null;
        List<Long> productDeliveryOrderIdList = new ArrayList<>();
        int preItemCount = 0;
        for (ProductDeliveryOrderExcelImport excelImport : productDeliveryOrderList) {
            if (StringUtilPlus.isNotBlank(excelImport.getThirdOrderNo())) {
                boolean proxy = excelImport.getProductCode().equals(excelImport.getThirdOrderNo());
                ProductDeliveryOrder productDeliveryOrder = this.toProductDeliveryOrder(supplier, excelImport.getThirdOrderNo(), proxy, null);
                this.prepareOrderStatus(productDeliveryOrder, 0);
                this.prepareAddressInfo(productDeliveryOrder, excelImport.getName(), excelImport.getPhone1(), excelImport.getPhone2(), excelImport.getProvince(), excelImport.getCity(), excelImport.getAddress(), excelImport.getCod(), excelImport.getRemark());
                productDeliveryOrderDao.save(productDeliveryOrder);

                productDeliveryOrderIdList.add(productDeliveryOrder.getId());

                preProductDeliveryOrder = productDeliveryOrder;
                preItemCount = 0;
            }

            if (preProductDeliveryOrder != null) {
                String productSkuCode = productSkuService.getProductSkuCode(excelImport.getProductCode(), supplier);
                ProductDeliveryOrderItem productDeliveryOrderItem = toProductDeliveryOrderItem(preProductDeliveryOrder.getId(), preProductDeliveryOrder.getProxy(), productSkuCode, excelImport.getPcs());

                //在存储前校验库存数量是否足够
                if (!preProductDeliveryOrder.getProxy()) {
                    this.checkInboundCount(preProductDeliveryOrder.getThirdOrderNo(), productDeliveryOrderItem.getProductSkuCode(), productDeliveryOrderItem.getTotalCount());
                }

                productDeliveryOrderItemDao.save(productDeliveryOrderItem);
                preItemCount = preItemCount + 1;

                //重新设置订单类型、计总和状态
                preProductDeliveryOrder.setTotalCount(preProductDeliveryOrder.getTotalCount() + excelImport.getPcs());
                preProductDeliveryOrder.setType(preItemCount == 1 ? (excelImport.getPcs() == 1 ? OrderType.OO : OrderType.OM) : OrderType.MM);
                this.prepareOrderStatus(preProductDeliveryOrder, preProductDeliveryOrder.getTotalCount());
                productDeliveryOrderDao.save(preProductDeliveryOrder);
            }
        }

        return productDeliveryOrderIdList;
    }

    @Override
    public ExProductDeliveryOrderDto productDeliveryOrderTrace(Long productDeliveryOrderId) throws BusinessException {
        ProductDeliveryOrder productDeliveryOrder = productDeliveryOrderDao.findById(productDeliveryOrderId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_NOT_FOUND));
        if (!productDeliveryOrder.getDispatched()) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_HAVE_NOT_DISPATCH);
        }

        List<ExTracingDto> tracingDtoList = qingFlowClient.queryWaybillTraceData(productDeliveryOrder.getWaybill());
        ExProductDeliveryOrderDto productDeliveryOrderDto = new ExProductDeliveryOrderDto();
        productDeliveryOrderDto.setCourier(productDeliveryOrder.getCourier());
        productDeliveryOrderDto.setWaybill(productDeliveryOrder.getWaybill());
        productDeliveryOrderDto.setTracingList(tracingDtoList);
        return productDeliveryOrderDto;
    }

    @Override
    public ExProductDeliveryOrderDto productDeliveryOrderTrace(String supplier, Long productDeliveryOrderId) throws BusinessException {
        ProductDeliveryOrder productDeliveryOrder = productDeliveryOrderDao.findByIdAndSupplier(productDeliveryOrderId, supplier).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_NOT_FOUND));
        if (!productDeliveryOrder.getDispatched()) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_HAVE_NOT_DISPATCH);
        }

        List<ExTracingDto> tracingDtoList = qingFlowClient.queryWaybillTraceData(productDeliveryOrder.getWaybill());
        ExProductDeliveryOrderDto productDeliveryOrderDto = new ExProductDeliveryOrderDto();
        productDeliveryOrderDto.setWaybill(productDeliveryOrder.getWaybill());
        productDeliveryOrderDto.setTracingList(tracingDtoList);
        return productDeliveryOrderDto;
    }

    @Override
    public void productDeliveryOrderTraceCallback(String courier, String waybill, Object callbackParams) throws BusinessException {
        ProductDeliveryOrder productDeliveryOrder = productDeliveryOrderDao.findByWaybill(waybill).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_NOT_FOUND));
        CourierType courierType = CourierType.of(productDeliveryOrder.getCourier());
        if (courierType == null) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_COURIER_NOT_SUPPORT);
        }

        CourierClient courierClient = SpringContextUtils.getBean(courierType.getClientClazz());
        List<ExTracingDto> tracingDtoList = courierClient.trackOrder(productDeliveryOrder.getWaybill(), callbackParams);

        //通知轻流
        for (ExTracingDto tracingDto : tracingDtoList) {
            ProductDeliveryOrderTraceReq request = new ProductDeliveryOrderTraceReq();
            request.setThirdDeliveryOrder(productDeliveryOrder.getThirdOrderNo());
            request.setCourier(productDeliveryOrder.getCourier());
            request.setWaybill(productDeliveryOrder.getWaybill());

            request.setStatusCode(tracingDto.getStatusCode());
            request.setStatus(tracingDto.getStatus());
            request.setTimeStamp(tracingDto.getTimeStamp());
            request.setScanLocation(tracingDto.getScanLocation());
            request.setShipmentDirection(tracingDto.getShipmentDirection());
            request.setDeliveryTo(tracingDto.getDeliveryTo());
            qingFlowClient.productDeliveryOrderTraceNotice(request);
        }
    }

    @Override
    public void productDeliveryOrderAddSync(List<Long> productDeliveryOrderIdList) throws BusinessException {
        List<ProductDeliveryOrder> productDeliveryOrderList = productDeliveryOrderDao.findAllById(productDeliveryOrderIdList);
        for (ProductDeliveryOrder productDeliveryOrder : productDeliveryOrderList) {
            //调用轻流生成快递单号
            ProductDeliveryOrderAddReq request = new ProductDeliveryOrderAddReq();
            request.setSupplier(productDeliveryOrder.getSupplier());
            request.setDeliveryOrder(productDeliveryOrder.getId());
            request.setThirdDeliveryOrder(productDeliveryOrder.getThirdOrderNo());
            request.setName(productDeliveryOrder.getName());
            request.setPhone1(productDeliveryOrder.getPhone1());
            request.setPhone2(productDeliveryOrder.getPhone2());
            request.setProvince(productDeliveryOrder.getProvince());
            request.setCity(productDeliveryOrder.getCity());
            request.setAddress(productDeliveryOrder.getAddress());
            request.setCod(productDeliveryOrder.getCod());
            request.setRemark(productDeliveryOrder.getRemark());

            List<ProductDeliveryOrderAddReq.Sku> skuList = productDeliveryOrderItemDao.findAllByProductDeliveryOrderId(productDeliveryOrder.getId()).stream().map(productDeliveryOrderItem -> {
                ProductDeliveryOrderAddReq.Sku sku = new ProductDeliveryOrderAddReq.Sku();
                sku.setProductCode(productDeliveryOrderItem.getProductSkuCode());
                sku.setPcs(productDeliveryOrderItem.getTotalCount());
                return sku;
            }).toList();
            request.setSkuList(skuList);
            qingFlowClient.productDeliveryOrderAddNotice(request);
        }
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void productDeliveryOrderWaybillEdit(String thirdOrderNo, String courier, String waybill, String waybillUrl) throws BusinessException {
        if (productDeliveryOrderDao.isWaybillExists(waybill)) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_WAYBILL_EXISTS);
        }

        ProductDeliveryOrder productDeliveryOrder = productDeliveryOrderDao.findByThirdOrderNo(thirdOrderNo).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_NOT_FOUND));
        if (productDeliveryOrder.getDispatched()) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_ALREADY_DISPATCH);
        }
        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));
        productDeliveryOrder.setDispatched(Boolean.TRUE);
        productDeliveryOrder.setDispatchTime(currTime);
        productDeliveryOrder.setCourier(courier);
        productDeliveryOrder.setWaybill(waybill);
        productDeliveryOrder.setWaybillUrl(waybillUrl);
        productDeliveryOrderDao.save(productDeliveryOrder);
    }

    @Override
    public ExProductDeliveryOrderDto productDeliveryOrderInfoWithNull(String waybill) {
        Optional<ProductDeliveryOrder> productDeliveryOrderOpt = productDeliveryOrderDao.findByWaybill(waybill);
        ExProductDeliveryOrderDto productDeliveryOrderDto = new ExProductDeliveryOrderDto();
        if(productDeliveryOrderOpt.isPresent()){
            productDeliveryOrderDto.setId(productDeliveryOrderOpt.get().getId());
            productDeliveryOrderDto.setWaybill(productDeliveryOrderOpt.get().getWaybill());
            return productDeliveryOrderDto;
        }else{
            ProductDeliveryOrder productDeliveryOrder = productDeliveryOrderDao.findByThirdOrderNo(waybill).orElse(new ProductDeliveryOrder());
            productDeliveryOrderDto.setId(productDeliveryOrder.getId());
            productDeliveryOrderDto.setWaybill(productDeliveryOrder.getThirdOrderNo());
            return productDeliveryOrderDto;
        }
    }

    @Override
    public List<ExProductDeliveryOrderItemDto> productDeliveryOrderItemUndoCalcList(Long productDeliveryOrderId) {
        List<ProductDeliveryOrderItem> productDeliveryOrderItemList = productDeliveryOrderItemDao.findAllVerifyUndoByProductDeliveryOrderId(productDeliveryOrderId);
        return productDeliveryOrderItemList.stream().map(this::toItemCalcDto).toList();
    }

    @Override
    public List<ExProductDeliveryOrderItemDto> productDeliveryOrderItemDoneCalcList(Long productDeliveryOrderId) {
        List<ProductDeliveryOrderItem> productDeliveryOrderItemList = productDeliveryOrderItemDao.findAllVerifyDoneByProductDeliveryOrderId(productDeliveryOrderId);
        return productDeliveryOrderItemList.stream().map(this::toItemCalcDto).toList();
    }

    @Override
    public List<ExProductSnDto> productDeliveryOrderItemProductSnUndoList(Long productDeliveryOrderId, Long productDeliveryOrderItemId) throws BusinessException {
        ProductDeliveryOrderItem productDeliveryOrderItem = productDeliveryOrderItemDao.findByIdAndProductDeliveryOrderId(productDeliveryOrderItemId, productDeliveryOrderId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_ITEM_NOT_FOUND));
        List<ExProductSnDto> productSnDtoList = productSnService.productSnUnVerifyList(productDeliveryOrderItem.getProductSkuCode());
        int remind = productDeliveryOrderItem.getTotalCount() - productDeliveryOrderItem.getVerifyCount();
        if (remind == 0 || productSnDtoList.isEmpty()) {
            return new ArrayList<>();
        }
        return productSnDtoList.subList(0, Math.min(productSnDtoList.size(), remind));
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void productDeliveryOrderProductSnVerify(Long productDeliveryOrderId, String productCode) throws BusinessException {
        //1. 查找productDeliveryOrder
        ProductDeliveryOrder productDeliveryOrder = productDeliveryOrderDao.findById(productDeliveryOrderId).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_NOT_FOUND));
        if (productDeliveryOrder.getSortingStatus() != OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_SORTING_HAVE_NOT_DONE);
        }
        if (productDeliveryOrder.getVerifyStatus() == OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_VERIFY_ALREADY_DONE);
        }

        //2. 查找productDeliveryOrderItem
        String productSkuCode = productSkuService.getProductSkuCode(productCode, productDeliveryOrder.getSupplier());
        ProductDeliveryOrderItem productDeliveryOrderItem = productDeliveryOrderItemDao.findByProductDeliveryOrderIdAndProductSkuCode(productDeliveryOrderId, productSkuCode)
                .orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_ITEM_SN_VERIFY_NOT_MATCH));

        //3. 将SN和出库订单挂钩
        String productSnCode = productSnService.productSnVerify(productDeliveryOrderItem.getId(), productSkuCode);

        //4 更新订单数量和状态
        this.updateProductDeliveryOrderCount(productDeliveryOrder);
        //5 更新订单详情数量和状态
        this.updateProductDeliveryOrderItemCount(productDeliveryOrderItem);
        //6. 通知轻流
        qingFlowClient.productSnOutboundNotice(productSnCode);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public ExProductDeliveryOrderDto productDeliveryOrderWaybillPrint(String waybill) throws BusinessException {
        ProductDeliveryOrder productDeliveryOrder = productDeliveryOrderDao.findByWaybillOrThirdOrderNo(waybill).orElseThrow(() -> new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_NOT_FOUND));
        if (!productDeliveryOrder.getDispatched()) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_HAVE_NOT_DISPATCH);
        }

        if (productDeliveryOrder.getVerifyStatus() != OrderStatus.DONE) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_VERIFY_HAVE_NOT_DONE);
        }
        ExProductDeliveryOrderDto productDeliveryOrderDto = new ExProductDeliveryOrderDto();
        productDeliveryOrderDto.setCourier(productDeliveryOrder.getCourier());
        productDeliveryOrderDto.setWaybill(productDeliveryOrder.getWaybill());
        productDeliveryOrderDto.setContentType("pdf");

        CourierType courierType = CourierType.of(productDeliveryOrder.getCourier());
        if (courierType == CourierType.C3X) {
            CourierClient courierClient = SpringContextUtils.getBean(courierType.getClientClazz());
            productDeliveryOrderDto.setContent(courierClient.genWaybillPdf(productDeliveryOrder.getWaybill()));
            productDeliveryOrderDto.setContentBase64(Boolean.TRUE);
        } else {
            productDeliveryOrderDto.setContent(productDeliveryOrder.getWaybillUrl());
            productDeliveryOrderDto.setContentBase64(Boolean.FALSE);
        }

        //保存相关的数据
        LocalDateTime currTime = LocalDateTime.from(dateTimeProvider.getNow().orElse(LocalDateTime.now()));
        productDeliveryOrder.setOutbound(Boolean.TRUE);
        productDeliveryOrder.setOutboundTime(currTime);
        productDeliveryOrderDao.save(productDeliveryOrder);

        return productDeliveryOrderDto;
    }

    /**
     * DB数据转DTO数据
     *
     * @param productDeliveryOrderItem DB数据
     * @return DTO数据
     */
    private ExProductDeliveryOrderItemDto toItemCalcDto(ProductDeliveryOrderItem productDeliveryOrderItem) {
        ExProductDeliveryOrderItemDto productDeliveryOrderItemDto = new ExProductDeliveryOrderItemDto();
        productDeliveryOrderItemDto.setId(productDeliveryOrderItem.getId());
        productDeliveryOrderItemDto.setProductSkuCode(productDeliveryOrderItem.getProductSkuCode());
        productDeliveryOrderItemDto.setVerifyCount(productDeliveryOrderItem.getVerifyCount());
        productDeliveryOrderItemDto.setTotalCount(productDeliveryOrderItem.getTotalCount());
        return productDeliveryOrderItemDto;
    }

    /**
     * 更新订单统计信息
     *
     * @param productDeliveryOrder 订单
     */
    private void updateProductDeliveryOrderCount(ProductDeliveryOrder productDeliveryOrder) {
        productDeliveryOrder.setVerifyCount(productDeliveryOrder.getVerifyCount() + 1);
        if (productDeliveryOrder.getVerifyCount().equals(productDeliveryOrder.getTotalCount())) {
            productDeliveryOrder.setVerifyStatus(OrderStatus.DONE);
        } else {
            productDeliveryOrder.setVerifyStatus(OrderStatus.IN_PROCESSING);
        }
        productDeliveryOrderDao.save(productDeliveryOrder);
    }

    /**
     * 更新订单详情统计信息
     *
     * @param productDeliveryOrderItem 订单
     */
    private void updateProductDeliveryOrderItemCount(ProductDeliveryOrderItem productDeliveryOrderItem) {
        productDeliveryOrderItem.setVerifyCount(productDeliveryOrderItem.getVerifyCount() + 1);
        if (productDeliveryOrderItem.getVerifyCount().equals(productDeliveryOrderItem.getTotalCount())) {
            productDeliveryOrderItem.setVerifyStatus(OrderStatus.DONE);
        } else {
            productDeliveryOrderItem.setVerifyStatus(OrderStatus.IN_PROCESSING);
        }
        productDeliveryOrderItemDao.save(productDeliveryOrderItem);
    }

    /**
     * 校验入库数量是否足够
     *
     * @param thirdOrderNo   第三方订单号
     * @param productSkuCode 商品编码
     * @param needOut        待出库
     */
    private void checkInboundCount(String thirdOrderNo, String productSkuCode, Integer needOut) throws BusinessException {
        //1. 查询在库数量(在货架上)
        List<ExProductSnDto> productSnDtoList = productSnService.productSnOnShelfList(productSkuCode);
        //2. 查询其它订单待出库数量
        List<ProductDeliveryOrderItem> notDoneList = productDeliveryOrderItemDao.findAllNotVerify(productSkuCode);
        //3. 计算并比较(这里比较已经下架的数量)
        int wait = notDoneList.stream().mapToInt(value -> value.getTotalCount() - value.getOffShelfCount()).sum();
        int remind = productSnDtoList.size() - wait - needOut;
        if (remind < 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_PRODUCT_DELIVERY_ORDER_ITEM_SN_NOT_ENOUGH, thirdOrderNo, productSkuCode, productSnDtoList.size(), wait);
        }
    }

    /**
     * 转换成订单对象
     *
     * @param supplier     客户
     * @param thirdOrderNo 第三方订单号
     * @param proxy        是否代理单
     * @return 订单对象
     */
    private ProductDeliveryOrder toProductDeliveryOrder(String supplier, String thirdOrderNo, Boolean proxy, OrderType type) {
        ProductDeliveryOrder productDeliveryOrder = new ProductDeliveryOrder();
        productDeliveryOrder.setSupplier(supplier);
        productDeliveryOrder.setType(type);
        productDeliveryOrder.setThirdOrderNo(thirdOrderNo);
        productDeliveryOrder.setProxy(proxy);
        productDeliveryOrder.setDispatched(Boolean.FALSE);
        productDeliveryOrder.setDispatchTime(null);
        productDeliveryOrder.setOutbound(Boolean.FALSE);
        productDeliveryOrder.setOutboundTime(null);
        productDeliveryOrder.setCourier(null);
        productDeliveryOrder.setWaybill(null);
        productDeliveryOrder.setWaybillUrl(null);
        return productDeliveryOrder;
    }

    /**
     * 准备订单状态
     *
     * @param productDeliveryOrder 订单
     * @param totalCount           总数量
     */
    private void prepareOrderStatus(ProductDeliveryOrder productDeliveryOrder, Integer totalCount) {
        productDeliveryOrder.setTotalCount(totalCount);
        if (productDeliveryOrder.getProxy()) {
            productDeliveryOrder.setOffShelfStatus(OrderStatus.DONE);
            productDeliveryOrder.setOffShelfCount(totalCount);
            productDeliveryOrder.setSortingStatus(OrderStatus.DONE);
            productDeliveryOrder.setSortingCount(totalCount);
            productDeliveryOrder.setBasketNo(StringUtilPlus.HYPHEN);
            productDeliveryOrder.setVerifyStatus(OrderStatus.DONE);
            productDeliveryOrder.setVerifyCount(totalCount);
        } else {
            productDeliveryOrder.setOffShelfStatus(OrderStatus.PENDING);
            productDeliveryOrder.setOffShelfCount(0);
            productDeliveryOrder.setSortingStatus(OrderStatus.PENDING);
            productDeliveryOrder.setSortingCount(0);
            productDeliveryOrder.setBasketNo(null);
            productDeliveryOrder.setVerifyStatus(OrderStatus.PENDING);
            productDeliveryOrder.setVerifyCount(0);
        }
    }

    /**
     * 准备地址信息
     *
     * @param productDeliveryOrder 订单
     * @param name                 姓名
     * @param phone1               手机号1
     * @param phone2               手机号2
     * @param province             省份
     * @param city                 城市
     * @param address              地址
     * @param cod                  代收金额
     * @param remark               备注
     */
    private void prepareAddressInfo(ProductDeliveryOrder productDeliveryOrder, String name, String phone1, String phone2, String province, String city, String address, BigDecimal cod, String remark) {
        productDeliveryOrder.setName(name);
        productDeliveryOrder.setPhone1(phone1);
        productDeliveryOrder.setPhone2(phone2);
        productDeliveryOrder.setProvince(province);
        productDeliveryOrder.setCity(city);
        productDeliveryOrder.setAddress(address);
        productDeliveryOrder.setCod(cod);
        productDeliveryOrder.setRemark(remark);
    }

    /**
     * 转换成订单详情对象
     *
     * @param productDeliveryOrderId 订单ID
     * @param productSkuCode         商品编码
     * @param totalCount             总数量
     * @return 订单详情对象
     */
    private ProductDeliveryOrderItem toProductDeliveryOrderItem(Long productDeliveryOrderId, Boolean proxy, String productSkuCode, Integer totalCount) {
        ProductDeliveryOrderItem productDeliveryOrderItem = new ProductDeliveryOrderItem();
        productDeliveryOrderItem.setProductDeliveryOrderId(productDeliveryOrderId);
        productDeliveryOrderItem.setProductSkuCode(productSkuCode);
        productDeliveryOrderItem.setTotalCount(totalCount);
        if(proxy){
            productDeliveryOrderItem.setOffShelfStatus(OrderStatus.DONE);
            productDeliveryOrderItem.setOffShelfCount(totalCount);
            productDeliveryOrderItem.setSortingStatus(OrderStatus.DONE);
            productDeliveryOrderItem.setSortingCount(totalCount);
            productDeliveryOrderItem.setVerifyStatus(OrderStatus.DONE);
            productDeliveryOrderItem.setVerifyCount(totalCount);
        }else{
            productDeliveryOrderItem.setOffShelfStatus(OrderStatus.PENDING);
            productDeliveryOrderItem.setOffShelfCount(0);
            productDeliveryOrderItem.setSortingStatus(OrderStatus.PENDING);
            productDeliveryOrderItem.setSortingCount(0);
            productDeliveryOrderItem.setVerifyStatus(OrderStatus.PENDING);
            productDeliveryOrderItem.setVerifyCount(0);
        }
        return productDeliveryOrderItem;
    }
}
