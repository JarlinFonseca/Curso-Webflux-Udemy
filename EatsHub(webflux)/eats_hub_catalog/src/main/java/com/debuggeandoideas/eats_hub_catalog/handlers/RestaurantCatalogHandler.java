package com.debuggeandoideas.eats_hub_catalog.handlers;

import com.debuggeandoideas.eats_hub_catalog.dtos.responses.RestaurantResponse;
import com.debuggeandoideas.eats_hub_catalog.services.definitions.RestaurantBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RestaurantCatalogHandler {

    private final RestaurantBusinessService restaurantBusinessService;

    public Mono<ServerResponse> getAllRestaurants(ServerRequest serverRequest){
        final var restaurantFlux = this.restaurantBusinessService.readAll();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(restaurantFlux, RestaurantResponse.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getRestaurantByName(ServerRequest serverRequest){
       final var restaurantName = serverRequest.pathVariable("name");
       final var monoResponse = this.restaurantBusinessService.readByName(restaurantName);
       return monoResponse
               .flatMap(restaurantResponse -> ServerResponse.ok()
                       .contentType(MediaType.APPLICATION_JSON)
                       .bodyValue(restaurantResponse))
               .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getRestaurantsByCuisineType(ServerRequest serverRequest){
        final var cuisineType = serverRequest.queryParam("cuisineType").orElse(null);
        if(Objects.isNull(cuisineType)){
            return ServerResponse.badRequest().bodyValue("cuisineType is required");
        }
        final var fluxResponse = this.restaurantBusinessService.readByCuisineType(cuisineType);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fluxResponse, RestaurantResponse.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getRestaurantBetweenPrice(ServerRequest serverRequest){
        return null;
    }

    public Mono<ServerResponse> getRestaurantsByCity(ServerRequest serverRequest){
        return null;
    }
}
