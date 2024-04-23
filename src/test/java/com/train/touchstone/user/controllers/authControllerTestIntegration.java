package com.train.touchstone.user.controllers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.train.touchstone.TouchstoneApplication;
import com.train.touchstone.auth.SecurityConfig;
import com.train.touchstone.user.dto.LoginDto;
import com.train.touchstone.user.service.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static com.train.touchstone.TestUtils.*;
import static com.train.touchstone.auth.JwtTokenService.JWT_TOKEN_VALIDITY;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {TouchstoneApplication.class, SecurityConfig.class})
@AutoConfigureMockMvc
@DisplayName("test for get jwt token")
class AuthControllerTestIntegration {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    AuthController authController;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getTokenSuccessFullyByEmailAndPassword() throws Exception {
        initUser();
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword(userOnePassword);
        loginDto.setEmail(userOneEmail);

        String userOneJsonDto = objectMapper.writeValueAsString(loginDto);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/login").servletPath("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON).content(userOneJsonDto))
                .andExpect(status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(response);
        String userOneJwt = jsonObject.getString("token");
        DecodedJWT decodedJWT = JWT.decode(userOneJwt);
        Date expiresAt = decodedJWT.getExpiresAt();
        long expireTimeEpoc = expiresAt.getTime();
        long now=  new Date(System.currentTimeMillis()).getTime();

        assertNotNull(userOneJwt);
        assertEquals(JWT_TOKEN_VALIDITY , expireTimeEpoc - now,700 );
    }

    @Test
    void getTokenFailByWrongEmail() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword(randomAlphabetic(5));
        loginDto.setEmail(userOneEmail);

        String userOneJsonDto = objectMapper.writeValueAsString(loginDto);
        mockMvc.perform(post("/api/v1/auth/login").servletPath("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON).content(userOneJsonDto))
                .andExpect(status().is(403));
    }


    private void initUser() {
        try {
            userService.findByEmail(userOneEmail);
        } catch (RuntimeException e) {
            initUserOnePostDto();
            userService.createUser(postDtoOne);
        }

        try {
            userService.findByEmail(userTwoEmail);
        } catch (RuntimeException e) {
            initUserTwoPostDto();
            userService.createUser(postDtoTwo);
        }

    }


}