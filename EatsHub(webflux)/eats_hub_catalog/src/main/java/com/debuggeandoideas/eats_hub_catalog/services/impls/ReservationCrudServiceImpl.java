package com.debuggeandoideas.eats_hub_catalog.services.impls;

import com.debuggeandoideas.eats_hub_catalog.collections.ReservationCollection;
import com.debuggeandoideas.eats_hub_catalog.enums.ReservationStatusEnum;
import com.debuggeandoideas.eats_hub_catalog.exeptions.ResourceNotFoundException;
import com.debuggeandoideas.eats_hub_catalog.repositories.ReservationRepository;
import com.debuggeandoideas.eats_hub_catalog.repositories.RestaurantRepository;
import com.debuggeandoideas.eats_hub_catalog.services.definitions.ReservationCrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationCrudServiceImpl implements ReservationCrudService {

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Mono<ReservationCollection> createReservation(ReservationCollection reservation) {
        return this.restaurantRepository.findById(UUID.fromString(reservation.getRestaurantId()))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Restaurant not found")))
                .flatMap(restaurant -> {
                    if(Objects.isNull(reservation.getStatus())){
                        reservation.setStatus(ReservationStatusEnum.PENDING);
                    }

                    log.info("Creating reservation with id {} for restaurant {}", reservation.getId(), restaurant.getName());
                    return this.reservationRepository.save(reservation);
                });
    }

    @Override
    public Mono<ReservationCollection> readByReservationId(UUID id) {
        return null;
    }

    @Override
    public Flux<ReservationCollection> readByRestaurantId(String restaurantId, ReservationStatusEnum status) {
        return null;
    }

    @Override
    public Mono<ReservationCollection> updateReservation(UUID id, ReservationCollection reservation) {
        return null;
    }

    @Override
    public Mono<Void> deleteReservation(UUID id) {
        return null;
    }
}
