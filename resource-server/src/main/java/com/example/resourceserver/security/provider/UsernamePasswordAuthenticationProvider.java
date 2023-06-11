package com.example.resourceserver.security.provider;

import com.example.resourceserver.model.dto.UserDetailsDto;
import com.example.resourceserver.security.authentication.UsernamePasswordAuthentication;
import com.example.resourceserver.utils.proxy.AuthorizationServerProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final AuthorizationServerProxy authorizationServerProxy;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String pass = (String) authentication.getCredentials();

        HttpStatusCode httpStatusCode = authorizationServerProxy.authUser(new UserDetailsDto(name, pass));
        if (httpStatusCode.is2xxSuccessful()) {
            return new UsernamePasswordAuthenticationToken(name, pass, List.of());
        }
        return new UsernamePasswordAuthenticationToken(name, pass);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
    }
}
