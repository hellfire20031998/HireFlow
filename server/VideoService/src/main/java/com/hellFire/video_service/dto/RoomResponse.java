package com.hellFire.video_service.dto;

import lombok.Builder;
import lombok.Data;

import com.hellFire.video_service.model.Participant;

import java.util.List;

@Data
@Builder
public class RoomResponse {
    private String roomId;
    private String interviewId;
    private String hostId;
    private String joinUrl;
    private List<Participant> participants;
}

