package com.example.resourceserver.security;

import com.example.resourceserver.security.filter.AuthenticationFilter;
import com.example.resourceserver.security.provider.JwtAuthenticationProvider;
import com.example.resourceserver.security.provider.OtpAuthenticationProvider;
import com.example.resourceserver.security.provider.UsernamePasswordAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OtpAuthenticationProvider otpAuthenticationProvider;

    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final JwtManager jwtManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry.requestMatchers("/signup").permitAll();
            authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
        });
        httpSecurity.addFilterAt(authenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
        httpSecurity.authenticationManager(authenticationManager());
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager (
                usernamePasswordAuthenticationProvider,
                otpAuthenticationProvider,
                jwtAuthenticationProvider
        );
    }

    @Bean
    public AuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager, jwtManager);
    }

}
