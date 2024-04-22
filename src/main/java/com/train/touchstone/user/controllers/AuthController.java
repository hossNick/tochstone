package com.train.touchstone.user.controllers;

import com.train.touchstone.user.dto.JwtToken;
import com.train.touchstone.user.dto.LoginDto;
import com.train.touchstone.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtToken> getToken(@RequestBody LoginDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }
}
