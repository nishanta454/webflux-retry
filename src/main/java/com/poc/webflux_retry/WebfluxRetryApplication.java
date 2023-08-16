package com.poc.webflux_retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class WebfluxRetryApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxRetryApplication.class, args);
	}

	@Bean
	public WebClient webClient() {
		return WebClient.create("https://05a7155f-79db-4c36-8257-e633a3a63462.mock.pstmn.io");
	}
}
