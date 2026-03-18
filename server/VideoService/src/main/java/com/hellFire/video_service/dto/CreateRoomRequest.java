package com.hellFire.video_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRoomRequest {

    @NotBlank
    private String interviewId;

    @NotBlank
    private String hostId;

    @NotBlank
    private String hostName;
}

