package com.train.touchstone.user.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.train.touchstone.TouchstoneApplication;
import com.train.touchstone.auth.SecurityConfig;
import com.train.touchstone.user.dto.LoginDto;
import com.train.touchstone.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.train.touchstone.TestUtils.userOneEmail;
import static com.train.touchstone.TestUtils.userOnePassword;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(classes = {TouchstoneApplication.class, SecurityConfig.class})
@AutoConfigureMockMvc
@DisplayName("test for get jwt token")
class AuthControllerTestIntegration {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getTokenSuccessFullyByEmailAndPassword() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword(userOnePassword);
        loginDto.setEmail(userOneEmail);

        String userOneJsonDto = objectMapper.writeValueAsString(loginDto);
        mockMvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON).content(userOneJsonDto)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());

//        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON).content(userOneJsonDto)).andReturn();
//        String response = mvcResult.getResponse().getContentAsString();
//        JSONObject jsonObject= new JSONObject(response);
//        String userOneJwt= jsonObject.getString("token");
//
//        assertNotNull(userOneJwt);

    }


}