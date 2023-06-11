package com.example.resourceserver.utils.proxy;

import com.example.resourceserver.model.dto.UserDetailsDto;
import com.example.resourceserver.model.dto.UserOtpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthorizationServerProxy {

    @Value("${authorization.server.host}")
    private String authServerHost;

    @Value("${authorization.server.port}")
    private String authSeverPort;

    private final RestTemplate restTemplate;


    public HttpStatusCode saveUser(final UserDetailsDto userDetailsDto) {
        try {
            return restTemplate.postForEntity(getUri() + "/add", userDetailsDto, Void.class)
                    .getStatusCode();
        } catch (HttpStatusCodeException httpStatusCodeException) {
            return HttpStatusCode.valueOf(httpStatusCodeException.getStatusCode().value());
        }
    }

    public HttpStatusCode authUser(final UserDetailsDto userDetailsDto) {
        try {
            return restTemplate.postForEntity(getUri() + "/auth", userDetailsDto, Void.class)
                    .getStatusCode();
        } catch (HttpStatusCodeException httpStatusCodeException) {
            return HttpStatusCode.valueOf(httpStatusCodeException.getStatusCode().value());
        }
    }

    public HttpStatusCode verifyUser(final UserOtpDto userOtpDto) {
        try {
            return restTemplate.postForEntity(getUri() + "/verify", userOtpDto, Void.class)
                    .getStatusCode();
        }catch (HttpStatusCodeException httpStatusCodeException) {
            return HttpStatusCode.valueOf(httpStatusCodeException.getStatusCode().value());
        }
    }

    private String getUri() {
        return "http://" + authServerHost + ":" + authSeverPort + "/app";
    }

}
