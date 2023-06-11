package com.example.authorizationserver.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Void> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<String> handleMailException() {
        return ResponseEntity.internalServerError().body("There is some problem with your email configuration");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Void> handleDuplicateKeyException() {
        return ResponseEntity.badRequest().build();
    }

}
