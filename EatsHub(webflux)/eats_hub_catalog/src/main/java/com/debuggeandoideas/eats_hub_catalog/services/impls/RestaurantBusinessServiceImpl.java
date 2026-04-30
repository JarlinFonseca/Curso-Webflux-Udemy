package com.debuggeandoideas.eats_hub_catalog.services.impls;

import com.debuggeandoideas.eats_hub_catalog.dtos.responses.RestaurantResponse;
import com.debuggeandoideas.eats_hub_catalog.enums.PriceEnum;
import com.debuggeandoideas.eats_hub_catalog.mappers.RestaurantMapper;
import com.debuggeandoideas.eats_hub_catalog.repositories.RestaurantRepository;
import com.debuggeandoideas.eats_hub_catalog.services.definitions.RestaurantBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantBusinessServiceImpl implements RestaurantBusinessService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public Flux<RestaurantResponse> readAll() {
        return null;
    }

    @Override
    public Flux<RestaurantResponse> readByCuisineType(String cuisineType) {
        return null;
    }

    @Override
    public Mono<RestaurantResponse> readByName(String name) {
        return null;
    }

    @Override
    public Flux<RestaurantResponse> readByPriceRangeIn(List<PriceEnum> priceRange) {
        return null;
    }

    @Override
    public Flux<RestaurantResponse> readByCity(String city) {
        return null;
    }
}
