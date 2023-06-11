package com.example.otpsender;

import com.example.otpsender.model.dto.Notification;
import com.example.otpsender.utils.otpsender.Sender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class OtpSenderApplication {

    private final Sender sender;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        SpringApplication.run(OtpSenderApplication.class, args);
    }

    @KafkaListener(topics = "sendOtp", groupId = "group1")
    public void receiveMessage(final String message) throws JsonProcessingException {
        log.info(message);
        Notification notification = OBJECT_MAPPER.readValue(message, Notification.class);
        sender.sendOtp(notification);
    }


}
