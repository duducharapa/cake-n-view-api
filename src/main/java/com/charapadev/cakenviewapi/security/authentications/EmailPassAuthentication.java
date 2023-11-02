package com.charapadev.cakenviewapi.security.authentications;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Represents the Authentication implementation used on login logic.
 *
 * Like the name suggests, this class wraps the email and password given by an user.
 */

public class EmailPassAuthentication extends UsernamePasswordAuthenticationToken {

    public EmailPassAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public EmailPassAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
