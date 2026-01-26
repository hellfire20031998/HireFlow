package com.hellFire.HireFlowEmailService.dtos.requests;

import lombok.Data;

@Data
public class WelcomeEmailRequest {
    private String to;
    private String username;
}
