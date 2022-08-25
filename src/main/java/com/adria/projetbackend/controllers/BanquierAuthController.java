package com.adria.projetbackend.controllers;


import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.security.jwt.LoginRequest;
import com.adria.projetbackend.security.jwt.LoginResponse;
import com.adria.projetbackend.services.BackOffice.IBackOfficeServices;
import com.adria.projetbackend.services.RoleService;
import com.adria.projetbackend.services.User.IUserService;
import com.adria.projetbackend.services.User.UserDetailsImpl;
import com.adria.projetbackend.utils.constants.SecurityAuthConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Add-new-CLient")
@RequestMapping(SecurityAuthConstants.API_URL_V1)
public class BanquierAuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IBackOfficeServices backOfficeService;


    @PostMapping(SecurityAuthConstants.SIGN_IN_URL_ADMIN)
    @ApiOperation(value = "This method is used to register a new client")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        try {
            LoginResponse userJwt = null;
            UserDetailsImpl myUserDetails = null;
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail( ),
                            loginRequest.getPassword( )
                    )

            );

            myUserDetails = (UserDetailsImpl) authentication.getPrincipal( );
            String accessToken = userService.genAccessToken(myUserDetails.getUser( ));
            userJwt = new LoginResponse(myUserDetails.getUsername( ), accessToken);
            return new ResponseEntity<>(userJwt, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage( )), HttpStatus.UNAUTHORIZED);
        }

    }


}
