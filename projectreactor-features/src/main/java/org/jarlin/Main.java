package org.jarlin;

import lombok.extern.java.Log;
import org.jarlin.pipelines.PipelineAllComments;
import org.jarlin.pipelines.PipelineSumAllPricesInDiscount;
import org.jarlin.pipelines.PipelineTopSelling;
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

        PipelineTopSelling.getTopSellingVideogames()
                .subscribe(
                        System.out::println,
                        err -> log.info("Error: " + err.getMessage()),
                        () -> log.info("Top Selling Videogames retrieval complete!")
                );

        PipelineSumAllPricesInDiscount.getSumAllPricesInDiscount()
                .subscribe(
                        System.out::println,
                        err -> log.info("Error: " + err.getMessage()),
                        () -> log.info("Sum calculation complete!")
                );

        PipelineAllComments.getAllReviewsComments()
                .subscribe(
                        System.out::println,
                        err -> log.info("Error: " + err.getMessage()),
                        () -> log.info("All comments retrieval complete!")
                );
    }
}