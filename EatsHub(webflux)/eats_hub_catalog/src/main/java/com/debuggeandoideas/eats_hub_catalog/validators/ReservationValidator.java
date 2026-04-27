package com.debuggeandoideas.eats_hub_catalog.validators;

import com.debuggeandoideas.eats_hub_catalog.clients.PlannerMSClient;
import com.debuggeandoideas.eats_hub_catalog.collections.ReservationCollection;
import com.debuggeandoideas.eats_hub_catalog.collections.RestaurantCollection;
import com.debuggeandoideas.eats_hub_catalog.repositories.ReservationRepository;
import com.debuggeandoideas.eats_hub_catalog.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationValidator {
    private final RestaurantRepository restaurantRepository;
    private final PlannerMSClient plannerMSClient;

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

    private boolean isRestaurantClosed(RestaurantCollection restaurant, String reservationTime){
        try {

            if(Objects.isNull(restaurant.getCloseAt() ) || Objects.isNull(reservationTime)){
                return true;
            }

            LocalTime closeLocalTime = LocalTime.parse(restaurant.getCloseAt(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime reservationLocalTime = LocalTime.parse(reservationTime, DateTimeFormatter.ofPattern("HH:mm"));

            return reservationLocalTime.isAfter(closeLocalTime);

        } catch (Exception e) {
            log.error("Error on verify close tome", e);
            return true;
        }
    }


}
