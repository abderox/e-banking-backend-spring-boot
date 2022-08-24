package com.adria.projetbackend.services.User;


import com.adria.projetbackend.dtos.User;
import com.adria.projetbackend.models.UserE;
import com.adria.projetbackend.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;


    public UserE findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String genAccessToken(UserE user) {
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }

    @Override
    public String getDetailsFromSubject(String token) {
        return null;
    }

    @Override
    public Claims parseClaims(String token) {
        return null;
    }

    @Override
    public Set<String> getAuthorities(String token) throws IOException {
        return null;
    }
}
