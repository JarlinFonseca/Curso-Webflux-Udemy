package org.jarlin.notification_system.service;

import org.jarlin.notification_system.models.NotificationEvent;
import reactor.core.publisher.Mono;

public interface NotificationService {

    Mono<Boolean> sendNotification(NotificationEvent event);
}
