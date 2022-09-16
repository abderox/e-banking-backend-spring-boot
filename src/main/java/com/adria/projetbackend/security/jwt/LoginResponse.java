package com.adria.projetbackend.security.jwt;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @autor abderox
 */




@Data
@AllArgsConstructor
public class LoginResponse {


    private String emailUser;
    private String accessToken;
    private List<String> agents;


}
