package com.example.Study_Hive_Backend.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "studyhive.topic";
    public static final String NOTIFICATION_QUEUE = "studyhive.notifications";
    public static final String INVITATION_QUEUE = "studyhive.invitations";
    public static final String ROUTING_KEY = "user.*.notifications";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE);
    }

    @Bean
    public Queue invitationQueue() {
        return new Queue(INVITATION_QUEUE);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(notificationQueue).to(exchange).with("user.*.notifications");
    }

    @Bean
    public Binding invitationBinding(Queue invitationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(invitationQueue).to(exchange).with("user.*.invitations");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

