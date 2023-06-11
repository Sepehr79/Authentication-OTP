package com.example.resourceserver.security.filter;

import com.example.resourceserver.security.JwtManager;
import com.example.resourceserver.security.authentication.JwtAuthentication;
import com.example.resourceserver.security.authentication.OtpAuthentication;
import com.example.resourceserver.security.authentication.UsernamePasswordAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtManager jwtManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String username = request.getHeader("Username");
        final String password = request.getHeader("Password");
        final String code = request.getHeader("Code");
        final String authorization = request.getHeader("Authorization");

        if (password != null && username != null) {
            final UsernamePasswordAuthentication usernamePasswordAuthentication =
                    new UsernamePasswordAuthentication(username, password);
            if(authenticationManager.authenticate(usernamePasswordAuthentication).isAuthenticated()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                return;
            }
        } else if (code != null && username != null) {
            final OtpAuthentication otpAuthentication = new OtpAuthentication(username, code);

            if(authenticationManager.authenticate(otpAuthentication).isAuthenticated()) {
                String jwt = jwtManager.createJwt(otpAuthentication.getName());
                response.setHeader("Authorization", jwt);
                response.setStatus(HttpStatus.NO_CONTENT.value());
                return;
            }


        } else if (authorization != null) {
            JwtAuthentication jwtAuthentication = new JwtAuthentication("", authorization);
            jwtAuthentication = (JwtAuthentication) authenticationManager.authenticate(jwtAuthentication);
            if (jwtAuthentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
                filterChain.doFilter(request, response);
                response.setStatus(HttpStatus.ACCEPTED.value());
                return;
            }
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/signup");
    }


}
