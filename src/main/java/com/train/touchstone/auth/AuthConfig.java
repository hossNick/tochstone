package com.train.touchstone.auth;

import com.train.touchstone.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class AuthConfig {
    private final UserService userService;
    @Bean
    UserDetailsService userDetailsService() {
        return userService::findByEmail;
    }

}
