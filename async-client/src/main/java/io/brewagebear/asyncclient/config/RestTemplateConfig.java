package com.szs.szshomework.global.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    private final HttpClientConfig httpClientConfig;

    private final ResponseErrorHandler errorHandler;

    public RestTemplateConfig(HttpClientConfig httpClientConfig,
        ResponseErrorHandler errorHandler) {
        this.httpClientConfig = httpClientConfig;
        this.errorHandler = errorHandler;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
            .requestFactory(this::generateHttpRequestFactory)
            .errorHandler(errorHandler)
            .build();
    }

    private ClientHttpRequestFactory generateHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory connectionFactory = new HttpComponentsClientHttpRequestFactory();

        connectionFactory.setReadTimeout(httpClientConfig.readTimeout());
        connectionFactory.setConnectionRequestTimeout(httpClientConfig.connectionTimeout());

        HttpClient client = HttpClientBuilder.create()
            .setMaxConnTotal(httpClientConfig.maxConnection())
            .setMaxConnPerRoute(httpClientConfig.maxConnectionPerRoute()).build();

        connectionFactory.setHttpClient(client);
        return connectionFactory;
    }
}
