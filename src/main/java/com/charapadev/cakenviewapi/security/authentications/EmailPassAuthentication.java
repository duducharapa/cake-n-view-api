package com.charapadev.cakenviewapi.security.authentications;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class EmailPassAuthentication extends UsernamePasswordAuthenticationToken {

    public EmailPassAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public EmailPassAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
