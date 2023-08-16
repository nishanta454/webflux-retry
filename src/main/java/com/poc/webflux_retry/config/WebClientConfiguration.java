package com.poc.webflux_retry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfiguration {
	@Bean
    public WebClient getWebClient(){
        return WebClient.builder()
                .baseUrl("https://05a7155f-79db-4c36-8257-e633a3a63462.mock.pstmn.io")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
