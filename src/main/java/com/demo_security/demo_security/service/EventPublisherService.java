package com.demo_security.demo_security.service;

import com.demo_security.demo_security.config.RabbitMQConfig;
import com.demo_security.demo_security.events.UserCreatedEvent;
import com.demo_security.demo_security.events.FileUploadedEvent;
import com.demo_security.demo_security.events.EmailNotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventPublisherService {

    private static final Logger logger = LoggerFactory.getLogger(EventPublisherService.class);
    private final RabbitTemplate rabbitTemplate;

    public EventPublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserCreatedEvent(Long userId, String username) {
        UserCreatedEvent event = new UserCreatedEvent(userId, username, null);
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.USER_EVENTS_EXCHANGE,
            RabbitMQConfig.USER_CREATED_ROUTING_KEY,
            event
        );
        logger.info("Published user created event for user: {}", username);
    }

    public void publishUserCreatedEvent(UserCreatedEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.USER_EVENTS_EXCHANGE,
            RabbitMQConfig.USER_CREATED_ROUTING_KEY,
            event
        );
        logger.info("Published user created event for user: {}", event.getUsername());
    }

    public void publishFileUploadedEvent(String fileName, String fileUrl) {
        FileUploadedEvent event = new FileUploadedEvent(fileName, fileUrl, 0L, null);
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.FILE_UPLOAD_EXCHANGE,
            RabbitMQConfig.FILE_UPLOADED_ROUTING_KEY,
            event
        );
        logger.info("Published file uploaded event for file: {}", fileName);
    }

    public void publishEmailNotification(String to, String subject, String body) {
        EmailNotificationEvent event = new EmailNotificationEvent(to, subject, body, "notification");
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EMAIL_EXCHANGE,
            RabbitMQConfig.EMAIL_SEND_ROUTING_KEY,
            event
        );
        logger.info("Published email notification to: {}", to);
    }
}