package com.charapadev.cakenviewapi.modules.auth;

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

    /**
     * Constructor equivalent to Lombok RequiredArgsConstructor annotation.
     *
     * @param userRepository The UserRepository instance.
     */
    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Generates the HMAC algorithm using the JWT secret.
     *
     * @return The Algorithm instance.
     */
    private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(secret.getBytes());
    }

    /**
     * Generate an JWT token for an user using the provided email address.
     *
     * @param email The user's email.
     * @return A valid JWT token.
     */
    public String generate(String email) {
        Algorithm alg = generateAlgorithm();

        return JWT.create()
            .withSubject(email)
            .sign(alg);
    }

    /**
     * Decrypt an JWT token provided by an user and retrieves your data from application itself.
     *
     * @param token The JWT token.
     * @return The user token owner.
     * @throws UserNotExistsException The user not exists on application.
     */
    public User decode(String token) throws UserNotExistsException {
        Algorithm alg = generateAlgorithm();

        String email = JWT.require(alg).build()
            .verify(token).getSubject();

        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotExistsException("Cannot find an user vinculated to the token provided"));
    }

}
