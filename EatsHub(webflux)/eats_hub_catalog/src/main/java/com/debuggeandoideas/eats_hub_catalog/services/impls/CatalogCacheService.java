package com.debuggeandoideas.eats_hub_catalog.services.impls;

import com.debuggeandoideas.eats_hub_catalog.dtos.responses.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogCacheService {

    private final ReactiveRedisTemplate<String, RestaurantResponse> redisTemplate;
    private final ReactiveRedisTemplate<String, List<RestaurantResponse>> redisListTemplate;

    private static final Duration DEFAULT_TTL = Duration.ofHours(1);
    private final String KEY_PREFIX = "restaurant:";

    public Mono<RestaurantResponse> getCacheRestaurant(String key) {
        return null;
    }

    public Mono<RestaurantResponse> cacheRestaurant(String key, RestaurantResponse restaurant) {
        return null;
    }

    public Flux<RestaurantResponse> getCacheRestaurants(String key) {
        return null;
    }

    public Flux<RestaurantResponse> cacheRestaurants(String key, Flux<RestaurantResponse> restaurants) {
        return null;
    }

    public Mono<Boolean> evictCacheRestaurant(String key) {
        return null;
    }

    public Mono<Void> evictCacheAllRestaurant() {
        return null;
    }

}
