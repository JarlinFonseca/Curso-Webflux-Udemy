package com.debuggeandoideas.eats_hub_catalog.services.impls;

import com.debuggeandoideas.eats_hub_catalog.dtos.requests.ReservationRequest;
import com.debuggeandoideas.eats_hub_catalog.dtos.responses.ReservationResponse;
import com.debuggeandoideas.eats_hub_catalog.enums.ReservationStatusEnum;
import com.debuggeandoideas.eats_hub_catalog.mappers.ReservationMapper;
import com.debuggeandoideas.eats_hub_catalog.services.definitions.ReservationBusinessService;
import com.debuggeandoideas.eats_hub_catalog.services.definitions.ReservationCrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationBusinessServiceImpl implements ReservationBusinessService {

    private final ReservationCrudService reservationCrudService;
    private final ReservationMapper reservationMapper;

    @Override
    public Mono<String> createReservation(ReservationRequest reservation) {
        log.info("Creating reservation");
        return Mono.just(reservation)
                .transform(reservationMapper::toCollectionMono)
                .flatMap(this.reservationCrudService::createReservation)
                .map(savedReservation -> {
                    log.info("Creating reservation with id {} completed", savedReservation.getId());
                    return savedReservation.getId().toString();
                });
    }

    @Override
    public Mono<ReservationResponse> readByReservationId(UUID id) {
        log.info("Reading reservation with id {}", id);
        return this.reservationCrudService.readByReservationId(id)
                .transform(reservationMapper::toResponseMono)
                .doOnSuccess(reservation -> log.info("Reading reservation with id {} completed", id));
    }

    @Override
    public Flux<ReservationResponse> readByRestaurantId(UUID restaurantId, ReservationStatusEnum status) {
        log.info("Reading reservations for restaurant {} with id {}", restaurantId, status);
        return this.reservationCrudService.readByRestaurantId(restaurantId, status)
                .transform(reservationMapper::toResponseFlux)
                .doOnComplete(() -> log.info("Reading reservations for restaurant {} with id {} completed", restaurantId, status));
    }

    @Override
    public Mono<ReservationResponse> updateReservation(UUID id, ReservationRequest reservation) {
        log.info("Updating reservation with id {}", id);
        return null;
    }

    @Override
    public Mono<Void> deleteReservation(UUID id) {
        log.info("Deleting reservation with id {}", id);
        return null;
    }
}
