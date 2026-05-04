package com.debuggeandoideas.eats_hub_catalog.handlers;

import com.debuggeandoideas.eats_hub_catalog.services.definitions.ReservationBusinessService;
import com.debuggeandoideas.eats_hub_catalog.validators.ReactiveValidator;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class ReservationHandler {

    private final ReservationBusinessService reservationBusinessService;
    private final ReactiveValidator validator;

    public Mono<ServerResponse> postReservation(ServerRequest serverRequest){
        return Mono.empty();
    }

    public Mono<ServerResponse> getReservationById(ServerRequest serverRequest){
        return Mono.empty();
    }

    public Mono<ServerResponse> updateReservation(ServerRequest serverRequest){
        return Mono.empty();
    }

    public Mono<ServerResponse> deleteReservation(ServerRequest serverRequest){
        return Mono.empty();
    }

    private Mono<UUID> parseUUID(String uuid){
        try {
             return Mono.just(UUID.fromString(uuid));
         } catch (IllegalArgumentException e) {
             return Mono.error(new ValidationException("Invalid UUID: "+uuid));
         }
    }
}
