package com.adria.projetbackend.controllers;


import com.adria.projetbackend.dtos.BanquierDetailsDto;
import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.security.jwt.LoginRequest;
import com.adria.projetbackend.services.BackOffice.IBackOfficeServices;
import com.adria.projetbackend.services.Banquier.IBanquierService;
import com.adria.projetbackend.services.Jobs.ISchedOperations;
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
import java.text.ParseException;

@RestController
@Api(tags = "Banquier-auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
@RequestMapping(SecurityAuthConstants.API_URL_V1)
public class BanquierAuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisRepository redisRepository;

    @Autowired
    IBanquierService banquierService;

    @Autowired
    IBackOfficeServices backOfficeService;

    @Autowired
    ISchedOperations schedOperations;


    @PostMapping(SecurityAuthConstants.SIGN_IN_URL_ADMIN)
    @ApiOperation(value = "This method is used to register a new client")

    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail( ),
                            loginRequest.getPassword( )
                    )
            );

            UserDetailsImpl myUserDetails = (UserDetailsImpl) authentication.getPrincipal( );
            String accessToken = userService.genAccessToken(myUserDetails.getUser( ));

            BanquierDetailsDto banquierInfo = banquierService.getBanquierDto(myUserDetails.getUser( ).getId( ));
            banquierInfo.setEmailUser(myUserDetails.getUsername( ));
            banquierInfo.setAccessToken(accessToken);

            redisRepository.add(new JwtToken(accessToken, myUserDetails.getUsername( )));
            return new ResponseEntity<>(banquierInfo, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage( )), HttpStatus.UNAUTHORIZED);
        }

    }

//    @GetMapping(SecurityAuthConstants.SIGN_OUT_URL_ADMIN)
//    public ResponseEntity<?> logout() {
//        String accessToken = (String) SecurityContextHolder.getContext( ).getAuthentication( ).getCredentials( );
//        redisRepository.delete(accessToken);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


    @GetMapping(SecurityAuthConstants.GET_BANKER_INFO)
    public ResponseEntity<?> getBanquierInfo() {
        UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
        BanquierDetailsDto banquierInfo = banquierService.getBanquierDto(myUserDetails.getUser( ).getId( ));
        banquierInfo.setRoles(myUserDetails.getRoles( ).toArray(new String[myUserDetails.getRoles( ).size( )]));

        return new ResponseEntity<>(banquierInfo, HttpStatus.OK);
    }


    // ! TODO: DELETE THIS AFTERMATH
//    @GetMapping("/validate-all-transfers")
//    public ResponseEntity validateAll() throws ParseException {
//        schedOperations.applyTxs();
//        return  ResponseEntity.ok( "ok");
//
//    }


}
