package com.debuggeandoideas.customer_manager.clients;

import com.debuggeandoideas.customer_manager.dtos.ReservationRequest;
import com.debuggeandoideas.customer_manager.dtos.ReservationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ReservationCrudClientImpl implements ReservationCrudClient {

    private final WebClient webClient;
    private static final String RESOURCE = "catalog/reservation/";

    @Autowired
    public ReservationCrudClientImpl(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    @Override
    public Mono<ReservationResponse> create(ReservationRequest reservationRequest) {
        return null;
    }

    @Override
    public Mono<ReservationResponse> read(String uuid) {
        return null;
    }

    @Override
    public Mono<ReservationResponse> update(String uuid, ReservationRequest reservationRequest) {
        return null;
    }

    @Override
    public Mono<Void> delete(String uuid) {
        return null;
    }
}
