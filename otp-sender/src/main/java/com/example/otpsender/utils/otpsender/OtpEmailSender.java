package com.example.otpsender.utils.otpsender;

import com.example.otpsender.model.dto.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OtpEmailSender implements Sender {

    @Value("${spring.mail.username}")
    private String source;

    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(source);
        message.setTo(notification.getTo());
        message.setSubject(notification.getSubject());
        message.setText(notification.getContent());
        mailSender.send(message);
    }
}
