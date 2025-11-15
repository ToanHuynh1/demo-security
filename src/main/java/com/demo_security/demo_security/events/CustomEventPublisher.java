package com.demo_security.demo_security.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CustomEventPublisher {
    @Autowired
    private ApplicationEventPublisher publisher;
    public void publish(String msg) {
        publisher.publishEvent(new CustomEvent(this, msg));
    }
}
