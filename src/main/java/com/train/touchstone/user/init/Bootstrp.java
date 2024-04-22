package com.train.touchstone.user.init;

import com.train.touchstone.user.domain.AuthUser;
import com.train.touchstone.user.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;


@Slf4j
@RequiredArgsConstructor
//@Component
public class Bootstrp implements CommandLineRunner {

    private final AuthUserRepository userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        AuthUser user= new AuthUser();
        user.setEmail("email@email.com");
        user.setPassword(passwordEncoder.encode("123456"));
        userService.save(user);
    }
}
