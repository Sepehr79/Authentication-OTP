package com.example.authorizationserver.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Notification implements Serializable {

    private String to;

    private String subject;

    private String content;

    private String otp;

}
