package com.train.touchstone;

import com.train.touchstone.user.domain.AuthUser;
import com.train.touchstone.user.dto.LoginDto;
import com.train.touchstone.user.dto.UserPostDto;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class TestUtils {

    public static AuthUser userOne;
    public static final String userOnePassword = "123456";
    public static final String userOneEmail = "userOne@email.com";
    public static final String userOneFirstName = randomAlphabetic(5);
    public static final String userOneLastName = randomAlphabetic(10);


    public static AuthUser userTwo;
    public static final String userTwoPassword = "654321";
    public static final String userTwoEmail = "userTow@email.com";
    public static final String userTwoFirstName = randomAlphabetic(5);
    public static final String userTwoLastName = randomAlphabetic(10);

    public static UserPostDto postDtoOne;
    public static UserPostDto postDtoTwo;


    public static void initUserOnePostDto() {
        postDtoOne = new UserPostDto();
        postDtoOne.setEmail(userOneEmail);
        postDtoOne.setPassword(userOnePassword);
        postDtoOne.setFirstName(userOneFirstName);
        postDtoOne.setLastName(userOneLastName);
    }


    public static void initUserTwoPostDto() {
        postDtoTwo = new UserPostDto();
        postDtoTwo.setLastName(userTwoLastName);
        postDtoTwo.setEmail(userTwoEmail);
        postDtoTwo.setPassword(userTwoPassword);
        postDtoTwo.setFirstName(userTwoFirstName);
    }

    public static void initUserOne() {
        userOne = new AuthUser();
        userOne.setPassword(userOnePassword);
        userOne.setEmail(userOneEmail);
    }

    public static void initUserTwo() {
        userTwo = new AuthUser();
        userTwo.setPassword(userTwoPassword);
        userTwo.setEmail(userTwoEmail);
    }


}
