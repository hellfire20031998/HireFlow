package com.hellFire.video_service.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Room {
    private String roomId;
    private String interviewId;
    private String hostId;
    private List<Participant> participants;
}

