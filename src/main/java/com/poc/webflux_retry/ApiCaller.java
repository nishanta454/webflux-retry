package com.poc.webflux_retry;

import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@AllArgsConstructor
public class ApiCaller {

	private final WebClient webClient;

	@SuppressWarnings("rawtypes")
	private final ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

	@PostConstruct
	public void init() {
		log.info(unstable().block());
	}
	
	public Mono<String> unstable() {
		log.info("INSIDE UNSTABLE GET API CALL");
		return webClient.get().uri("/get?test=234").retrieve().bodyToMono(String.class).transform(it -> {
			ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("default-service");
			return rcb.run(it, throwable -> stable());
		});
	}
	
	public Mono<String> stable() {
		log.info("INSIDE STABLE GET API CALL");
		return webClient.get().uri("/get?test=123").retrieve().bodyToMono(String.class);
	}
}
