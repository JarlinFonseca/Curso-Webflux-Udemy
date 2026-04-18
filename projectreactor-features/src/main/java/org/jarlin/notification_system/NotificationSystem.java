package org.jarlin.notification_system;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jarlin.notification_system.models.NotificationEvent;
import org.jarlin.notification_system.service.EmailService;
import org.jarlin.notification_system.service.NotificationService;
import org.jarlin.notification_system.service.PhoneService;
import org.jarlin.notification_system.service.TeamsService;
import reactor.core.publisher.Sinks;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class NotificationSystem {

    private final Sinks.Many<NotificationEvent> mainEventSink;
    @Getter
    private final Sinks.Many<NotificationEvent> historeySink;

    private final NotificationService teamsService;
    private final NotificationService emailService;
    private final NotificationService phoneService;

    private final Sinks.One<NotificationEvent> teamsSink;
    private final Sinks.One<NotificationEvent> emailSink;
    private final Sinks.One<NotificationEvent> phoneSink;

    private final ConcurrentMap<String, NotificationEvent> notificationCache;

    public NotificationSystem() {
        this.mainEventSink = Sinks.many().multicast().onBackpressureBuffer();
        this.historeySink = Sinks.many().replay().limit(50);

        this.teamsSink = Sinks.one();
        this.emailSink = Sinks.one();
        this.phoneSink = Sinks.one();

        this.teamsService = new TeamsService();
        this.emailService = new EmailService();
        this.phoneService = new PhoneService();

        this.notificationCache = new ConcurrentHashMap<>();

    }
}
