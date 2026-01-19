package com.hellFire.AuthService.controllers;

import com.hellFire.AuthService.dto.responses.ApiResponse;
import com.hellFire.AuthService.services.EmailVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailVerificationController {
   private final EmailVerificationService emailVerificationService;

    public EmailVerificationController(EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("generate-verification")
    public ResponseEntity<ApiResponse<String>> generateEmailVerification() {
        emailVerificationService.createEmailVerificationRequest();

        return ResponseEntity.ok(ApiResponse.success("Verification email sent successfully", "OTP generated & email sent"));
    }
}
