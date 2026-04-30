package com.debuggeandoideas.eats_hub_catalog.services.impls;

import com.debuggeandoideas.eats_hub_catalog.dtos.responses.RestaurantResponse;
import com.debuggeandoideas.eats_hub_catalog.enums.PriceEnum;
import com.debuggeandoideas.eats_hub_catalog.mappers.RestaurantMapper;
import com.debuggeandoideas.eats_hub_catalog.services.definitions.RestaurantBusinessService;
import com.debuggeandoideas.eats_hub_catalog.services.definitions.RestaurantCatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantBusinessServiceImpl implements RestaurantBusinessService {

    private final RestaurantCatalogService restaurantCatalogService;
    private final RestaurantMapper restaurantMapper;

    @Override
    public Flux<RestaurantResponse> readAll() {
        log.info("Reading all restaurants");
        return this.restaurantCatalogService.readAll()
                .transform(this.restaurantMapper::toResponseFlux)
                .doOnComplete(() -> log.info("Reading all restaurants completed"));
    }

    @Override
    public Flux<RestaurantResponse> readByCuisineType(String cuisineType) {
        log.info("Reading restaurants by cuisine type {}", cuisineType);
        return this.restaurantCatalogService.readByCuisineType(cuisineType)
                .transform(this.restaurantMapper::toResponseFlux)
                .doOnComplete(() -> log.info("Reading restaurants by cuisine type {} completed", cuisineType));
    }

    @Override
    public Mono<RestaurantResponse> readByName(String name) {
        log.info("Reading restaurant by name {}", name);
        return this.restaurantCatalogService.readByName(name)
                .transform(this.restaurantMapper::toResponseMono)
                .doOnSuccess(restaurant -> {
                    if(Objects.isNull(restaurant)){
                        log.info("Reading restaurant by name {} not found any restaurants", name);
                    } else {
                        log.info("Reading restaurant by name {} completed", name);
                    }
                });
    }

    @Override
    public Flux<RestaurantResponse> readByPriceRangeIn(List<PriceEnum> priceRange) {
        log.info("Reading restaurants by price range {}", priceRange);
        return this.restaurantCatalogService.readByPriceRangeIn(priceRange)
                .transform(this.restaurantMapper::toResponseFlux)
                .doOnComplete(() -> log.info("Reading restaurants by price range {} completed", priceRange));
    }

    @Override
    public Flux<RestaurantResponse> readByCity(String city) {
        log.info("Reading restaurants by city {}", city);
        return this.restaurantCatalogService.readByCity(city)
                .transform(this.restaurantMapper::toResponseFlux)
                .doOnComplete(() -> log.info("Reading restaurants by city {} completed", city));
    }
}
