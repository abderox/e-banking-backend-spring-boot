package com.adria.projetbackend.controllers;


import com.adria.projetbackend.dtos.BanquierDetailsDto;
import com.adria.projetbackend.dtos.ClientDetailsDto;
import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.security.jwt.LoginRequest;
import com.adria.projetbackend.services.BackOffice.IBackOfficeServices;
import com.adria.projetbackend.services.Client.IClientServices;
import com.adria.projetbackend.services.RoleService;
import com.adria.projetbackend.services.User.IUserService;
import com.adria.projetbackend.services.User.UserDetailsImpl;
import com.adria.projetbackend.utils.constants.SecurityAuthConstants;
import com.adria.projetbackend.utils.storage.JwtToken;
import com.adria.projetbackend.utils.storage.RedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.Date;

@RestController
@Api(tags = "CLient-auth")
@RequestMapping(SecurityAuthConstants.API_URL_V1)
public class ClientAuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IClientServices clientService;

    @Autowired
    RedisRepository redisRepository;


    @PostMapping(SecurityAuthConstants.SIGN_IN_URL_CLIENT)
    @ApiOperation(value = "This method is used to login by a client")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail( ),
                            loginRequest.getPassword( )
                    )

            );

            UserDetailsImpl myUserDetails = (UserDetailsImpl) authentication.getPrincipal( );
            String accessToken = userService.genAccessToken(myUserDetails.getUser( ));

            ClientDetailsDto clientInfo = clientService.getClientDto(myUserDetails.getUser( ).getId( ));
            clientInfo.setEmailUser(myUserDetails.getUsername( ));
            clientInfo.setAccessToken(accessToken);


            redisRepository.add(new JwtToken(
                    accessToken,
                    myUserDetails.getUsername( ),
                    loginRequest.getAgent( ),
                    new Date(System.currentTimeMillis( ) + SecurityAuthConstants.EXPIRATION_TIME)));

            clientInfo.setAgents(redisRepository.userSessions(myUserDetails.getUsername( )));
            clientInfo.setSessions(redisRepository.getAllActiveSessions(myUserDetails.getUsername( )));
            return new ResponseEntity<>(clientInfo, HttpStatus.OK);

        } catch (BadCredentialsException | JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage( )+
                    " ! Or something went wrong , try to re-insert your credentials or change the agent if correct ."), HttpStatus.UNAUTHORIZED);
        }

    }


//    @ApiOperation(value = "This method is used to logout a client")
//    @GetMapping(SecurityAuthConstants.SIGN_OUT_URL_CLIENT)
//    public ResponseEntity<?> logout() {
//        String  accessToken = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
//        redisRepository.delete(accessToken);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


}
