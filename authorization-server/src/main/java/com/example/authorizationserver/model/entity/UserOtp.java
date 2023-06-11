package com.example.authorizationserver.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "UserOtp", timeToLive = 120)
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class UserOtp {

    @Id
    private String username;

    private String otp;

}
