package org.jarlin.notification_system;

import org.jarlin.notification_system.models.NotificationEvent;
import org.jarlin.notification_system.models.NotificationStatus;
import org.jarlin.notification_system.models.Priority;
import org.jarlin.notification_system.service.EmailService;
import org.jarlin.notification_system.service.NotificationService;
import org.jarlin.notification_system.service.PhoneService;
import org.jarlin.notification_system.service.TeamsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;

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

            this.notificationSystem = new NotificationSystem(this.mockTeamService, this.mockEmailService, this.mockPhoneService);
    }

    @Test
    @DisplayName("Should send events with LOW priority")
    void testLowPriority(){
        NotificationEvent event = this.createTestEvent(Priority.LOW);
        this.notificationSystem.publishEvent(event);

        verify(this.mockTeamService, times(1)).sendNotification(any());
        verify(this.mockEmailService, never()).sendNotification(any());
        verify(this.mockPhoneService, never()).sendNotification(any());

        assert(this.teamsCallCount.get() == 1);
        assert(this.emailCallCount.get() == 0);
        assert(this.phoneCallCount.get() == 0);
    }

    @Test
    @DisplayName("Should send events with MEDIUM priority")
    void testMediumPriority(){
        NotificationEvent event = this.createTestEvent(Priority.MEDIUM);
        this.notificationSystem.publishEvent(event);

        verify(this.mockTeamService, times(1)).sendNotification(any());
        verify(this.mockEmailService, times(1)).sendNotification(any());
        verify(this.mockPhoneService, never()).sendNotification(any());

        assert(this.teamsCallCount.get() == 1);
        assert(this.emailCallCount.get() == 1);
        assert(this.phoneCallCount.get() == 0);
    }

    @Test
    @DisplayName("Should send events with HIGH priority")
    void testHighPriority(){
        NotificationEvent event = this.createTestEvent(Priority.HIGH);
        this.notificationSystem.publishEvent(event);

        verify(this.mockTeamService, times(1)).sendNotification(any());
        verify(this.mockEmailService, times(1)).sendNotification(any());
        verify(this.mockPhoneService, times(1)).sendNotification(any());

        assert(this.teamsCallCount.get() == 1);
        assert(this.emailCallCount.get() == 1);
        assert(this.phoneCallCount.get() == 1);
    }

    @Test
    @DisplayName("Should history keep last 3 events")
    void shouldHistoryKeep3Events(){
        NotificationEvent testEvent1 = this.createTestEvent(Priority.LOW);
        NotificationEvent testEvent2 = this.createTestEvent(Priority.MEDIUM);
        NotificationEvent testEvent3 = this.createTestEvent(Priority.HIGH);

        this.notificationSystem.publishEvent(testEvent1);
        this.notificationSystem.publishEvent(testEvent2);
        this.notificationSystem.publishEvent(testEvent3);

        StepVerifier.create(notificationSystem.getNotificationHistory().take(3))
                .expectNextCount(3)
                .verifyComplete();

    }

    private NotificationEvent createTestEvent(Priority priority) {
        return NotificationEvent.builder()
                .id(UUID.randomUUID().toString())
                .source("TEST")
                .message("Test msg with priority: " + priority.toString())
                .priority(priority)
                .timestamp(LocalDateTime.now())
                .status(NotificationStatus.PENDING)
                .build();
    }
    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
