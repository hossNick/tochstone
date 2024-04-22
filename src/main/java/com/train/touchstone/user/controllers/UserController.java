package com.train.touchstone.user.controllers;

import com.train.touchstone.user.dto.UserGetDto;
import com.train.touchstone.user.dto.UserPostDto;
import com.train.touchstone.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/user/manage")
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<UserGetDto>> getAll() {
        return ResponseEntity.ok(userService.getUserList());
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserPostDto postDto) {
        userService.createUser(postDto);
        return ResponseEntity.ok().build();
    }


}
