package com.adria.projetbackend.exceptions.controllerAdvices;


import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.exceptions.runTimeExpClasses.CodeAgencyMustBeUnique;
import com.adria.projetbackend.exceptions.runTimeExpClasses.CustmerAlreadyExistsException;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchCustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AgencyHandlingExp {

    @ExceptionHandler(value
            = NoSuchCustomerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleException(NoSuchCustomerException ex)
    {
        return new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

    }


    @ExceptionHandler(value
            = CodeAgencyMustBeUnique.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleException(CodeAgencyMustBeUnique ex)
    {
        return new ApiError(HttpStatus.CONFLICT, ex.getMessage());

    }

}
