package com.train.touchstone.user.service;

import com.train.touchstone.auth.JwtTokenService;
import com.train.touchstone.auth.authentications.PasswordAuthentication;
import com.train.touchstone.auth.authentications.TokenAuthentication;
import com.train.touchstone.user.dto.JwtToken;
import com.train.touchstone.user.dto.LoginDto;
import com.train.touchstone.user.exceptions.UnAuthorizeException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;


    public JwtToken login(LoginDto dto) {
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication(dto.getEmail(), dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(passwordAuthentication);
        if (authentication instanceof TokenAuthentication && authentication.isAuthenticated()) {
            String token = getToken(authentication.getName());
            JwtToken jwtToken = new JwtToken();
            jwtToken.setToken(token);
            return jwtToken;
        }
        throw new UnAuthorizeException("password wrong");
    }


    private String getToken(String email) {
        Map<String, Object> claimsForToken = new HashMap<>();
        claimsForToken.put("EMAIL", email);
        claimsForToken.put("EXPIRE", true);
        return jwtTokenService.generateToken(claimsForToken, email);
    }

}
