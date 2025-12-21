package org.jarlin.pipelines;

import org.jarlin.database.Database;
import org.jarlin.models.Review;
import reactor.core.publisher.Flux;

public class PipelineAllComments {
    /*
        Return all comments of all reviews of all videogames

        box -> [items, items, items] -> box -> [items, items]
        .map box -> boxWithLabel = 2 items
        .flatMap: item -> useItem(item) = 5 items

     */
    public static Flux<String> getAllReviewsComments(){
        return Database.getDataAsFlux()
                .flatMap(videogame ->  Flux.fromIterable(videogame.getReviews())) //Flux<Videogame> -> Flux<Review>
                .map(Review::getComment); //Flux<Review> -> Flux<String

    }
}
