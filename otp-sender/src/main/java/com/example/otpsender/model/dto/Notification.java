package com.example.otpsender.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Notification {

    private String to;

    private String subject;

    private String content;

    private int otp;

}
