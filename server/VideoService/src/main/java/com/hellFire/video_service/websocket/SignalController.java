package com.hellFire.video_service.websocket;

import com.hellFire.video_service.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SignalController {

    private final SimpMessagingTemplate messagingTemplate;
    private final RoomService roomService;
    private final SessionRegistry sessionRegistry;

    @MessageMapping("/signal")
    public void handleSignal(@Payload SignalMessage message, SimpMessageHeaderAccessor headerAccessor) {
        if (message == null || message.getRoomId() == null) {
            log.warn("Received invalid signal message: {}", message);
            return;
        }

        String sessionId = headerAccessor.getSessionId();

        log.debug("Signal received: type={}, roomId={}, from={}, to={}, session={}",
                message.getType(), message.getRoomId(), message.getFromUserId(), message.getToUserId(), sessionId);

        if (message.getType() == SignalType.JOIN) {
            sessionRegistry.register(sessionId, message.getFromUserId(), message.getFromUsername(), message.getRoomId());
            roomService.joinRoom(message.getRoomId(), message.getFromUserId(), message.getFromUsername());
            log.info("Session {} registered: user={} ({}), room={}", sessionId, message.getFromUserId(), message.getFromUsername(), message.getRoomId());
        }

        if (message.getType() == SignalType.LEAVE) {
            sessionRegistry.unregister(sessionId);
            roomService.leaveRoom(message.getRoomId(), message.getFromUserId());
            log.info("User {} left room {} via LEAVE signal", message.getFromUserId(), message.getRoomId());
        }

        String destination = "/topic/room/" + message.getRoomId();
        messagingTemplate.convertAndSend(destination, message);
    }
}
