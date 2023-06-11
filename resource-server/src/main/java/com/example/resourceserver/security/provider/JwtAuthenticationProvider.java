package com.example.resourceserver.security.provider;

import com.example.resourceserver.security.JwtManager;
import com.example.resourceserver.security.authentication.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtManager jwtManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String jwt = (String) authentication.getCredentials();
        final String username = jwtManager.verifyJwt(jwt);
        if (username != null) {
            return new JwtAuthentication(username, "", List.of(new SimpleGrantedAuthority("USER")));
        }
        return new JwtAuthentication("", "");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }
}
