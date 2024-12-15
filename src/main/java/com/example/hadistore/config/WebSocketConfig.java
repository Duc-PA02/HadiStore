package com.example.hadistore.config;

import com.example.hadistore.handler.NotificationWebSocketHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final static String NOTIFICATION_ENDPOINT = "v1/notification";
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getNotificationWebSocketHandler(), NOTIFICATION_ENDPOINT)
                .setAllowedOrigins("*");
    }
    @Bean
    public WebSocketHandler getNotificationWebSocketHandler() {
        return new NotificationWebSocketHandle();
    }
}
