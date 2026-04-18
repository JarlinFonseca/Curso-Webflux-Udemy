package org.jarlin.notification_system.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationEvent {
    private String source;
    private String message;
    Priority priority;
    LocalDateTime timestamp;
}
