package com.hellFire.HireFlowEmailService.controllers;

import com.hellFire.HireFlowEmailService.dtos.requests.EmailVerificationRequest;
import com.hellFire.HireFlowEmailService.dtos.requests.WelcomeEmailRequest;
import com.hellFire.HireFlowEmailService.dtos.response.ApiResponse;
import com.hellFire.HireFlowEmailService.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<String>> sendEmail(@RequestBody EmailVerificationRequest request) {

        emailService.verification(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Email sent successfully",
                        "Verification email sent"
                )
        );
    }

    @PostMapping("/welcome")
    public ResponseEntity<ApiResponse<String>> sendWelcomeEmail(
            @RequestBody WelcomeEmailRequest request
    ) {

        emailService.sendWelcomeEmail(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Welcome email sent successfully",
                        "Welcome email sent"
                )
        );
    }

}
