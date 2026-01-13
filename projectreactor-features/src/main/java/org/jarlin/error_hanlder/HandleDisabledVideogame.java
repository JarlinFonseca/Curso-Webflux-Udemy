package org.jarlin.error_hanlder;

import org.jarlin.database.Database;
import org.jarlin.models.Console;
import org.jarlin.models.Videogame;
import reactor.core.publisher.Flux;

public class HandleDisabledVideogame {

    public static Flux<Videogame> handleDisabledVideogames() {
        return Database.getDataAsFlux()
                .handle((vg, sink) -> {
                            if (Console.DISABLED == vg.getConsole()){
                                sink.error(new RuntimeException("Videogame is disabled"));
                                return;
                            }
                            sink.next(vg);
                        });
    }
}
