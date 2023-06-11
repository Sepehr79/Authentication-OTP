package com.example.resourceserver.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class JwtAuthentication extends UsernamePasswordAuthenticationToken {
    public JwtAuthentication(String principal, String jwt) {
        super(principal, jwt);
    }

    public JwtAuthentication(Object principal, String jwt, Collection<? extends GrantedAuthority> authorities) {
        super(principal, jwt, authorities);
    }
}
