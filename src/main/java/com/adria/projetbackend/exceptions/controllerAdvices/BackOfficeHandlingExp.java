package com.adria.projetbackend.exceptions.controllerAdvices;

/**
 * @autor abderox
 */

import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.exceptions.runTimeExpClasses.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BackOfficeHandlingExp {

    @ExceptionHandler(value
            = CustmerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleException(CustmerAlreadyExistsException ex)
    {
        return new ApiError(HttpStatus.CONFLICT, ex.getMessage());

    }

    @ExceptionHandler(value
            = AlreadyUsedTeleNumber.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleException(AlreadyUsedTeleNumber ex)
    {
        return new ApiError(HttpStatus.CONFLICT, ex.getMessage());

    }

    @ExceptionHandler(value
            = IdentityMustBeUnique.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleException(IdentityMustBeUnique ex)
    {
        return new ApiError(HttpStatus.CONFLICT, ex.getMessage());

    }

    @ExceptionHandler(value
            = UsernameMustBeUnique.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleException(UsernameMustBeUnique ex)
    {
        return new ApiError(HttpStatus.CONFLICT, ex.getMessage());

    }

    @ExceptionHandler(value
            = IdentityPieceMustBeUnique.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleException(IdentityPieceMustBeUnique ex)
    {
        return new ApiError(HttpStatus.CONFLICT, ex.getMessage());

    }


    @ExceptionHandler(value
            = NoSuchCustomerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleException(NoSuchCustomerException ex)
    {
        return new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

    }



}


