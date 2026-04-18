package org.jarlin.notification_system.service;

import lombok.extern.slf4j.Slf4j;
import org.jarlin.notification_system.models.NotificationEvent;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class TeamsService implements NotificationService {

    @Override
    public Mono<Boolean> sendNotification(NotificationEvent event) {
        return Mono.fromCallable(() -> {
          Thread.sleep(150);

          //simulate error with 10% probability
            if(ThreadLocalRandom.current().nextInt(10)  == 0) {
                throw new RuntimeException("Error on send msg in Teamns");
            }
            log.info("Msg in Teams OK: {}", event);
            return true;
        });
    }
}
