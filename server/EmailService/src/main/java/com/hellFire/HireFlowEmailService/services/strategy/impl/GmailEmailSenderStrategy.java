package com.hellFire.HireFlowEmailService.services.strategy.impl;

import com.hellFire.HireFlowEmailService.services.strategy.IEmailSenderStrategy;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component("gmailSender")
@RequiredArgsConstructor
public class GmailEmailSenderStrategy implements IEmailSenderStrategy {

    private final JavaMailSender mailSender;

    @Override
    public void send(String to, String subject, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
            mailSender.send(mimeMessage);
        } catch (RuntimeException | MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
