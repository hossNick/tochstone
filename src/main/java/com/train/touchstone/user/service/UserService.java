package com.train.touchstone.user.service;

import com.train.touchstone.user.domain.AuthUser;
import com.train.touchstone.user.dto.UserGetDto;
import com.train.touchstone.user.dto.UserPostDto;
import com.train.touchstone.user.repositories.AuthUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final AuthUserRepository userRepository;

    public UserService(AuthUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserGetDto> getUserList() {
        return userRepository.findAll().stream().map(UserGetDto::new).toList();
    }

    public void createUser(UserPostDto postDto) {
        AuthUser user = new AuthUser();
        userRepository.save(convertFromPostDto(user, postDto));
    }

    public void updateUser(Long id, UserPostDto userPostDto) {
        AuthUser user = findOrThrow(id);
        userRepository.save(convertFromPostDto(user, userPostDto));
    }

    public AuthUser findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("no user for " + email));
    }


    public AuthUser findOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("no user for " + userId));
    }



    private AuthUser convertFromPostDto(AuthUser user, UserPostDto postDto) {
        user.setFirstName(postDto.getFirstName());
        user.setLastName(postDto.getLastName());
        user.setEmail(postDto.getEmail());
        return user;
    }

}
