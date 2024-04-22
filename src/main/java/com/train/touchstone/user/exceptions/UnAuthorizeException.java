package com.train.touchstone.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UnAuthorizeException extends RuntimeException{

    public UnAuthorizeException(String message) {
        super(message);
    }
}
