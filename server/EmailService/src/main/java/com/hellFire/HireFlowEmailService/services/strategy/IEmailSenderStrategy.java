package com.hellFire.HireFlowEmailService.services.strategy;

public interface IEmailSenderStrategy {
    void send(String to, String subject, String content);
}
