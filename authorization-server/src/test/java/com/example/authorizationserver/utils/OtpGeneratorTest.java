package com.example.authorizationserver.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OtpGeneratorTest {

    private static final OtpGenerator OTP_GENERATOR = new OtpGenerator();

    @Test
    void testGeneratingOtp() {
        int randomOtp;
        for (int i = 0; i < 10000 ; i ++) {
            randomOtp = OTP_GENERATOR.generateRandomOtp();
            Assertions.assertTrue(randomOtp <= 10000 && randomOtp >= 1000);
        }
    }

}
