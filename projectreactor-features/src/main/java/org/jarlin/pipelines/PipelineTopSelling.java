package org.jarlin.pipelines;

import org.jarlin.database.Database;
import org.jarlin.models.Videogame;
import reactor.core.publisher.Flux;

import java.util.List;

public class PipelineTopSelling {
    /*
       Return all names of videogames with have a sold > 80
     */
    public static Flux<String> getTopSellingVideogames(){
         return Database.getDataAsFlux()
                 .filter( videogame -> videogame.getTotalSold() > 80)
                 .map(Videogame::getName);
    }
}
