package com.debuggeandoideas.eats_hub_catalog.mappers;

import com.debuggeandoideas.eats_hub_catalog.collections.ReservationCollection;
import com.debuggeandoideas.eats_hub_catalog.dtos.requests.ReservationRequest;
import com.debuggeandoideas.eats_hub_catalog.dtos.responses.ReservationResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "dateTime", expression = "java(collection.getDate() + \",\" + collection.getTime())")
    ReservationResponse toResponse(ReservationCollection collection);

    @Mapping(target = "date", expression = "java(extractDate(request.getDateTime()))")
    @Mapping(target = "time", expression = "java(extractTime(request.getDateTime()))")
    @Mapping(target = "notes", source = "comment", defaultValue = "")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "staus", ignore = true)
    ReservationCollection toCollection(ReservationRequest request);

    default Flux<ReservationResponse> toResponseFlux(Flux<ReservationCollection> collections) {
        return collections.map(this::toResponse);
    }

    default Mono<ReservationResponse> toResponseMono(Mono<ReservationCollection> collection) {
        return collection.map(this::toResponse);
    }

    default Flux<ReservationCollection> toCollectionFlux(Flux<ReservationRequest> requests) {
        return requests.map(this::toCollection);
    }

    default Mono<ReservationCollection> toCollectionMono(Mono<ReservationRequest> request) {
        return request.map(this::toCollection);
    }

    default String extractDate(String dateTime){
        return dateTime.split(",")[0];
    }

    default String extractTime(String dateTime){
        return dateTime.split(",")[1];
    }

    @AfterMapping
    default void splitDate(ReservationRequest request, @MappingTarget ReservationCollection collection){
        if(Objects.nonNull(request.getDateTime()) && request.getDateTime().contains(",")){
            var dateTimeSplit = request.getDateTime().split(",", 2);
            collection.setDate(dateTimeSplit[0]);
            collection.setTime(dateTimeSplit[1]);
        }
    }
}
