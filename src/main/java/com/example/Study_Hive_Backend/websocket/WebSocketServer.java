package com.example.Study_Hive_Backend.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketServer extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = extractUserId(session);
        if (userId != null) {
            sessions.put(userId, session);
            System.out.println("WebSocket connection established for user: " + userId);
        } else {
            System.err.println("Failed to establish WebSocket connection: userId is null");
            session.close(CloseStatus.PROTOCOL_ERROR.withReason("UserId not provided"));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages if needed
        String userId = extractUserId(session);
        if (userId != null) {
            System.out.println("Received message from user " + userId + ": " + message.getPayload());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = extractUserId(session);
        if (userId != null) {
            sessions.remove(userId);
            System.out.println("WebSocket connection closed for user: " + userId);
        }
    }

    public void sendNotification(String userId, String message) {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
                System.out.println("Notification sent to user " + userId + ": " + message);
            } catch (IOException e) {
                System.err.println("Error sending notification to user " + userId + ": " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("User " + userId + " is not connected. Notification not sent.");
        }
    }

    private String extractUserId(WebSocketSession session) {
        Object userIdObj = session.getAttributes().get("userId");
        if (userIdObj != null) {
            return userIdObj.toString();
        }
        // Try to get userId from query parameter
        String query = session.getUri().getQuery();
        if (query != null && query.contains("userId=")) {
            String[] params = query.split("&");
            for (String param : params) {
                if (param.startsWith("userId=")) {
                    return param.split("=")[1];
                }
            }
        }
        return null;
    }
}

