package com.hellFire.video_service.websocket;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ChatMessage {
    private String roomId;
    private String fromUserId;
    private String content;
    @Builder.Default
    private Instant timestamp = Instant.now();
}
