package com.hellFire.AuthService.services;

import com.hellFire.AuthService.dto.responses.ApiResponse;
import com.hellFire.AuthService.dto.responses.EmailVerificationRequest;
import com.hellFire.AuthService.model.EmailVerificationToken;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.respositories.IEmailVerificationTokenRepository;
import com.hellFire.AuthService.utils.SecurityUtil;
import com.hellFire.AuthService.utils.Utils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class EmailVerificationService {

    private final IEmailVerificationTokenRepository emailVerificationTokenRepository;
    private final RestTemplate restTemplate;
    private final EmailVerificationTokenService emailVerificationTokenService;

    public EmailVerificationService(IEmailVerificationTokenRepository emailVerificationTokenRepository, RestTemplate restTemplate, EmailVerificationTokenService emailVerificationTokenService) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.restTemplate = restTemplate;
        this.emailVerificationTokenService = emailVerificationTokenService;
    }

    public String createEmailVerificationRequest() {
        User user = SecurityUtil.getCurrentUser();
        EmailVerificationToken token = emailVerificationTokenService.getToken(user);

        EmailVerificationRequest request =
                new EmailVerificationRequest(user.getEmail(), token.getToken());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EmailVerificationRequest> entity =
                new HttpEntity<>(request, headers);

        String url = "http://localhost:8081/email/verify";

        ResponseEntity<ApiResponse> response =
                restTemplate.postForEntity(url, entity, ApiResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            sendWelcomeMail(user.getEmail(), user.getUsername());
            return "Email sent successfully";
        } else {
            throw new RuntimeException("Failed to send email");
        }
    }

    private void sendWelcomeMail(String email, String username) {

        String url = "http://localhost:8081/email/welcome";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("to", email);
        requestBody.put("username", username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<ApiResponse<String>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<ApiResponse<String>>() {}
        );

        if (Boolean.FALSE.equals(Objects.requireNonNull(response.getBody()).isSuccess())) {
            throw new RuntimeException("Failed to send welcome email");
        }

        System.out.println("Welcome email sent: " + response.getBody().getData());
    }

}
