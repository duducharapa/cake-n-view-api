package com.charapadev.cakenviewapi.modules.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(secret.getBytes());
    }

    public String generate(String email) {
        Algorithm alg = generateAlgorithm();

        return JWT.create()
            .withSubject(email)
            .sign(alg);
    }

}
