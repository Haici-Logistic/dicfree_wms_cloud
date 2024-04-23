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
package com.dicfree.start;

import com.dicfree.ms.wms.service.client.C3XClient;
import com.dicfree.ms.wms.service.client.FastLineClient;
import com.dicfree.ms.wms.service.client.FeieyunClient;
import com.dicfree.ms.wms.service.client.LarkClient;
import com.dicfree.ms.wms.service.client.QingFlowClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

/**
 * @author wiiyaya
 * @date 2023/7/12.
 */
@SpringBootApplication
@EnableScheduling
public class DicFreeCloudStarter {

    @Bean
    public FeieyunClient.Proxy feieyunClientProxy(WebClient.Builder webClientBuilder, Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        //这里需要clone，否则会改动全局配置
        WebClient webClient = webClientBuilder.clone()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .jackson2JsonDecoder(new Jackson2JsonDecoder(jackson2ObjectMapperBuilder.build(),
                                MediaType.APPLICATION_JSON,
                                new MediaType("application", "*+json"),
                                MediaType.APPLICATION_NDJSON,
                                MediaType.TEXT_HTML //飞鹅云欧洲区和中国区返回情况不一样，需要兼容
                        )))
                .build();

        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClient);
        webClientAdapter.setBlockTimeout(Duration.ofSeconds(5));

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        return factory.createClient(FeieyunClient.Proxy.class);
    }

    @Bean
    public QingFlowClient.Proxy qingFlowClientProxy(WebClient.Builder webClientBuilder, Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        WebClient webClient = webClientBuilder.clone()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .jackson2JsonDecoder(new Jackson2JsonDecoder(jackson2ObjectMapperBuilder.build(),
                                MediaType.APPLICATION_JSON,
                                new MediaType("application", "*+json"),
                                MediaType.APPLICATION_NDJSON
                        )))
                .build();

        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClient);
        webClientAdapter.setBlockTimeout(Duration.ofSeconds(5));

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        return factory.createClient(QingFlowClient.Proxy.class);
    }

    @Bean
    public QingFlowClient.ApiProxy qingFlowClientApiProxy(WebClient.Builder webClientBuilder) {
        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClientBuilder.build());
        webClientAdapter.setBlockTimeout(Duration.ofSeconds(5));

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        return factory.createClient(QingFlowClient.ApiProxy.class);
    }

    @Bean
    public LarkClient.Proxy larkClientProxy(WebClient.Builder webClientBuilder) {
        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClientBuilder.build());
        webClientAdapter.setBlockTimeout(Duration.ofSeconds(5));

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        return factory.createClient(LarkClient.Proxy.class);
    }

    @Bean
    public C3XClient.Proxy c3xClientProxy(WebClient.Builder webClientBuilder) {
        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClientBuilder.build());
        webClientAdapter.setBlockTimeout(Duration.ofSeconds(60));

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        return factory.createClient(C3XClient.Proxy.class);
    }

    @Bean
    public FastLineClient.Proxy fastLineClientProxy(WebClient.Builder webClientBuilder) {
        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClientBuilder.build());
        webClientAdapter.setBlockTimeout(Duration.ofSeconds(15));

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        return factory.createClient(FastLineClient.Proxy.class);
    }

    public static void main(String[] args) {
        System.setProperty(SecurityContextHolder.SYSTEM_PROPERTY, SecurityContextHolder.MODE_THREADLOCAL);
        SpringApplication.run(DicFreeCloudStarter.class, args);
    }
}
