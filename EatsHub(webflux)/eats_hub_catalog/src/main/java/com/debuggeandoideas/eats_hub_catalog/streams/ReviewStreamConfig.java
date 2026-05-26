package com.debuggeandoideas.eats_hub_catalog.streams;

import com.debuggeandoideas.eats_hub_catalog.dtos.events.ReviewEvent;
import com.debuggeandoideas.eats_hub_catalog.services.impls.ReviewStreamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ReviewStreamConfig {

    private final ObjectMapper objectMapper;
    private final ReviewStreamService reviewStreamService;

    @Bean
    public Consumer<Flux<Message<String>>> reviewEventConsumer() {
        return flux -> flux
                .doOnNext(message -> log.info("Review event received {}", message.getPayload()))
                .flatMap(this::processMessage)
                .doOnError(error -> log.error("Error processing review event: {}", error.getMessage()))
                .retry(3)
                .subscribe();
    }


    private Mono<Void> processMessage(Message<String> message){
      return Mono.fromCallable(() -> {
          String payload = message.getPayload();
          return this.objectMapper.readValue(payload, ReviewEvent.class);
      })
              .flatMap(this.reviewStreamService::processReview)
              .doOnSuccess(unused -> log.info("Processed review event successfully"))
              .onErrorResume(throwable -> {
                  log.error(throwable.getMessage(), throwable);
                  return Mono.empty();
              });
    }
}
