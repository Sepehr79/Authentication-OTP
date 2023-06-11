package com.example.authorizationserver.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserOtpDto {

    private String username;

    private String otp;

}
