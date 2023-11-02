package com.charapadev.cakenviewapi.modules.auth;

/**
 * Record used to organize the data given to user on successfully authentications.
 */

public record SuccessLoginDTO(
    /**
     * The JWT token that will be used on authorization.
     */
    String token
) {}
