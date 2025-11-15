package com.demo_security.demo_security.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;





// 3. Listener: Lắng nghe sự kiện
@Component
class CustomEventListener {
    private static final Logger logger = LoggerFactory.getLogger(CustomEventListener.class);
    @EventListener
    public void handleCustomEvent(CustomEvent event) {
        logger.info("[EVENT] Nhận sự kiện: {}", event.getMessage());
    }
}
