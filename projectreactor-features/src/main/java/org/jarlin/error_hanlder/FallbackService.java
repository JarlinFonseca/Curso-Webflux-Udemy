package org.jarlin.error_hanlder;

import lombok.extern.slf4j.Slf4j;
import org.jarlin.database.Database;
import org.jarlin.models.Console;
import org.jarlin.models.Videogame;
import reactor.core.publisher.Flux;

@Slf4j
public class FallbackService {

    public static Flux<Videogame> callFallback() {
        return Database.getDataAsFlux()
                .handle((vg, sink) -> {
                    if (Console.DISABLED == vg.getConsole()){
                        sink.error(new RuntimeException("Videogame is disabled"));
                        return;
                    }
                    sink.next(vg);
                })
                .retry(5)
                .onErrorResume( error -> {
                    log.error("Database is failing - calling fallback service: " + error.getMessage());
                    return Database.fluxFallback;
                })
                .repeat(1)
                .cast(Videogame.class);
    }
}
