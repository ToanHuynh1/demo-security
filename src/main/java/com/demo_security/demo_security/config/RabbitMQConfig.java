package com.demo_security.demo_security.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String USER_EVENTS_QUEUE = "user.events.queue";
    public static final String FILE_UPLOAD_QUEUE = "file.upload.queue";
    public static final String EMAIL_NOTIFICATION_QUEUE = "email.notification.queue";

    public static final String USER_EVENTS_EXCHANGE = "user.events.exchange";
    public static final String FILE_UPLOAD_EXCHANGE = "file.upload.exchange";
    public static final String EMAIL_EXCHANGE = "email.exchange";

    public static final String USER_CREATED_ROUTING_KEY = "user.created";
    public static final String FILE_UPLOADED_ROUTING_KEY = "file.uploaded";
    public static final String EMAIL_SEND_ROUTING_KEY = "email.send";

    @Bean
    public Queue userEventsQueue() {
        return QueueBuilder.durable(USER_EVENTS_QUEUE).build();
    }

    @Bean
    public Queue fileUploadQueue() {
        return QueueBuilder.durable(FILE_UPLOAD_QUEUE).build();
    }

    @Bean
    public Queue emailNotificationQueue() {
        return QueueBuilder.durable(EMAIL_NOTIFICATION_QUEUE).build();
    }

    @Bean
    public TopicExchange userEventsExchange() {
        return new TopicExchange(USER_EVENTS_EXCHANGE);
    }

    @Bean
    public TopicExchange fileUploadExchange() {
        return new TopicExchange(FILE_UPLOAD_EXCHANGE);
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public Binding userEventsBinding() {
        return BindingBuilder.bind(userEventsQueue())
                .to(userEventsExchange())
                .with(USER_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding fileUploadBinding() {
        return BindingBuilder.bind(fileUploadQueue())
                .to(fileUploadExchange())
                .with(FILE_UPLOADED_ROUTING_KEY);
    }

    @Bean
    public Binding emailNotificationBinding() {
        return BindingBuilder.bind(emailNotificationQueue())
                .to(emailExchange())
                .with(EMAIL_SEND_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}