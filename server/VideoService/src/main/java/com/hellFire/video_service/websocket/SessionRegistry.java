package com.hellFire.video_service.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks which WebSocket session belongs to which user in which room.
 * Used by SignalController (on JOIN/LEAVE) and WebSocketEventListener (on disconnect).
 */
@Component
public class SessionRegistry {

    private final Map<String, SessionInfo> sessions = new ConcurrentHashMap<>();

    public void register(String sessionId, String userId, String username, String roomId) {
        sessions.put(sessionId, new SessionInfo(userId, username, roomId));
    }

    public SessionInfo unregister(String sessionId) {
        return sessions.remove(sessionId);
    }

    public SessionInfo get(String sessionId) {
        return sessions.get(sessionId);
    }

    @Data
    @AllArgsConstructor
    public static class SessionInfo {
        private String userId;
        private String username;
        private String roomId;
    }
}
