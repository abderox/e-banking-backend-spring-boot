package com.adria.projetbackend.services.User;

import com.adria.projetbackend.models.UserE;
import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.Set;

public interface IUserService {

    String genAccessToken(UserE user);
    boolean validateToken(String token);
    String getDetailsFromSubject(String token);
    Claims parseClaims(String token);
    Set<String> getAuthorities(String token) throws IOException;
    UserE findUserByEmail(String email);
    boolean isUserFullyAuthorized();

}
