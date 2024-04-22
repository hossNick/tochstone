package com.train.touchstone.auth.authentications;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PasswordAuthentication extends UsernamePasswordAuthenticationToken {


    public PasswordAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public PasswordAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
