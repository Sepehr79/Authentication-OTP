package com.example.resourceserver.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtManager {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expireAfter}")
    private Duration expireAfter;

    private Algorithm algorithm;

    private static final String ISSUER = "JwtManager";

    @PostConstruct
    void initialAlgorithm() {
        algorithm = Algorithm.HMAC256(secretKey);
    }

    public String createJwt(final String user) {
        return JWT.create()
                .withClaim("username", user)
                .withIssuer(ISSUER)
                .withExpiresAt(new Date(System.currentTimeMillis() + expireAfter.toMillis()))
                .sign(algorithm);
    }

    public String verifyJwt(final String jwt) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT verify = verifier.verify(jwt);
            return verify.getClaim("username").asString();
        } catch (JWTVerificationException jwtVerificationException) {
            return null;
        }
    }


}
