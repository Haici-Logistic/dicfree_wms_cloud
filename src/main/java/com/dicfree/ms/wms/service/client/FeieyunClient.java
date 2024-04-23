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
import cn.jzyunqi.common.utils.DigestUtilPlus;
import cn.jzyunqi.common.utils.StringUtilPlus;
import com.dicfree.ms.wms.common.constant.WmsMessageConstant;
import com.dicfree.ms.wms.common.dto.ex.feie.AddData;
import com.dicfree.ms.wms.common.dto.ex.feie.FeieCommonRsp;
import com.dicfree.ms.wms.common.enums.DevicePrinterStatus;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.time.LocalDateTime;

/**
 * @author wiiyaya
 * @date 2023/7/19.
 */
@Component
@Slf4j
public class FeieyunClient {

//    public static void main(String[] args) {
//        // 创建一个DataBuffer对象
//        DataBuffer buffer = new DefaultDataBufferFactory().wrap("{\"ret\":0,\"msg\":\"ok\",\"data\":{\"ok\":[]},\"serverExecutedTime\":0}".getBytes());
//
//        // 创建一个ResolvableType对象
//        ResolvableType targetType = ResolvableType.forType(new ParameterizedTypeReference<Object>() {});
//
//        // 创建一个MimeType对象，表示媒体类型为application/json
//        MimeType mimeType = MimeTypeUtils.APPLICATION_JSON;
//
//        // 创建一个空的hints参数
//        Map<String, Object> hints = new HashMap<>();//Hints
//        hints.put(Hints.SUPPRESS_LOGGING_HINT, false);
//
//        // 调用Jackson2JsonDecoder的decode方法进行解码
//        Jackson2JsonDecoder decoder = new Jackson2JsonDecoder();
//        Object result = decoder.decode(buffer, targetType, mimeType, hints);
//
//        System.out.println("syso: " + result);
//    }

    private static final String PRINTER_ADD_API = "Open_printerAddlist";
    private static final String PRINTER_DELETE_API = "Open_printerDelList";
    private static final String PRINTER_STATUS_API = "Open_queryPrinterStatus";
    private static final String PRINTER_CLEAN_API = "Open_delPrinterSqs";
    private static final String PRINTER_PRINT_LABEL_API = "Open_printLabelMsg";

    @Value("${df.feature.feie.user:}")
    private String user;

    @Value("${df.feature.feie.user-key:}")
    private String userKey;

    @Value("${df.feature.feie.host:}")
    private String host;

    @Resource
    private FeieyunClient.Proxy feieyunClientProxy;

    /**
     * 添加打印机
     *
     * @param sn   打印机编号(必填)
     * @param key  打印机识别码(必填)
     * @param name 备注名称(选填)
     * @param sim  流量卡号码(选填)
     * @throws BusinessException 状态获取异常
     */
    public void devicePrinterAdd(String sn, String key, String name, String sim) throws BusinessException {
        String time = getTime();
        String sig = getSign(time);

        String devicePrinterContent = StringUtilPlus.joinWith("#", sn, key, name, sim);

        FeieCommonRsp<AddData> result = feieyunClientProxy.devicePrinterAdd(host, user, time, sig, PRINTER_ADD_API, devicePrinterContent);
        if (result.getRet() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_DEVICE_PRINTER_ADD_FAILED, result.getMsg());
        }else {
            log.info("===Feieyun devicePrinterAdd success to [{}] with request[{}][{}][{}][{}] and response is: [{}]===", PRINTER_ADD_API, sn, key, name, sim, result);
        }
    }

    /**
     * 删除打印机
     *
     * @param snList 打印机编号，多台打印机请用减号“-”连接起来。
     * @throws BusinessException 删除打印机异常
     */
    public void devicePrinterDelete(String snList) throws BusinessException {
        String time = getTime();
        String sig = getSign(time);

        FeieCommonRsp<AddData> result = feieyunClientProxy.devicePrinterDelete(host, user, time, sig, PRINTER_DELETE_API, snList);
        if (result.getRet() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_DEVICE_PRINTER_DELETE_FAILED, result.getMsg());
        }else {
            log.info("===Feieyun devicePrinterDelete success to [{}] with request[{}] and response is: [{}]===", PRINTER_DELETE_API, snList, result);
        }
    }

    /**
     * 获取打印机状态
     *
     * @param sn 打印机sn
     * @return 打印机状态
     * @throws BusinessException 状态获取异常
     */
    public DevicePrinterStatus devicePrinterStatus(String sn) throws BusinessException {
        String time = getTime();
        String sig = getSign(time);

        FeieCommonRsp<String> result = feieyunClientProxy.devicePrinterStatus(host, user, time, sig, PRINTER_STATUS_API, sn);
        if (result.getRet() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_DEVICE_PRINTER_STATUS_FAILED, result.getMsg());
        } else {
            return DevicePrinterStatus.toDevicePrinterStatus(result.getData());
        }
    }

    /**
     * 清空待打印队列
     *
     * @param sn 打印机sn
     */
    public void devicePrinterClear(String sn) throws BusinessException {
        String time = getTime();
        String sig = getSign(time);

        FeieCommonRsp<Boolean> result = feieyunClientProxy.devicePrinterClear(host, user, time, sig, PRINTER_CLEAN_API, sn);
        if (result.getRet() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_DEVICE_PRINTER_CLEAN_FAILED, result.getMsg());
        }else {
            log.info("===Feieyun devicePrinterClear success to [{}] with request[{}] and response is: [{}]===", PRINTER_CLEAN_API, sn, result);
        }
    }

    /**
     * 打印数据
     *
     * @param sn      打印机sn
     * @param content 数据信息
     */
    public void print(String sn, String content) throws BusinessException {
        String time = getTime();
        String sig = getSign(time);

        FeieCommonRsp<String> result = feieyunClientProxy.print(host, user, time, sig, PRINTER_PRINT_LABEL_API, sn, content);
        if (result.getRet() != 0) {
            throw new BusinessException(WmsMessageConstant.ERROR_DEVICE_PRINTER_PRINT_FAILED, result.getMsg());
        }else {
            log.info("===Feieyun print success to [{}] with request[{}][{}] and response is: [{}]===", PRINTER_PRINT_LABEL_API, sn, content, result);
        }
    }


    /**
     * 获取当前时间戳，10位，精确到秒
     *
     * @return 时间戳
     */
    private static String getTime() {
        return DateTimeUtilPlus.toEpochSecond(LocalDateTime.now()).toString();
    }

    /**
     * 签名数据
     *
     * @param time 当前时间戳
     * @return 签名数据
     */
    private String getSign(String time) {
        return DigestUtilPlus.SHA.sign(user + userKey + time, DigestUtilPlus.SHAAlgo._1, Boolean.FALSE);
    }

    @HttpExchange(url = "https://{host}/Api/Open/", contentType = "application/x-www-form-urlencoded", accept = {"application/json", "text/html;charset=UTF-8"})
    public interface Proxy {
        @PostExchange
        FeieCommonRsp<AddData> devicePrinterAdd(@PathVariable String host, @RequestParam String user, @RequestParam String stime, @RequestParam String sig, @RequestParam String apiname, @RequestParam String devicePrinterContent);
        @PostExchange
        FeieCommonRsp<AddData> devicePrinterDelete(@PathVariable String host, @RequestParam String user, @RequestParam String stime, @RequestParam String sig, @RequestParam String apiname, @RequestParam String snlist);
        @PostExchange
        FeieCommonRsp<String> devicePrinterStatus(@PathVariable String host, @RequestParam String user, @RequestParam String stime, @RequestParam String sig, @RequestParam String apiname, @RequestParam String sn);
        @PostExchange
        FeieCommonRsp<Boolean> devicePrinterClear(@PathVariable String host, @RequestParam String user, @RequestParam String stime, @RequestParam String sig, @RequestParam String apiname, @RequestParam String sn);
        @PostExchange
        FeieCommonRsp<String> print(@PathVariable String host, @RequestParam String user, @RequestParam String stime, @RequestParam String sig, @RequestParam String apiname, @RequestParam String sn, @RequestParam String content);
    }
}
