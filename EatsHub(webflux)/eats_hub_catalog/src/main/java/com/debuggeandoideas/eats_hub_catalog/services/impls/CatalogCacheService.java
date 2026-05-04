package com.debuggeandoideas.eats_hub_catalog.services.impls;

import com.debuggeandoideas.eats_hub_catalog.dtos.responses.RestaurantResponse;
import com.debuggeandoideas.eats_hub_catalog.enums.PriceEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogCacheService {

    private final ReactiveRedisTemplate<String, RestaurantResponse> redisTemplate;
    private final ReactiveRedisTemplate<String, List<RestaurantResponse>> redisListTemplate;

    private static final Duration DEFAULT_TTL = Duration.ofHours(1);
    private final String KEY_PREFIX = "restaurant:";

    public Mono<RestaurantResponse> getCacheRestaurant(String key) {
        return this.redisTemplate
                .opsForValue()
                .get(KEY_PREFIX + key)
                .doOnNext(restaurantResponse -> log.info("Get cached restaurant: {}", restaurantResponse.getName()))
                .doOnSubscribe(subscription -> log.info("Looking restaurant with key: {}", key));
    }

    public Mono<RestaurantResponse> cacheRestaurant(String key, RestaurantResponse restaurant) {
        return this.redisTemplate
                .opsForValue()
                .set(KEY_PREFIX + key, restaurant, DEFAULT_TTL)
                .thenReturn(restaurant)
                .doOnNext(restaurantResponse -> log.info("Cached restaurant: {}", restaurantResponse.getName()))
                .doOnSubscribe(subscription -> log.info("Caching restaurant with key: {}", key));
    }

    public Flux<RestaurantResponse> getCacheRestaurants(String key) {
        return this.redisListTemplate
                .opsForValue()
                .get(KEY_PREFIX + key)
                .flatMapMany(Flux::fromIterable)
                .doOnNext(restaurantResponse -> log.info("Cache hit for list key: {}", key))
                .doOnSubscribe(subscription -> log.info("Looking in cache for list key: {}", key));

    }

    public Flux<RestaurantResponse> cacheRestaurants(String key, Flux<RestaurantResponse> restaurants) {
        return restaurants.collectList()
                .flatMap(restaurantList -> this.redisListTemplate
                        .opsForValue()
                        .set(KEY_PREFIX + key, restaurantList, DEFAULT_TTL)
                        .thenReturn(restaurantList))
                .flatMapMany(Flux::fromIterable)
                .doOnComplete(() -> log.info("Cached restaurant list with key: {}", key));
    }

    public Mono<Boolean> evictCacheRestaurant(String key) {
        return this.redisTemplate
                .delete(KEY_PREFIX + key)
                .map(count -> count > 0)
                .doOnNext(isDeleted -> {
                    if(Boolean.TRUE.equals(isDeleted)) log.info("Cache evicted restaurant with key: {}", key);
                });
    }

    public Mono<Void> evictCacheAllRestaurant() {
        return this.redisListTemplate.getConnectionFactory()
                .getReactiveConnection()
                .serverCommands()
                .flushAll()
                .then(Mono.fromRunnable(() -> log.info("Cache evicted all restaurants")));
    }

    public static String buildNameKey(String name){
        return "name:"+name.toLowerCase();
    }

    public static String buildCuisineTypeKey(String cuisineType){
        return "cuisine:"+cuisineType.toLowerCase();
    }

    public static String buildCityKey(String city){
        return "city:"+city.toLowerCase();
    }

    public static String buildPriceKey(List<PriceEnum> prices){
        String pricesList = prices.stream()
                .map(PriceEnum::toString)
                .sorted()
                .collect(Collectors.joining(","));

        return "price:"+pricesList;
    }


}
