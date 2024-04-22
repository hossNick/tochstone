package com.train.touchstone.auth.providers;

import com.train.touchstone.auth.authentications.PasswordAuthentication;
import com.train.touchstone.auth.authentications.TokenAuthentication;
import com.train.touchstone.user.domain.AuthUser;
import com.train.touchstone.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Slf4j
public class LoginProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public LoginProvider(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        AuthUser user = userService.findByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.error("Wrong password.");
            return unAuthenticatedUser();
        }

        return new TokenAuthentication(user.getEmail(), user.getUsername()
                , Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }

    private PasswordAuthentication unAuthenticatedUser() {
        PasswordAuthentication authentication = new PasswordAuthentication(null, "");
        authentication.setAuthenticated(false);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordAuthentication.class.equals(authentication);
    }


}
