package com.hellFire.video_service.websocket;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignalMessage {
    private SignalType type;
    private String roomId;
    private String fromUserId;
    private String fromUsername;
    private String toUserId;
    private String sdp;
    private String candidate;
}

