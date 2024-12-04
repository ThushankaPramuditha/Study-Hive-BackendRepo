package com.example.Study_Hive_Backend.config;

import com.example.Study_Hive_Backend.websocket.WebSocketServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketServer webSocketServer;

    public WebSocketConfig(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketServer, "/ws")
                .setAllowedOrigins("http://127.0.0.1:5173","http://localhost:5173") // For development, replace with your frontend URL in production
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                        String query = request.getURI().getQuery();
                        if (query != null && query.contains("userId=")) {
                            String userId = query.split("userId=")[1].split("&")[0];
                            attributes.put("userId", userId);
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                               WebSocketHandler wsHandler, Exception exception) {
                        // No action needed after handshake
                    }
                });
    }
}

