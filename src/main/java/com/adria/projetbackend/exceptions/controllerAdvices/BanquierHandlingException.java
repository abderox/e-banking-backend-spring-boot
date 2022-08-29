package com.adria.projetbackend.exceptions.controllerAdvices;


import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchBanquierException;
import com.adria.projetbackend.exceptions.runTimeExpClasses.RolesNotAllowedExceptions;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BanquierHandlingException {


    @ExceptionHandler(value
            = NoSuchBanquierException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleException(NoSuchBanquierException ex) {
        return new ApiError(HttpStatus.NOT_FOUND, ex.getMessage( ));
    }



}
