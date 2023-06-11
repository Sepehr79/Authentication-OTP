package com.example.resourceserver.security.provider;

import com.example.resourceserver.model.dto.UserOtpDto;
import com.example.resourceserver.security.authentication.OtpAuthentication;
import com.example.resourceserver.utils.proxy.AuthorizationServerProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    private final AuthorizationServerProxy authorizationServerProxy;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String code = (String) authentication.getCredentials();

        HttpStatusCode httpStatusCode = authorizationServerProxy.verifyUser(new UserOtpDto(name, code));

        if (httpStatusCode.is2xxSuccessful()) {
            return new OtpAuthentication(name, code, List.of());
        }
        return new OtpAuthentication(name, code);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.isAssignableFrom(authentication);
    }
}
