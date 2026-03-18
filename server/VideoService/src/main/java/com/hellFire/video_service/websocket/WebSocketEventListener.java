package com.hellFire.video_service.websocket;

import com.hellFire.video_service.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Detects when a WebSocket session disconnects (e.g. browser tab closed)
 * and automatically removes the user from their room + notifies other participants.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SessionRegistry sessionRegistry;
    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        SessionRegistry.SessionInfo info = sessionRegistry.unregister(sessionId);

        if (info == null) {
            return;
        }

        String userId = info.getUserId();
        String username = info.getUsername();
        String roomId = info.getRoomId();

        log.info("WebSocket disconnect detected: session={}, user={} ({}), room={}", sessionId, userId, username, roomId);

        roomService.leaveRoom(roomId, userId);

        SignalMessage leaveMessage = SignalMessage.builder()
                .type(SignalType.LEAVE)
                .roomId(roomId)
                .fromUserId(userId)
                .fromUsername(username)
                .build();

        String destination = "/topic/room/" + roomId;
        messagingTemplate.convertAndSend(destination, leaveMessage);

        log.info("Broadcast LEAVE for disconnected user {} ({}) in room {}", userId, username, roomId);
    }
}
