//package com.example.Study_Hive_Backend.invitation.controller;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Controller
//public class RabbitMQController {
//
//    private static final Logger logger = LoggerFactory.getLogger(RabbitMQController.class);
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    @RabbitListener(queues = "#{rabbitMQConfig.NOTIFICATION_QUEUE}")
//    public void receiveMessage(String message) {
//        logger.info("Received message: {}", message);
//
//        try {
//            // Validate and parse the message
//            if (message == null || message.isBlank()) {
//                logger.warn("Received an empty or null message, ignoring.");
//                return;
//            }
//
//            String[] parts = message.split(":", 2);
//            if (parts.length != 2) {
//                logger.warn("Invalid message format: {}", message);
//                return; // Skip invalid messages
//            }
//
//            String userId = parts[0].trim();
//            String notificationMessage = parts[1].trim();
//
//            if (userId.isEmpty() || notificationMessage.isEmpty()) {
//                logger.warn("Message contains empty userId or notification: {}", message);
//                return; // Skip messages with missing information
//            }
//
//            // Send the message to the specific user
//            logger.info("Sending notification to userId: {} with message: {}", userId, notificationMessage);
//            messagingTemplate.convertAndSendToUser(userId, "/queue/messages", notificationMessage);
//
//        } catch (Exception e) {
//            logger.error("Error processing RabbitMQ message: {}", message, e);
//        }
//    }
//}
