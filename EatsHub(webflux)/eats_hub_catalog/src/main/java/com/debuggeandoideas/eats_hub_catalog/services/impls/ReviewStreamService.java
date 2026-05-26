package com.debuggeandoideas.eats_hub_catalog.services.impls;

import com.debuggeandoideas.eats_hub_catalog.dtos.events.ReviewEvent;
import com.debuggeandoideas.eats_hub_catalog.services.definitions.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewStreamService {

    private final ReviewService reviewService;

    public Mono<Void> processReview(ReviewEvent review){
        return Mono.empty();
    }
}
