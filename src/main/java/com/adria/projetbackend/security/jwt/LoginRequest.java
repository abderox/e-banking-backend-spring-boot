package com.adria.projetbackend.security.jwt;
import lombok.Data;

/**
 * @autor abderox
 */




@Data
public class LoginRequest {


    private String password;
    private String email;



}
