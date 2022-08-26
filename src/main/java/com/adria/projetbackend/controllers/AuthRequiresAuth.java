package com.adria.projetbackend.controllers;


import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.security.jwt.LoginRequest;
import com.adria.projetbackend.security.jwt.LoginResponse;
import com.adria.projetbackend.services.BackOffice.IBackOfficeServices;
import com.adria.projetbackend.services.RoleService;
import com.adria.projetbackend.services.User.IUserService;
import com.adria.projetbackend.services.User.UserDetailsImpl;
import com.adria.projetbackend.utils.constants.SecurityAuthConstants;
import com.adria.projetbackend.utils.storage.JwtToken;
import com.adria.projetbackend.utils.storage.RedisRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "logout-auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(SecurityAuthConstants.API_URL_V2)
public class AuthRequiresAuth {

    @Autowired
    RedisRepository redisRepository;



    @GetMapping(SecurityAuthConstants.SIGN_OUT_URL_ADMIN)
    public ResponseEntity<?> logoutAdmin() {
        String accessToken = (String) SecurityContextHolder.getContext( ).getAuthentication( ).getCredentials( );
        redisRepository.delete(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "This method is used to logout a client")
    @GetMapping(SecurityAuthConstants.SIGN_OUT_URL_CLIENT)
    public ResponseEntity<?> logoutClient() {
        String  accessToken = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        redisRepository.delete(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
