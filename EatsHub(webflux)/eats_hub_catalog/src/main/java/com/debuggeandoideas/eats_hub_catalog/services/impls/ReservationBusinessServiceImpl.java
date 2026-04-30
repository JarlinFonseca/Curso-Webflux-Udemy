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
        return null;
    }

    @Override
    public Mono<ReservationResponse> readByReservationId(UUID id) {
        return null;
    }

    @Override
    public Flux<ReservationResponse> readByRestaurantId(UUID restaurantId, ReservationStatusEnum status) {
        return null;
    }

    @Override
    public Mono<ReservationResponse> updateReservation(UUID id, ReservationRequest reservation) {
        return null;
    }

    @Override
    public Mono<Void> deleteReservation(UUID id) {
        return null;
    }
}
