package com.hellFire.HireFlowEmailService.dtos.requests;


import lombok.Data;

@Data
public class EmailVerificationRequest {
    private String to;
    private String otp;
}
