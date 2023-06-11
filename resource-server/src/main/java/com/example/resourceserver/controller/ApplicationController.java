package com.example.resourceserver.controller;

import com.example.resourceserver.model.dto.UserDetailsDto;
import com.example.resourceserver.utils.proxy.AuthorizationServerProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final AuthorizationServerProxy authorizationServerProxy;

    @GetMapping("/app/status")
    public String getStatus() {
        return "Up";
    }


    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserDetailsDto userDetailsDto) {
        HttpStatusCode statusCode = authorizationServerProxy.saveUser(userDetailsDto);
        if (statusCode.value() == 400) { // Duplicate username
            return ResponseEntity.status(statusCode).build();
        } else if (statusCode.is2xxSuccessful()) {
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

}
