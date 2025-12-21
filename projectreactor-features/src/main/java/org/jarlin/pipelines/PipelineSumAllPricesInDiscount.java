package org.jarlin.pipelines;

import org.jarlin.database.Database;
import org.jarlin.models.Videogame;
import reactor.core.publisher.Mono;

public class PipelineSumAllPricesInDiscount {
    /*
          Sum all prices of each videogame in discount
     */
    public static Mono<Double> getSumAllPricesInDiscount() {
        return Database.getDataAsFlux()
                .filter(Videogame::getIsDiscount)
                .map(Videogame::getPrice)
                .reduce(0.0, Double::sum);
    }

}
