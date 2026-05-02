package com.debuggeandoideas.eats_hub_catalog.handlers;

import com.debuggeandoideas.eats_hub_catalog.services.definitions.RestaurantBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RestaurantCatalogHandler {

    private final RestaurantBusinessService restaurantBusinessService;

    public Mono<ServerResponse> getAllRestaurants(ServerRequest serverRequest){
        return null;
    }

    public Mono<ServerResponse> getRestaurantByName(ServerRequest serverRequest){
        return null;
    }

    public Mono<ServerResponse> getRestaurantsByCuisineType(ServerRequest serverRequest){
        return null;
    }

    public Mono<ServerResponse> getRestaurantBetweenPrice(ServerRequest serverRequest){
        return null;
    }

    public Mono<ServerResponse> getRestaurantsByCity(ServerRequest serverRequest){
        return null;
    }
}
