package org.jarlin.notification_system;

import org.jarlin.notification_system.models.NotificationEvent;
import org.jarlin.notification_system.service.EmailService;
import org.jarlin.notification_system.service.NotificationService;
import org.jarlin.notification_system.service.PhoneService;
import org.jarlin.notification_system.service.TeamsService;
import org.junit.jupiter.api.BeforeEach;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;
import static org.mockito.AdditionalMatchers.*;

public class NotificationSystemTest {

    private NotificationService mockTeamService;
    private NotificationService mockEmailService;
    private NotificationService mockPhoneService;

    private NotificationSystem notificationSystem;

    private AtomicInteger teamsCallCount;
    private AtomicInteger emailCallCount;
    private AtomicInteger phoneCallCount;

    @BeforeEach
    void setup() {
        teamsCallCount = new AtomicInteger(0);
        emailCallCount = new AtomicInteger(0);
        phoneCallCount = new AtomicInteger(0);

       this.mockTeamService = mock(TeamsService.class);
       this.mockEmailService = mock(EmailService.class);
       this.mockPhoneService = mock(PhoneService.class);

       when(this.mockTeamService.sendNotification(any(NotificationEvent.class)))
               .thenAnswer(invocation -> {
                   this.teamsCallCount.incrementAndGet();
                     return Mono.just(true);
               });

         when(this.mockEmailService.sendNotification(any(NotificationEvent.class)))
                .thenAnswer(invocation -> {
                     this.emailCallCount.incrementAndGet();
                        return Mono.just(true);
                });

            when(this.mockPhoneService.sendNotification(any(NotificationEvent.class)))
                .thenAnswer(invocation -> {
                     this.phoneCallCount.incrementAndGet();
                        return Mono.just(true);
                });
    }
}
