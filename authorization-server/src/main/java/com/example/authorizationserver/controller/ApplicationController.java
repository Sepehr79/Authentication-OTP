package com.example.authorizationserver.controller;

import com.example.authorizationserver.model.dto.UserDetailsDto;
import com.example.authorizationserver.model.dto.UserOtpDto;
import com.example.authorizationserver.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class ApplicationController {

    private final UserService userService;

    @PostMapping("/add")
    public void addUser(@RequestBody UserDetailsDto userDetailsDto) {
        userService.add(userDetailsDto);
    }

    @PostMapping("/auth")
    public void authUser(@RequestBody UserDetailsDto userDetailsDto) {
        userService.auth(userDetailsDto);
    }

    @PostMapping("/verify")
    public void verifyUser(@RequestBody UserOtpDto userOtpDto, HttpServletResponse response) {
        if(userService.verifyOtp(userOtpDto)) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
}
