package com.poc.webflux_retry;

import java.time.Duration;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

@Slf4j
@Component
@AllArgsConstructor
public class ApiCaller {

	private final WebClient webClient;

	@PostConstruct
	public void init() {
		try {
			webfluxRetry().block();
		} catch(Exception ex) {
			log.error("Exception : "+ ex.getMessage());
		}
	}

	public Mono<String> webfluxRetry() {
		RetryBackoffSpec retrySpec = Retry.backoff(3, Duration.ofSeconds(2)).jitter(0.75)
	            .doAfterRetry(signal -> log.info("Retry Number {}", signal.totalRetries()));

		return webClient.get().uri("/get?test=234").retrieve().bodyToMono(String.class)
				.onErrorResume(Mono::error)
				.retryWhen(retrySpec);
	}
}
