package org.jarlin;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.jarlin.error_hanlder.FallbackService;
import org.jarlin.error_hanlder.HandleDisabledVideogame;
import org.jarlin.pipelines.PipelineAllComments;
import org.jarlin.pipelines.PipelineSumAllPricesInDiscount;
import org.jarlin.pipelines.PipelineTopSelling;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
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


        Flux<String> fluxA  = Flux.just("1", "2");  //From reactive mongo
        Flux<String> fluxB  = Flux.just("A", "B", "C"); //From WebClient

        Flux<String> combinedFlux = fluxA.flatMap(
                strA -> fluxB.map(strB -> strA + "-"+ strB)
        );

        combinedFlux
                .map(String::toLowerCase)
                .doOnNext(System.out::println)
                .subscribe();


        // Merge and concat
        Flux<String> fluxA1  = Flux.just("1", "2", "3").delayElements(Duration.ofMillis(100));
        Flux<String> fluxB2  = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(50));


        System.out.println("--- Using merge ---");

        Flux<String> combinedFlux2 = Flux.merge(fluxA1, fluxB2);

        combinedFlux2
                .doOnNext(System.out::println)
                .blockLast();

        System.out.println("--- Using concat ---");
        Flux<String> combinedFlux3 = Flux.concat(fluxA1, fluxB2);
        combinedFlux3
                .doOnNext(System.out::println)
                .blockLast();



        // call ms shipments
        Flux<String> fluxShipments = Flux.just("Shipment1", "Shipment2", "Shipment3")
                .delayElements(Duration.ofMillis(120));
        // call ms warehouses
        Flux<String> fluxWarehouse = Flux.just("stock1", "stock2", "stock3")
                .delayElements(Duration.ofMillis(50));
        // call ms payments
        Flux<String> fluxPayments = Flux.just("pay1", "pay2", "pay3")
                .delayElements(Duration.ofMillis(150));
        // call ms confirm
        Flux<String> fluxConfirm = Flux.just("confirm1", "confirm2", "confirm3")
                .delayElements(Duration.ofMillis(20));


        //Flux<String> reportFlux = Flux.zip(fluxShipments, fluxWarehouse, (shipment, stock) -> shipment + ""+stock);

        Flux<String> reportFlux = Flux.zip(fluxShipments, fluxWarehouse, fluxPayments, fluxConfirm)
                        .map(tuple -> tuple.getT1() + " | " + tuple.getT2() + " | " + tuple.getT3() + " | " + tuple.getT4());
        reportFlux
                .doOnNext(System.out::println)
                .blockLast();


        HandleDisabledVideogame.handleDisabledVideogamesDefault()
                .subscribe(System.out::println);

        System.out.println("--- Fallback Service Example ---");

        FallbackService.callFallback()
                .subscribe(v -> log.info(v.toString()));
    }

}