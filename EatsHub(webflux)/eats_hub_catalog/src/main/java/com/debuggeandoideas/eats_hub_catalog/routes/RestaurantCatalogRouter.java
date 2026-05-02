package com.debuggeandoideas.eats_hub_catalog.routes;

import com.debuggeandoideas.eats_hub_catalog.handlers.RestaurantCatalogHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RestaurantCatalogRouter {

    @Bean
   public RouterFunction<ServerResponse> routes(RestaurantCatalogHandler handler) {
        return route()
                .GET(BY_NAME_URL, handler::getRestaurantByName)
                .GET(BASE_URL, request -> {
                    if(request.queryParam("cuisineType").isPresent()){
                        return handler.getRestaurantsByCuisineType(request);
                    } else if(request.queryParam("min").isPresent() && request.queryParam("max").isPresent()){
                        return handler.getRestaurantBetweenPrice(request);
                    } else if(request.queryParam("city").isPresent()){
                        return handler.getRestaurantsByCity(request);
                    } else {
                        return handler.getAllRestaurants(request);
                    }
                })
                .build();
    }

    private final String BASE_URL = "/catalog/restaurants";
    private final String BY_NAME_URL = BASE_URL + "/{name}";
}
