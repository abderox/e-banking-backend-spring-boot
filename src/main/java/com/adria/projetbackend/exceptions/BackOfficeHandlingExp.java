package com.adria.projetbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BackOfficeHandlingExp {

    @ExceptionHandler(value
            = AlreadyUsedEmail.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleException(AlreadyUsedEmail ex)
    {
        return new ApiError(HttpStatus.CONFLICT, ex.getMessage(),ex);

    }


}


