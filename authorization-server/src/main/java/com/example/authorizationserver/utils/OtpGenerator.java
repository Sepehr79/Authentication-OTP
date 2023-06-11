package com.example.authorizationserver.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OtpGenerator {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public int generateRandomOtp() {
        return 1000 + SECURE_RANDOM.nextInt(8999);
    }

}
