package org.jarlin.callbacks;


import lombok.extern.slf4j.Slf4j;
import org.jarlin.database.Database;
import org.jarlin.models.Videogame;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class CallbacksExample {
    public static Flux<Videogame> callbacks() {
        return Database.getDataAsFlux()
//                .delayElements(Duration.ofMillis(500))
//                .timeout(Duration.ofMillis(300))
                .doOnSubscribe(subs -> log.info("[doOnSubscribe]"))
                .doOnRequest(n -> log.info("[doOnRequest]:{}", n))
                .doOnNext(videogame -> log.info("[doOnNext]:{}", videogame.getName()))
                .doOnCancel(() -> log.warn("[doOnCancel]"))
                .doOnError(err -> log.error("[doOnError]:{}", err.getMessage()))
                .doOnComplete(() -> log.info("[doOnComplete] success"))
                .doOnTerminate(() -> log.info("[doOnTerminate]"))
                .doFinally(signalType -> log.warn("[doFinally]:{}", signalType));

    }
}
