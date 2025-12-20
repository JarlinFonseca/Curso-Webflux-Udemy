package org.jarlin;

import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log
public class Main {
    public static void main(String[] args) {

        //Publisher Mono
        Mono<String> mono = Mono.just("Hello world")
                .doOnNext(value -> log.info("[onNext] " + value))
                .doOnSuccess(value -> log.info("[onSuccess] " + value))
                .doOnError(error -> log.info("[onError] " + error.getMessage()));

        //Consumer
        mono.subscribe(
                data -> log.info("Receiving data: " + data),
                err -> log.info("Error: " + err.getMessage()),
                () -> log.info("Complete success!")
        );

        //Publisher Flux
        Flux<String> flux = Flux.just("Java", "Reactor", "Spring", "Reactor", "R2DBC")
                .doOnNext(value -> log.info("[onNext] " + value))
                .doOnComplete(() -> log.info("[onComplete]: Success"))
                .doOnError(error -> log.info("[onError] " + error.getMessage()));

        //Consumer
        flux.subscribe(
                data -> log.info("Receiving data: " + data),
                err -> log.info("Error: " + err.getMessage()),
                () -> log.info("Complete success!")
        );
    }
}