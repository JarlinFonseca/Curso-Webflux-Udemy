package com.debuggeandoideas.eats_hub_catalog.services.definitions;

import com.debuggeandoideas.eats_hub_catalog.collections.RestaurantCollection;
import com.debuggeandoideas.eats_hub_catalog.enums.PriceEnum;
import reactor.core.publisher.Flux;

import java.util.List;

public interface RestaurantCatalogService {

    Flux<RestaurantCollection> readByCuisineType(String cuisineType);

    Flux<RestaurantCollection> readByName(String name);

    Flux<RestaurantCollection> readByPriceRangeIn(List<PriceEnum> priceRange);

    Flux<RestaurantCollection> readByCity(String city);
}
