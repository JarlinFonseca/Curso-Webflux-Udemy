package com.debuggeandoideas.eats_hub_catalog.validators;

import com.debuggeandoideas.eats_hub_catalog.collections.ReservationCollection;
import com.debuggeandoideas.eats_hub_catalog.repositories.ReservationRepository;
import com.debuggeandoideas.eats_hub_catalog.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationValidator {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;

    public <T>Mono<Void> applyValidations(T input, List<BusinessValidator<T>> validations){
        return null;
    }

    public BusinessValidator<ReservationCollection> validateRestaurantNotClosed(ReservationCollection reservation){
        return null;
    }

    public BusinessValidator<ReservationCollection> validateAvailability(ReservationCollection reservation){
        return null;
    }

    public BusinessValidator<ReservationCollection> validateRestaurantIDBeforeUpdate(ReservationCollection reservation){
        return null;
    }


}
