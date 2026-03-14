package com.hellFire.HireFlowEmailService.dtos.requests;

import lombok.Data;

@Data
public class NotificationEmailRequest {
    private String to;
    private String subject;
    private String message;
}
