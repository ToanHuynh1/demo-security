package com.demo_security.demo_security.service;

import com.demo_security.demo_security.events.UserCreatedEvent;
import com.demo_security.demo_security.events.FileUploadedEvent;
import com.demo_security.demo_security.events.EmailNotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(EventConsumerService.class);

    @RabbitListener(queues = "user.created.queue")
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        logger.info("Processing user created event: {}", event);
        // Simulate processing: send welcome email, create user profile, etc.        
        try {  
            Thread.sleep(100); // Simulate processing time
            logger.info("User created event processed successfully for user: {}", event.getUserId()); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error processing user created event", e);
        }
    }

    @RabbitListener(queues = "file.uploaded.queue")
    public void handleFileUploadedEvent(FileUploadedEvent event) {
        logger.info("Processing file uploaded event: {}", event);
        // Simulate processing: virus scan, thumbnail generation, etc.
        try {
            Thread.sleep(200); // Simulate processing time
            logger.info("File uploaded event processed successfully for file: {}", event.getFileName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error processing file uploaded event", e);
        }
    }

    @RabbitListener(queues = "email.notification.queue")
    public void handleEmailNotificationEvent(EmailNotificationEvent event) {
        logger.info("Processing email notification event: {}", event);
        // Simulate processing: send email via SMTP
        try {
            Thread.sleep(150); // Simulate processing time
            logger.info("Email notification sent successfully to: {}", event.getTo());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error processing email notification event", e);
        }
    }
}