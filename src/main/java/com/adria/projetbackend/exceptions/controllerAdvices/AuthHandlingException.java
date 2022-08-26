package com.adria.projetbackend.exceptions.controllerAdvices;


import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchCustomerException;
import com.adria.projetbackend.exceptions.runTimeExpClasses.RolesNotAllowedExceptions;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuthHandlingException {


    @ExceptionHandler(value
            = RolesNotAllowedExceptions.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiError handleException(RolesNotAllowedExceptions ex) {
        return new ApiError(HttpStatus.FORBIDDEN, ex.getMessage( ));

    }

    @ExceptionHandler(value
            = ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiError handleException(ExpiredJwtException ex) {
        return new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage( ));

    }

}
