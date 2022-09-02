package com.adria.projetbackend.exceptions.controllerAdvices;
/**
 * @autor abderox
 */
import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.exceptions.runTimeExpClasses.DomesticBenifOnlyExp;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchBanquierException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BenifHandlingExp {


    @ExceptionHandler(value
            = DomesticBenifOnlyExp.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleException(DomesticBenifOnlyExp ex) {
        return new ApiError(HttpStatus.NOT_FOUND, ex.getMessage( ));
    }
}
