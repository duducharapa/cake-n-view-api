package com.charapadev.cakenviewapi.modules.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.charapadev.cakenviewapi.exceptions.UserNotExistsException;
import com.charapadev.cakenviewapi.modules.users.User;
import com.charapadev.cakenviewapi.modules.users.UserRepository;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    private UserRepository userRepository;

    @Autowired
    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(secret.getBytes());
    }

    public String generate(String email) {
        Algorithm alg = generateAlgorithm();

        return JWT.create()
            .withSubject(email)
            .sign(alg);
    }

    public User decode(String token) throws UserNotExistsException {
        Algorithm alg = generateAlgorithm();

        String email = JWT.require(alg).build()
            .verify(token).getSubject();

        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotExistsException("Cannot find an user vinculated to the token provided"));
    }

}
