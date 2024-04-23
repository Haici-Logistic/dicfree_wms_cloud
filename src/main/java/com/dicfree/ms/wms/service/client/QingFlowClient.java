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
package com.dicfree.ms.wms.service.client;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.utils.CollectionUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.ExTracingDto;
import com.dicfree.ms.wms.common.dto.ex.qingflow.AppRecordData;
import com.dicfree.ms.wms.common.dto.ex.qingflow.AppRecordReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.BoxLocationReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.BoxSnReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.CityData;
import com.dicfree.ms.wms.common.dto.ex.qingflow.CourierData;
import com.dicfree.ms.wms.common.dto.ex.qingflow.DeliveryAddressData;
import com.dicfree.ms.wms.common.dto.ex.qingflow.LocationData;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ProductDeliveryOrderAddReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ProductDeliveryOrderTraceReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ProductSnOffShelfReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ProductSnOnShelfReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ProductSnReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ProductSkuWeightData;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ProductWaveTaskWeighingReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ProvinceData;
import com.dicfree.ms.wms.common.dto.ex.qingflow.QingFlowCommonRsp;
import com.dicfree.ms.wms.common.dto.ex.qingflow.QingFlowData;
import com.dicfree.ms.wms.common.dto.ex.qingflow.QingFlowField;
import com.dicfree.ms.wms.common.dto.ex.qingflow.QingFlowPageData;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ThirdBoxArrivalOrderAddReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ThirdBoxDeliveryOrderAddReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ThirdTransportOrderAddReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.ThirdTransportOrderTraceReq;
import com.dicfree.ms.wms.common.dto.ex.qingflow.TracingData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2023/7/19.
 */
@Component
@Slf4j
public class QingFlowClient {

    @Value("${df.feature.qingflow.box-location-lock-notice-path:}")
    private String boxLocationLockNoticePath;

    @Value("${df.feature.qingflow.box-whole-location-assign-notice-path:}")
    private String boxWholeLocationAssignNoticePath;

    @Value("${df.feature.qingflow.box-bulk-location-assign-notice-path:}")
    private String boxBulkLocationAssignNoticePath;

    @Value("${df.feature.qingflow.box-sn-inbound-notice-path:}")
    private String boxSnInboundNoticePath;

    @Value("${df.feature.qingflow.box-sn-outbound-notice-path:}")
    private String boxSnOutboundNoticePath;

    @Value("${df.feature.qingflow.box-sn-creation-notice-path:}")
    private String boxSnCreationNoticePath;

    @Value("${df.feature.qingflow.product-sn-inbound-notice-path:}")
    private String productSnInboundNoticePath;

    @Value("${df.feature.qingflow.product-sn-outbound-notice-path:}")
    private String productSnOutboundNoticePath;

    @Value("${df.feature.qingflow.product-sn-creation-notice-path:}")
    private String productSnCreationNoticePath;

    @Value("${df.feature.qingflow.product-sn-on-shelf-notice-path:}")
    private String productSnOnShelfNoticePath;

    @Value("${df.feature.qingflow.product-sn-off-shelf-notice-path:}")
    private String productSnOffShelfNoticePath;

    @Value("${df.feature.qingflow.product-delivery-order-add-notice-path:}")
    private String productDeliveryOrderAddNoticePath;

    @Value("${df.feature.qingflow.product-delivery-order-trace-notice-path:}")
    private String productDeliveryOrderTraceNoticePath;

    @Value("${df.feature.qingflow.product-wave-task-weighing-notice-path:}")
    private String productWaveTaskWeighingNoticePath;

    @Value("${df.feature.qingflow.third-box-arrival-order-add-notice-path:}")
    private String thirdBoxArrivalOrderAddNoticePath;

    @Value("${df.feature.qingflow.third-box-delivery-order-add-notice-path:}")
    private String thirdBoxDeliveryOrderAddNoticePath;

    @Value("${df.feature.qingflow.third-transport-order-add-notice-path:}")
    private String thirdTransportOrderAddNoticePath;

    @Value("${df.feature.qingflow.third-transport-order-trace-notice-path:}")
    private String thirdTransportOrderTraceNoticePath;

    @Value("${df.feature.qingflow.third-courier-fast-line-trace-notice-path:}")
    private String thirdCourierFastLineTraceNoticePath;

    @Value("${df.feature.qingflow.open-api-token:}")
    private String accessToken;

    @Resource
    private QingFlowClient.Proxy qingFlowClientProxy;

    @Resource
    private QingFlowClient.ApiProxy qingFlowClientApiProxy;

    /**
     * 通知轻流锁定库位
     *
     * @param request 请求
     */
    public void boxLocationLockNotice(BoxLocationReq request) throws BusinessException {
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.boxLocationLockNotice(boxLocationLockNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_LOCATION_LOCK_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow boxLocationLockNotice to [{}] with request[{}] success and response is: [{}]===", boxLocationLockNoticePath, request, response.getResult());
        }
    }

    /**
     * 整库库位规划
     *
     * @param uniqueNo     订单号
     * @param locationCode 库位编码
     */
    public void boxWholeLocationAssignNotice(String uniqueNo, String locationCode) throws BusinessException {
        BoxLocationReq request = new BoxLocationReq();
        request.setUniqueBoxArrivalOrderNo(uniqueNo);
        request.setLocationCode(locationCode);
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.boxWholeLocationAssignNotice(boxWholeLocationAssignNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_WHOLE_LOCATION_ASSIGN_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow boxWholeLocationAssignNotice to [{}] with request[{}] success and response is: [{}]===", boxWholeLocationAssignNoticePath, request, response.getResult());
        }
    }

    /**
     * 混库库位规划
     *
     * @param uniqueNo   订单号
     * @param boxSkuCode 箱号
     */
    public void boxBulkLocationAssignNotice(String uniqueNo, String boxSkuCode) throws BusinessException {
        BoxLocationReq request = new BoxLocationReq();
        request.setUniqueBoxArrivalOrderNo(uniqueNo);
        request.setBskuCode(boxSkuCode);
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.boxBulkLocationAssignNotice(boxBulkLocationAssignNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_BULK_LOCATION_ASSIGN_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow boxBulkLocationAssignNotice to [{}] with request[{}] success and response is: [{}]===", boxBulkLocationAssignNoticePath, request, response.getResult());
        }
    }

    /**
     * 通知轻流入库
     *
     * @param boxSnCode 入库数据
     * @throws BusinessException 通知失败
     */
    public void boxSnInboundNotice(String boxSnCode) throws BusinessException {
        BoxSnReq request = new BoxSnReq();
        request.setBsnCode(boxSnCode);
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.boxSnInboundNotice(boxSnInboundNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_INBOUND_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow boxSnInboundNotice to [{}] with request[{}] success and response is: [{}]===", boxSnInboundNoticePath, request, response.getResult());
        }
    }

    /**
     * 通知轻流出库
     *
     * @param boxSnCode 出库数据
     * @throws BusinessException 通知失败
     */
    public void boxSnOutboundNotice(String boxSnCode) throws BusinessException {
        BoxSnReq request = new BoxSnReq();
        request.setBsnCode(boxSnCode);
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.boxSnOutboundNotice(boxSnOutboundNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_OUTBOUND_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow boxSnOutboundNotice to [{}] with request[{}] success and response is: [{}]===", boxSnOutboundNoticePath, request, response.getResult());
        }
    }

    /**
     * 通知轻流序列箱创建
     *
     * @param uniqueNo          订单号
     * @param boxSkuCode        箱号
     * @param boxSnCode         序列箱号
     * @param serialNo          序号
     * @param pcs               数量
     * @param supplierBoxSnCode 客户编号
     * @throws BusinessException 通知失败
     */
    public void boxSnCreationNotice(String uniqueNo, String boxSkuCode, String boxSnCode, String serialNo, Integer pcs, String supplierBoxSnCode) throws BusinessException {
        BoxSnReq request = new BoxSnReq();
        request.setUniqueBoxArrivalOrderNo(uniqueNo);
        request.setBskuCode(boxSkuCode);
        request.setBsnCode(boxSnCode);
        request.setSerialNo(serialNo);
        request.setPcs(pcs);
        request.setSupplierBsnCode(supplierBoxSnCode);
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.boxSnCreationNotice(boxSnCreationNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_SN_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow boxSnNotice success to [{}] with request[{}] and response is: [{}]===", boxSnCreationNoticePath, request, response.getResult());
        }
    }

    /**
     * 通知轻流入库
     *
     * @param snCode  序列商品编号
     * @param weight  重量
     * @param length  长
     * @param width   宽
     * @param height  高
     * @param quality 质检
     */
    public void productSnInboundNotice(String snCode, Float weight, Integer length, Integer width, Integer height, Boolean quality) throws BusinessException {
        ProductSnReq request = new ProductSnReq();
        request.setSnCode(snCode);
        request.setWeight(weight);
        request.setLength(length);
        request.setWidth(width);
        request.setHeight(height);
        request.setQuality(quality);

        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.productSnInboundNotice(productSnInboundNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_PRODUCT_SN_INBOUND_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow productSnInboundNotice to [{}] with request[{}] success and response is: [{}]===", productSnInboundNoticePath, request, response.getResult());
        }
    }

    /**
     * 通知轻流出库
     *
     * @param snCode 序列商品编号
     */
    public void productSnOutboundNotice(String snCode) throws BusinessException {
        ProductSnReq request = new ProductSnReq();
        request.setSnCode(snCode);

        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.productSnOutboundNotice(productSnOutboundNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_PRODUCT_SN_OUTBOUND_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow productSnOutboundNotice to [{}] with request[{}] success and response is: [{}]===", productSnOutboundNoticePath, request, response.getResult());
        }
    }

    /**
     * 通知轻流序列箱创建
     *
     * @param thirdArrivalOrderNo 第三方订单号
     * @param skuCode             箱号
     * @param snCode              序列箱号
     * @param serialNo            序号
     * @param pcs                 数量
     */
    public void productSnCreationNotice(String thirdArrivalOrderNo, String skuCode, String snCode, String serialNo, Integer pcs) throws BusinessException {
        ProductSnReq request = new ProductSnReq();
        request.setThirdArrivalOrderNo(thirdArrivalOrderNo);
        request.setSkuCode(skuCode);
        request.setSnCode(snCode);
        request.setSerialNo(serialNo);
        request.setPcs(pcs);
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.productSnCreationNotice(productSnCreationNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_SN_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow productSnNotice to [{}] with request[{}] success and response is: [{}]===", productSnCreationNoticePath, request, response.getResult());
        }
    }

    /**
     * 通知轻流序列商品上架
     *
     * @param snCode   序列箱号
     * @param location 货架号
     */
    public void productSnOnShelfNotice(String snCode, String location) throws BusinessException {
        ProductSnOnShelfReq request = new ProductSnOnShelfReq();
        request.setSnCode(snCode);
        request.setLocation(location);
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.productSnOnShelfNotice(productSnOnShelfNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_SN_ON_SHELF_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow productSnOnShelfNotice to [{}] with request[{}] success and response is: [{}]", productSnOnShelfNoticePath, request, response.getResult());
        }
    }

    /**
     * 通知轻流序列商品下架
     *
     * @param snCode 序列箱号
     */
    public void productSnOffShelfNotice(String snCode) throws BusinessException {
        ProductSnOffShelfReq request = new ProductSnOffShelfReq();
        request.setSnCode(snCode);
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.productSnOffShelfNotice(productSnOffShelfNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_SN_OFF_SHELF_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow productSnOffShelfNotice to [{}] with request[{}] success and response is: [{}]", productSnOffShelfNoticePath, request, response.getResult());
        }
    }

    /**
     * 通知轻流出库订单新增
     *
     * @param request 派单请求
     */
    public void productDeliveryOrderAddNotice(ProductDeliveryOrderAddReq request) throws BusinessException {
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.productDeliveryOrderAddNotice(productDeliveryOrderAddNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_SN_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow productDeliveryOrderAddNotice to [{}] with request[{}] success and response is: [{}]===", productDeliveryOrderAddNoticePath, request, response.getResult());
        }
    }

    /**
     * 通知轻流出库订单轨迹
     *
     * @param request 轨迹请求
     */
    public void productDeliveryOrderTraceNotice(ProductDeliveryOrderTraceReq request) throws BusinessException {
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.productDeliveryOrderTraceNotice(productDeliveryOrderTraceNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_TRACE_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow productDeliveryOrderTraceNotice to [{}] with request[{}] success and response is: [{}]===", productDeliveryOrderTraceNoticePath, request, response.getResult());
        }
    }

    /**
     * 通知轻流波次任务称重
     *
     * @param waybill 运单号
     * @param weight 重量
     */
    public void productWaveTaskWeighingNotice(String waybill, Float weight) throws BusinessException {
        ProductWaveTaskWeighingReq productWaveTaskWeighingReq = new ProductWaveTaskWeighingReq();
        productWaveTaskWeighingReq.setWaybill(waybill);
        productWaveTaskWeighingReq.setWeight(weight);
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.productWaveTaskWeighingNotice(productWaveTaskWeighingNoticePath, productWaveTaskWeighingReq);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_WAVE_TASK_WEIGHING_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow productWaveTaskWeighingNotice to [{}] with request[{}] success and response is: [{}]===", productWaveTaskWeighingNoticePath, waybill, response.getResult());
        }
    }

    /**
     * 第三方入库订单创建通知
     */
    public void thirdBoxArrivalOrderAddNotice(ThirdBoxArrivalOrderAddReq request) throws BusinessException {
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.thirdBoxArrivalOrderAddNotice(thirdBoxArrivalOrderAddNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_ARRIVAL_ORDER_ADD_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow thirdBoxArrivalOrderAddNotice to [{}] with request[{}] success and response is: [{}]===", thirdBoxArrivalOrderAddNoticePath, request, response.getResult());
        }
    }

    /**
     * 第三方出库订单创建通知
     */
    public void thirdBoxDeliveryOrderAddNotice(ThirdBoxDeliveryOrderAddReq request) throws BusinessException {
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.thirdBoxDeliveryOrderAddNotice(thirdBoxDeliveryOrderAddNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_DELIVERY_ORDER_ADD_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow thirdBoxDeliveryOrderAddNotice to [{}] with request[{}] success and response is: [{}]===", thirdBoxDeliveryOrderAddNoticePath, request, response.getResult());
        }
    }

    /**
     * 第三方转运订单创建通知
     */
    public void thirdTransportOrderAddNotice(ThirdTransportOrderAddReq request) throws BusinessException {
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.thirdTransportOrderAddNotice(thirdTransportOrderAddNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_TRANSPORT_ORDER_ADD_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow thirdTransportOrderAddNotice to [{}] with request[{}] success and response is: [{}]===", thirdTransportOrderAddNoticePath, request, response.getResult());
        }
    }

    /**
     * 第三方转运订单跟踪通知
     */
    public void thirdTransportOrderTraceNotice(ThirdTransportOrderTraceReq request) throws BusinessException {
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.thirdTransportOrderTraceNotice(thirdTransportOrderTraceNoticePath, request);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_TRANSPORT_ORDER_ADD_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow thirdTransportOrderTraceNotice to [{}] with request[{}] success and response is: [{}]===", thirdTransportOrderTraceNoticePath, request, response.getResult());
        }
    }

    /**
     * 第三方转运订单跟踪通知(纯粹的转发)
     */
    public void thirdCourierTraceNotice(Map<String, Object> traceInfo) throws BusinessException {
        QingFlowCommonRsp<QingFlowData> response = qingFlowClientProxy.thirdCourierTraceNotice(thirdCourierFastLineTraceNoticePath, traceInfo);
        if (response.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_TRANSPORT_ORDER_ADD_NOTICE_FAILED, response.getErrMsg());
        } else {
            log.info("===qingflow thirdCourierTraceNotice to [{}] with request[{}] success and response is: [{}]===", thirdCourierFastLineTraceNoticePath, traceInfo, response.getResult());
        }
    }

    /**
     * 删除出库订单
     *
     * @param thirdOrderNo 第三方订单号
     */
    public void deleteProductDeliveryOrder(String thirdOrderNo) throws BusinessException {
        AppRecordReq.Query query = new AppRecordReq.Query();
        query.setQueId(180203452);
        query.setSearchKey(thirdOrderNo);
        this.deleteAllAppData("10e1e0af", List.of(query));
    }

    /**
     * 查找商品重量信息
     *
     * @param productSkuCode 电商商品编号
     */
    public Optional<ProductSkuWeightData> queryProductSkuWeight(String productSkuCode) throws BusinessException {
        AppRecordReq.Query query1 = new AppRecordReq.Query();
        query1.setQueId(173956235);
        query1.setSearchKey(productSkuCode);

        List<ProductSkuWeightData> productSnWeightDataList = this.queryAllAppData("6f8deafd", List.of(query1), null, ProductSkuWeightData.class);
        if(CollectionUtilPlus.Collection.isEmpty(productSnWeightDataList)){
            return Optional.empty();
        }else{
            return Optional.of(productSnWeightDataList.get(0));
        }
    }

    /**
     * 查询快递商列表
     *
     * @return 快递商列表
     */
    public List<CourierData> queryCourierList() throws BusinessException {
        return this.queryAllAppData("a5560ec8", null, null, CourierData.class);
    }

    /**
     * 查询库位列表
     *
     * @return 库位列表
     */
    public List<LocationData> queryLocationList() throws BusinessException {
        return this.queryAllAppData("533bb784", null, null, LocationData.class);
    }

    /**
     * 查询收货地址
     *
     * @param supplier   客户
     * @param country    国家
     * @param deliveryTo 收货地址代码
     * @return 收货地址
     */
    public List<DeliveryAddressData> queryPublicAddressList(String supplier, String country, String deliveryTo) throws BusinessException {
        AppRecordReq.Query query1 = new AppRecordReq.Query();
        query1.setQueId(168009185);
        query1.setSearchKey(supplier);

        AppRecordReq.Query query2 = new AppRecordReq.Query();
        query2.setQueId(168009179);
        query2.setSearchKey(country);

        AppRecordReq.Query query3 = new AppRecordReq.Query();
        query3.setQueId(184307571);
        query3.setSearchKey(deliveryTo);

        return this.queryAllAppData("162c02c3", List.of(query1, query2, query3), null, DeliveryAddressData.class);
    }

    /**
     * 查询省份列表
     *
     * @return 省份列表
     */
    public List<ProvinceData> queryProvinceList() throws BusinessException {
        return this.queryAllAppData("f21409dd", null, null, ProvinceData.class);
    }

    /**
     * 查询城市列表
     *
     * @return 城市列表
     */
    public List<CityData> queryCityList(String province) throws BusinessException {
        AppRecordReq.Query query = new AppRecordReq.Query();
        query.setQueId(135191042);
        query.setSearchKey(province);
        return this.queryAllAppData("e54738ee", List.of(query), null, CityData.class);
    }

    /**
     * 查询运单轨迹
     *
     * @param waybill 运单号
     * @return 轨迹列表
     */
    public List<ExTracingDto> queryWaybillTraceData(String waybill) throws BusinessException {
        AppRecordReq.Query query = new AppRecordReq.Query();
        query.setQueId(176773348);
        query.setSearchKey(waybill);
        AppRecordReq.Sort sort = new AppRecordReq.Sort();
        sort.setQueId(180427014);
        sort.setIsAscend(Boolean.FALSE);

        List<TracingData> tracingDataList = this.queryAllAppData("2a6e46a1", List.of(query), List.of(sort), TracingData.class);
        return tracingDataList.stream().map(tracingData -> {
            ExTracingDto tracingDataDto = new ExTracingDto();
            tracingDataDto.setTimeStamp(tracingData.getTimeStamp());
            tracingDataDto.setScanLocation(tracingData.getScanLocation());
            tracingDataDto.setShipmentDirection(tracingData.getShipmentDirection());
            tracingDataDto.setStatus(tracingData.getStatus());
            return tracingDataDto;
        }).toList();
    }

    /**
     * 查询指定APP的数据
     *
     * @param appKey  轻流应用id
     * @param queries 查询条件
     * @param sorts   排序条件
     * @param clazz   返回数据类型
     * @return APP数据
     */
    private <T> List<T> queryAllAppData(String appKey, List<AppRecordReq.Query> queries, List<AppRecordReq.Sort> sorts, Class<T> clazz) throws BusinessException {
        AppRecordReq recordReq = new AppRecordReq();
        recordReq.setPageSize(500);
        recordReq.setPageNum(1);
        recordReq.setType(9);
        recordReq.setQueries(queries);
        recordReq.setSorts(sorts);
        QingFlowCommonRsp<QingFlowPageData<List<AppRecordData>>> result = qingFlowClientApiProxy.queryAppData(accessToken, appKey, recordReq);
        if (result.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_QUERY_APP_DATA_FAILED, result.getErrMsg());
        } else {
            log.info("===qingflow queryAppData[{}] with qry[{}] success and result is: [{}]===", appKey, queries, StringUtilPlus.abbreviate(result.getResult().toString(), 300));
            return result.getResult().getResult().stream().map(appRecordData -> {
                try {
                    T instance = clazz.getDeclaredConstructor().newInstance();
                    ReflectionUtils.doWithFields(clazz, field -> {
                        QingFlowField qingFlowField = field.getAnnotation(QingFlowField.class);
                        for (AppRecordData.Field answer : appRecordData.getAnswers()) {
                            if (answer.getQueTitle().equals(qingFlowField.value())) {
                                //这里简单处理，只取第一个值，并且只支持String类型
                                String value = answer.getValues().get(0).getValue();
                                field.setAccessible(true);
                                ReflectionUtils.setField(field, instance, value);
                                field.setAccessible(false);
                            }
                        }
                    });
                    return instance;
                } catch (Exception e) {
                    log.error("===qingflow queryAppData[{}] with qry[{}] failed and error is: [{}]===", appKey, queries, e.getMessage(), e);
                    return null;
                }
            }).toList();
        }
    }

    /**
     * 删除指定APP的数据
     *
     * @param appKey  APP唯一id
     * @param queries 查询条件
     */
    private void deleteAllAppData(String appKey, List<AppRecordReq.Query> queries) throws BusinessException {
        AppRecordReq recordReq = new AppRecordReq();
        recordReq.setPageSize(100);
        recordReq.setPageNum(1);
        recordReq.setType(9);
        recordReq.setQueries(queries);
        QingFlowCommonRsp<QingFlowData> result = qingFlowClientApiProxy.deleteAppData(accessToken, appKey, recordReq);
        if (result.getErrCode() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_QING_FLOW_DELETE_APP_DATA_FAILED, result.getErrMsg());
        } else {
            log.info("===qingflow deleteAllAppData[{}] with qry[{}] success and result is: [{}]===", appKey, queries, result.getResult());
        }
    }

    /**
     * Open API 代理
     */
    @HttpExchange(url = "https://api.qingflow.com", contentType = "application/json", accept = "application" + "/json")
    public interface ApiProxy {

        @PostExchange("/app/{appKey}/apply/filter")
        QingFlowCommonRsp<QingFlowPageData<List<AppRecordData>>> queryAppData(@RequestHeader String accessToken, @PathVariable String appKey, @RequestBody AppRecordReq appRecordReq);

        @DeleteExchange("/app/{appKey}/apply")
        QingFlowCommonRsp<QingFlowData> deleteAppData(@RequestHeader String accessToken, @PathVariable String appKey, @RequestBody AppRecordReq recordReq);
    }

    /**
     * 独立业务代理
     */
    @HttpExchange(url = "https://qingflow.com/api/qsource/", contentType = "application/json", accept = "application" + "/json")
    public interface Proxy {

        @PostExchange("{boxLocationLockNoticePath}")
        QingFlowCommonRsp<QingFlowData> boxLocationLockNotice(@PathVariable String boxLocationLockNoticePath, @RequestBody BoxLocationReq boxLocationReq);

        @PostExchange("{boxWholeLocationAssignNoticePath}")
        QingFlowCommonRsp<QingFlowData> boxWholeLocationAssignNotice(@PathVariable String boxWholeLocationAssignNoticePath, @RequestBody BoxLocationReq boxLocationReq);

        @PostExchange("{boxBulkLocationAssignNoticePath}")
        QingFlowCommonRsp<QingFlowData> boxBulkLocationAssignNotice(@PathVariable String boxBulkLocationAssignNoticePath, @RequestBody BoxLocationReq boxLocationReq);

        @PostExchange("{boxSnNoticePath}")
        QingFlowCommonRsp<QingFlowData> boxSnCreationNotice(@PathVariable String boxSnNoticePath, @RequestBody BoxSnReq boxSnReq);

        @PostExchange("{boxSnInboundNoticePath}")
        QingFlowCommonRsp<QingFlowData> boxSnInboundNotice(@PathVariable String boxSnInboundNoticePath, @RequestBody BoxSnReq boxSnReq);

        @PostExchange("{boxSnOutboundNoticePath}")
        QingFlowCommonRsp<QingFlowData> boxSnOutboundNotice(@PathVariable String boxSnOutboundNoticePath, @RequestBody BoxSnReq boxSnReq);

        @PostExchange("{productSnNoticePath}")
        QingFlowCommonRsp<QingFlowData> productSnCreationNotice(@PathVariable String productSnNoticePath, @RequestBody ProductSnReq productSnReq);

        @PostExchange("{productSnOnShelfNoticePath}")
        QingFlowCommonRsp<QingFlowData> productSnOnShelfNotice(@PathVariable String productSnOnShelfNoticePath, @RequestBody ProductSnOnShelfReq productSnOnShelfReq);

        @PostExchange("{productSnOffShelfNoticePath}")
        QingFlowCommonRsp<QingFlowData> productSnOffShelfNotice(@PathVariable String productSnOffShelfNoticePath, @RequestBody ProductSnOffShelfReq productSnOffShelfReq);

        @PostExchange("{productSnInboundNoticePath}")
        QingFlowCommonRsp<QingFlowData> productSnInboundNotice(@PathVariable String productSnInboundNoticePath, @RequestBody ProductSnReq productSnReq);

        @PostExchange("{productSnOutboundNoticePath}")
        QingFlowCommonRsp<QingFlowData> productSnOutboundNotice(@PathVariable String productSnOutboundNoticePath, @RequestBody ProductSnReq productSnReq);

        @PostExchange("{productDeliveryOrderAddNoticePath}")
        QingFlowCommonRsp<QingFlowData> productDeliveryOrderAddNotice(@PathVariable String productDeliveryOrderAddNoticePath, @RequestBody ProductDeliveryOrderAddReq productDeliveryOrderAddReq);

        @PostExchange("{productDeliveryOrderTraceNoticePath}")
        QingFlowCommonRsp<QingFlowData> productDeliveryOrderTraceNotice(@PathVariable String productDeliveryOrderTraceNoticePath, @RequestBody ProductDeliveryOrderTraceReq productDeliveryOrderTraceReq);

        @PostExchange("{productWaveTaskWeighingNoticePath}")
        QingFlowCommonRsp<QingFlowData> productWaveTaskWeighingNotice(@PathVariable String productWaveTaskWeighingNoticePath, @RequestBody ProductWaveTaskWeighingReq productWaveTaskWeighingReq);

        @PostExchange("{thirdBoxArrivalOrderAddNoticePath}")
        QingFlowCommonRsp<QingFlowData> thirdBoxArrivalOrderAddNotice(@PathVariable String thirdBoxArrivalOrderAddNoticePath, @RequestBody ThirdBoxArrivalOrderAddReq request);

        @PostExchange("{thirdBoxDeliveryOrderAddNoticePath}")
        QingFlowCommonRsp<QingFlowData> thirdBoxDeliveryOrderAddNotice(@PathVariable String thirdBoxDeliveryOrderAddNoticePath, @RequestBody ThirdBoxDeliveryOrderAddReq request);

        @PostExchange("{thirdTransportOrderAddNoticePath}")
        QingFlowCommonRsp<QingFlowData> thirdTransportOrderAddNotice(@PathVariable String thirdTransportOrderAddNoticePath, @RequestBody ThirdTransportOrderAddReq request);

        @PostExchange("{thirdTransportOrderTraceNoticePath}")
        QingFlowCommonRsp<QingFlowData> thirdTransportOrderTraceNotice(@PathVariable String thirdTransportOrderTraceNoticePath, @RequestBody ThirdTransportOrderTraceReq request);

        @PostExchange("{thirdCourierTraceNoticePath}")
        QingFlowCommonRsp<QingFlowData> thirdCourierTraceNotice(@PathVariable String thirdCourierTraceNoticePath, @RequestBody Map<String, Object> traceInfo);
    }
}
