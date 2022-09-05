package com.adria.projetbackend.exceptions.controllerAdvices;


import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.exceptions.runTimeExpClasses.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AccountHandlingExp {


    @ExceptionHandler(value
            = NoSuchAccountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleException(NoSuchAccountException ex) {
        return new ApiError(HttpStatus.NOT_FOUND, ex.getMessage( ));
    }

    @ExceptionHandler(value
            = BalanceMustBePositive.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleException(BalanceMustBePositive ex) {
        return new ApiError(HttpStatus.CONFLICT, ex.getMessage( ));
    }

    @ExceptionHandler(value
            = CustomerHasAccountException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleException(CustomerHasAccountException ex) {
        return new ApiError(HttpStatus.CONFLICT, ex.getMessage( ));
    }

    @ExceptionHandler(value
            = TransactionExp.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleException(TransactionExp ex) {
        return new ApiError(HttpStatus.CONFLICT, ex.getMessage( ));
    }

    @ExceptionHandler(value
            = InsufficientDepositException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleException(InsufficientDepositException ex) {
        return new ApiError(HttpStatus.CONFLICT, ex.getMessage( ));
    }







}
