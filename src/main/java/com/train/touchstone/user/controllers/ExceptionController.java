package com.train.touchstone.user.controllers;

import com.train.touchstone.user.dto.ApiError;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
public class ExceptionController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public ExceptionController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
            RequestMethod.PUT, RequestMethod.PATCH})
    ApiError handleError(WebRequest webRequest){
        Map<String,Object> attributes=errorAttributes.getErrorAttributes(webRequest,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.BINDING_ERRORS));

        String message=(String)attributes.get("message");
        String path=(String)attributes.get("path");
        int status=(Integer)attributes.get("status");
        ApiError apiError=new ApiError(status,message,path);
        if(attributes.containsKey("errors")){
            @SuppressWarnings("unchecked")
            List<FieldError> fieldErrors=(List<FieldError>)attributes.get("errors");
            Map<String,String> validationErrors= new HashMap<>();
            for(FieldError f:fieldErrors){
                validationErrors.put(f.getField(),f.getDefaultMessage());
            }
            apiError.setValidationErrors(validationErrors);
        }

        return apiError;
    }
}