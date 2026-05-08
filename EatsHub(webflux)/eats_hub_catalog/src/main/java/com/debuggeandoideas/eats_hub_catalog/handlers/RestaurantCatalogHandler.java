package com.debuggeandoideas.eats_hub_catalog.handlers;

import com.debuggeandoideas.eats_hub_catalog.dtos.responses.RestaurantResponse;
import com.debuggeandoideas.eats_hub_catalog.enums.PriceEnum;
import com.debuggeandoideas.eats_hub_catalog.services.definitions.RestaurantBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RestaurantCatalogHandler {

    private final RestaurantBusinessService restaurantBusinessService;

    public Mono<ServerResponse> getAllRestaurants(ServerRequest serverRequest){
        final Integer page = serverRequest.queryParam("page").map(Integer::parseInt).orElse(0);
        final Integer size = serverRequest.queryParam("size").map(Integer::parseInt).orElse(10);
        final var restaurantFlux = this.restaurantBusinessService.readAll(page, size);
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

    //@QueryParam prices List
    public Mono<ServerResponse> getRestaurantBetweenPrice(ServerRequest serverRequest){
        final var prices = serverRequest.queryParam("prices").orElse(null);

        if(Objects.isNull(prices)){
            return ServerResponse.badRequest().bodyValue("prices is required");
        }

        final var typeList = Arrays.stream(prices.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .map(PriceEnum::valueOf)
                .toList();

        final var fluxResponse = this.restaurantBusinessService.readByPriceRangeIn(typeList);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fluxResponse, RestaurantResponse.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getRestaurantsByCity(ServerRequest serverRequest){
        final var city = serverRequest.queryParam("city").orElse(null);
        if(Objects.isNull(city)){
            return ServerResponse.badRequest().bodyValue("city is required");
        }

        final var fluxRestaurants = this.restaurantBusinessService.readByCity(city);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fluxRestaurants, RestaurantResponse.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
