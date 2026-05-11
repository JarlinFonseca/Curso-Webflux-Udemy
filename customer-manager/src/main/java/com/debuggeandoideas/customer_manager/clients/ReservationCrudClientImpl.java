package com.debuggeandoideas.customer_manager.clients;

import com.debuggeandoideas.customer_manager.dtos.ReservationRequest;
import com.debuggeandoideas.customer_manager.dtos.ReservationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ReservationCrudClientImpl implements ReservationCrudClient {

    private final WebClient webClient;
    private static final String RESOURCE = "catalog/reservation/";
    private static final String ERROR_MSJ_4XX = "Error while creating reservation";
    private static final String ERROR_MSJ_5XX = "Error while calling reservation service";

    @Autowired
    public ReservationCrudClientImpl(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    @Override
    public Mono<ReservationResponse> create(ReservationRequest reservationRequest) {
        log.info("Creating reservation request with restaurant id: {}", reservationRequest.getRestaurantId());

        return this.webClient
                .post()
                .uri(RESOURCE)
                .bodyValue(reservationRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new IllegalArgumentException(ERROR_MSJ_4XX)))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new IllegalArgumentException(ERROR_MSJ_5XX)))
                .bodyToMono(ReservationResponse.class)
                .doOnSuccess(res -> log.info("Reservation created successfully: {}", res));
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
