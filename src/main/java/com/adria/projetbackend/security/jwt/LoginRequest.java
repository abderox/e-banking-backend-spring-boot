package com.adria.projetbackend.security.jwt;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @autor abderox
 */




@Data
public class LoginRequest {


    @NotEmpty(message = "password is required")
    private String password;
    @NotBlank
    @NotEmpty(message = "email is required")
    @Email(message = "HINT : Try a valid email")
    private String email;



}
