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
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

 class NotificationSystemTest {

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

    @Test
    @DisplayName("Should retry 3 attempts when phone service fail")
    void testRetryPhoneAttempts(){

        AtomicInteger attempts = new AtomicInteger(0);

        when(this.mockPhoneService.sendNotification(any(NotificationEvent.class)))
                .thenAnswer(invocation -> {
                   int currentAttempt = attempts.incrementAndGet();
                   if(currentAttempt <= 2) {
                          return Mono.error(new RuntimeException("Error on send msg in Phone call"));
                     } else {
                          this.phoneCallCount.incrementAndGet();
                          return Mono.just(true);
                   }
                });

        NotificationEvent event = this.createTestEvent(Priority.HIGH);
        this.notificationSystem.publishEvent(event);

        this.sleep(500);
        assert attempts.get() >= 3;
        assert this.phoneCallCount.get() == 1;
    }

    @Test
    @DisplayName("How to use virtual time")
    void testVirtualTime(){
        VirtualTimeScheduler scheduler = VirtualTimeScheduler.create();
        NotificationService teams = mock(NotificationService.class);
        NotificationService email = mock(NotificationService.class);
        NotificationService phone = mock(NotificationService.class);


        when(teams.sendNotification(any(NotificationEvent.class)))
                .thenAnswer(inv  -> Mono.just(true).delayElement(Duration.ofMillis(150), scheduler));

        when(email.sendNotification(any(NotificationEvent.class)))
                .thenAnswer(inv  -> Mono.just(true).delayElement(Duration.ofMillis(300), scheduler));

        when(phone.sendNotification(any(NotificationEvent.class)))
                .thenAnswer(inv  -> Mono.just(true).delayElement(Duration.ofMillis(1000), scheduler));

        NotificationSystem testSystem = new NotificationSystem(teams, email, phone);
        NotificationEvent event = this.createTestEvent(Priority.HIGH);
        testSystem.publishEvent(event);

        scheduler.advanceTimeBy(Duration.ofMillis(1500));

        StepVerifier.withVirtualTime( () -> testSystem.getNotificationHistory().take(1))
                .expectNextMatches(element -> element.getStatus() == NotificationStatus.DELIVERED)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should route many events by priority and keep only last 50 in history")
    void shouldRouteManyEventsAndKeepLast50InHistory() {
        int lowCount = 30;
        int mediumCount = 40;
        int highCount = 50;
        int totalEvents = lowCount + mediumCount + highCount;

        List<NotificationEvent> lowEvents = IntStream.range(0, lowCount)
                .mapToObj(i -> this.createTestEvent(Priority.LOW))
                .toList();
        List<NotificationEvent> mediumEvents = IntStream.range(0, mediumCount)
                .mapToObj(i -> this.createTestEvent(Priority.MEDIUM))
                .toList();
        List<NotificationEvent> highEvents = IntStream.range(0, highCount)
                .mapToObj(i -> this.createTestEvent(Priority.HIGH))
                .toList();

        lowEvents.forEach(this.notificationSystem::publishEvent);
        mediumEvents.forEach(this.notificationSystem::publishEvent);
        highEvents.forEach(this.notificationSystem::publishEvent);

        this.sleep(500);

        assertEquals(totalEvents, this.teamsCallCount.get());
        assertEquals(mediumCount + highCount, this.emailCallCount.get());
        assertEquals(highCount, this.phoneCallCount.get());

        StepVerifier.create(this.notificationSystem.getNotificationHistory().take(50).count())
                .expectNext(50L)
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
