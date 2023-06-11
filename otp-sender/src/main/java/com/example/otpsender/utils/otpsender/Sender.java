package com.example.otpsender.utils.otpsender;

import com.example.otpsender.model.dto.Notification;

public interface Sender {
    void sendOtp(Notification userOtpDto);
}
