package com.hellFire.video_service.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void handleChat(@Payload ChatMessage message) {
        if (message == null || message.getRoomId() == null || message.getContent() == null) {
            log.warn("Received invalid chat message: {}", message);
            return;
        }

        if (message.getTimestamp() == null) {
            message.setTimestamp(Instant.now());
        }

        log.debug("Chat message: roomId={}, from={}, content={}",
                message.getRoomId(), message.getFromUserId(), message.getContent());

        String destination = "/topic/room/" + message.getRoomId() + "/chat";
        messagingTemplate.convertAndSend(destination, message);
    }
}
