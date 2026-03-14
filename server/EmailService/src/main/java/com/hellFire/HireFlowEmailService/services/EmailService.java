package com.hellFire.HireFlowEmailService.services;

import com.hellFire.HireFlowEmailService.dtos.requests.EmailVerificationRequest;
import com.hellFire.HireFlowEmailService.dtos.requests.WelcomeEmailRequest;
import com.hellFire.HireFlowEmailService.services.strategy.IEmailSenderStrategy;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
   private final IEmailSenderStrategy emailSenderStrategy;
   private final TemplateEngine templateEngine;

    public EmailService(IEmailSenderStrategy emailSenderStrategy, TemplateEngine templateEngine) {
        this.emailSenderStrategy = emailSenderStrategy;
        this.templateEngine = templateEngine;
    }

    public void verification(EmailVerificationRequest request) {
        Context ctx = new Context();
        ctx.setVariable("otp", request.getOtp());

        String html = templateEngine.process("verification-email", ctx);

        emailSenderStrategy.send(request.getTo(), "Email Verification", html);
    }

    public void sendWelcomeEmail(WelcomeEmailRequest request) {

        Context ctx = new Context();
        ctx.setVariable("userName", request.getUsername());

        String html = templateEngine.process("welcome-email", ctx);

        emailSenderStrategy.send(
                request.getTo(),
                "Welcome to HireFlow!",
                html
        );
    }



}
