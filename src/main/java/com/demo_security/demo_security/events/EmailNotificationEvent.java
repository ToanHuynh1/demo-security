package com.demo_security.demo_security.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationEvent {
    private String to;
    private String subject;
    private String body;
    private String type;
}