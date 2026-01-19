package com.hellFire.AuthService.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailVerificationRequest {
    private String to;
    private String otp;
}
