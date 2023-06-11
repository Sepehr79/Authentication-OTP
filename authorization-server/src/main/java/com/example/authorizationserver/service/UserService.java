package com.example.authorizationserver.service;

import com.example.authorizationserver.model.dto.Notification;
import com.example.authorizationserver.model.dto.UserDetailsDto;
import com.example.authorizationserver.model.dto.UserOtpDto;
import com.example.authorizationserver.model.entity.UserDetails;
import com.example.authorizationserver.model.entity.UserOtp;
import com.example.authorizationserver.model.repo.UserDetailsRepository;
import com.example.authorizationserver.model.repo.UserOtpRepository;
import com.example.authorizationserver.utils.OtpGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDetailsRepository userDetailsRepository;

    private final UserOtpRepository userOtpRepository;

    private final OtpGenerator otpGenerator;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public void add(final UserDetailsDto userDetailsDto) {
        if(userDetailsRepository.existsById(userDetailsDto.getUsername()))
            throw new DuplicateKeyException(userDetailsDto.getUsername());
        userDetailsRepository.save(new UserDetails(userDetailsDto.getUsername(), userDetailsDto.getPassword()));
    }

    public void auth(final UserDetailsDto userDetailsDto) {
        Optional<UserDetails> byId = userDetailsRepository.findById(userDetailsDto.getUsername());
        if (byId.isPresent()) {
            if (byId.get().getPassword().equals(userDetailsDto.getPassword())) {
                renewOtp(userDetailsDto);
            } else {
                throw new BadCredentialsException(userDetailsDto.getUsername() + ":" + userDetailsDto.getPassword());
            }
        } else {
            throw new UsernameNotFoundException(userDetailsDto.getUsername());
        }
    }

    public boolean verifyOtp(final UserOtpDto userOtpDto) {
        Optional<UserOtp> byId = userOtpRepository.findById(userOtpDto.getUsername());
        return byId.filter(otp -> otp.getOtp().equals(userOtpDto.getOtp())).isPresent();
    }

    @SneakyThrows
    private void renewOtp(UserDetailsDto userDetailsDto) {
        String otp = String.valueOf(otpGenerator.generateRandomOtp());
        UserOtp userOtp = userOtpRepository.save(new UserOtp(userDetailsDto.getUsername(), otp));

        Notification notification = new Notification();
        notification.setOtp(userOtp.getOtp());
        notification.setTo(userOtp.getUsername());
        notification.setSubject("Authentication");
        notification.setContent("Your authentication code is: " + notification.getOtp());
        kafkaTemplate.send("sendOtp", OBJECT_MAPPER.writeValueAsString(notification));
    }

}
