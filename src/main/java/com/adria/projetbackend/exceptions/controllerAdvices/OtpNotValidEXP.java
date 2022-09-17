package com.adria.projetbackend.exceptions.controllerAdvices;
/**
 * @autor abderox
 */
import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.exceptions.runTimeExpClasses.IllegitimateToMakeTransfers;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NotValidDateExp;
import com.adria.projetbackend.exceptions.runTimeExpClasses.OtpNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OtpNotValidEXP {




    @ExceptionHandler(value
            = OtpNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleException(OtpNotValidException ex) {
        return new ApiError(HttpStatus.NOT_FOUND, ex.getMessage( ));
    }



}
