package com.train.touchstone.auth;


import com.train.touchstone.auth.filters.JwtTokenFilter;
import com.train.touchstone.auth.providers.LoginProvider;
import com.train.touchstone.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final UserService userService;
    private final JwtTokenFilter jwtTokenFilter;
    private static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/**",
            "/api/v1/user/manage"
    };

    @Bean
    public SecurityFilterChain createFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(AUTH_WHITELIST).permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(c -> {
                    CorsConfigurationSource cs = r -> {
                        CorsConfiguration cc = new CorsConfiguration();
                        cc.setAllowedOrigins(List.of("*"));
                        cc.setAllowedMethods(List.of("*"));
                        cc.setAllowedHeaders(List.of("*"));
                        cc.addExposedHeader("*");
                        cc.addAllowedHeader("*");
                        cc.addAllowedOrigin("*");
                        cc.addAllowedMethod("*");
                        return cc;
                    };
                    c.configurationSource(cs);
                });

        return httpSecurity.build();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManagerBean() {
        return new ProviderManager(List.of(loginProvider()));
    }


    @Bean
    public LoginProvider loginProvider() {
        return new LoginProvider(passwordEncoder(), userService);
    }


}
