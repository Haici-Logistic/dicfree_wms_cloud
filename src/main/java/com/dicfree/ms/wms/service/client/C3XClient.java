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
import cn.jzyunqi.common.utils.DateTimeUtilPlus;
import com.dicfree.ms.wms.common.dto.ex.ExTracingDto;
import com.dicfree.ms.wms.common.dto.ex.c3x.C3xComReq;
import com.dicfree.ms.wms.common.dto.ex.c3x.PdfReq;
import com.dicfree.ms.wms.common.dto.ex.c3x.PdfRsp;
import com.dicfree.ms.wms.common.dto.ex.c3x.TrackingReq;
import com.dicfree.ms.wms.common.dto.ex.c3x.TrackingRsp;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author wiiyaya
 * @date 2023/10/28
 */
@Component
@Slf4j
public class C3XClient implements CourierClient {

    private static final DateTimeFormatter C3X_DATE_TIME = DateTimeFormatter.ofPattern("EEEE dd  MMMM  yyyy HH:mm", Locale.ENGLISH);

    @Value("${df.feature.c3x.username:}")
    private String username;

    @Value("${df.feature.c3x.password:}")
    private String password;

    @Value("${df.feature.c3x.account-no:}")
    private String accountNo;

    @Resource
    private C3XClient.Proxy c3xClientProxy;

    @Override
    public List<ExTracingDto> trackOrder(String waybill, Object callbackParams) throws BusinessException {
        log.info("C3X trackOrder[{}] receive", waybill);
        TrackingReq trackingRequestData = new TrackingReq();
        initComReq(trackingRequestData);
        trackingRequestData.setTrackingAwb(waybill);
        TrackingRsp trackingRsp = c3xClientProxy.trackOrder(trackingRequestData);
        if (!"1".equals(trackingRsp.getCode())) {
            throw new BusinessException("C3XClient.trackOrder.failed", trackingRsp.getDescription());
        }
        List<TrackingRsp.AirWaybillTrack> waybillTrackList = trackingRsp.getAirWaybillTrackList();
        if (waybillTrackList.get(0).getTrackingLogDetails() == null) {
            return new ArrayList<>();
        } else {
            return waybillTrackList.get(0).getTrackingLogDetails().stream().map(trackingLogDetail -> {
                ExTracingDto tracingDto = new ExTracingDto();
                LocalDateTime temp = LocalDateTime.from(C3X_DATE_TIME.parse(trackingLogDetail.getActivityDate() + " " + trackingLogDetail.getActivityTime()));
                tracingDto.setTimeStamp(temp.format(DateTimeUtilPlus.SYSTEM_DATE_TIME_FORMAT));
                tracingDto.setScanLocation(trackingLogDetail.getLocation());
                tracingDto.setDeliveryTo(trackingLogDetail.getDeliveredTo());
                tracingDto.setStatusCode(trackingLogDetail.getStatus());
                tracingDto.setStatus(trackingLogDetail.getRemarks());
                return tracingDto;
            }).toList();
        }
    }

    /**
     * 生成面单PDF
     *
     * @param waybill 运单号
     * @return PDF
     */
    @Override
    public String genWaybillPdf(String waybill) throws BusinessException {
        PdfReq pdfReqData = new PdfReq();
        initComReq(pdfReqData);

        pdfReqData.setAirWaybillNumber(waybill);
        pdfReqData.setPrintType("LABEL");
        pdfReqData.setRequestUser("");
        PdfRsp pdfRsp = c3xClientProxy.genWaybillPdf(pdfReqData);
        if (!"1".equals(pdfRsp.getCode())) {
            throw new BusinessException("C3XClient.genWaybillPdf.failed", pdfRsp.getDescription());
        }
        return pdfRsp.getReportDoc();
    }

    /**
     * 初始化公共请求参数
     *
     * @param c3xComReqData 公共请求参数
     */
    private void initComReq(C3xComReq c3xComReqData) {
        c3xComReqData.setUsername(username);
        c3xComReqData.setPassword(password);
        c3xComReqData.setAccountNo(accountNo);
        c3xComReqData.setCountry("AE");
    }

    @HttpExchange(url = "https://portal.c3xpress.com/C3XService.svc/", contentType = "application/json", accept = "application" + "/json")
    public interface Proxy {

        @PostExchange("/Tracking")
        TrackingRsp trackOrder(@RequestBody TrackingReq trackingReq);

        @PostExchange("/AirwayBillPDFFormat")
        PdfRsp genWaybillPdf(@RequestBody PdfReq pdfReq);
    }
}
