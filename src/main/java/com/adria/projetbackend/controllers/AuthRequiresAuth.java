package com.adria.projetbackend.controllers;


import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.security.jwt.LoginRequest;
import com.adria.projetbackend.security.jwt.LoginResponse;
import com.adria.projetbackend.security.otp.OtpRequest;
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
@Api(tags = "auth-requires-auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(SecurityAuthConstants.API_URL_V2)
public class AuthRequiresAuth {

    @Autowired
    RedisRepository redisRepository;
    @Autowired
    IUserService userService;


    @ApiOperation(value = "This method is used to logout a banker")
    @GetMapping(SecurityAuthConstants.SIGN_OUT_URL_ADMIN)
    public ResponseEntity<?> logoutAdmin() {
        String accessToken = (String) SecurityContextHolder.getContext( ).getAuthentication( ).getCredentials( );
        redisRepository.delete(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "This method is used to logout a client")
    @GetMapping(SecurityAuthConstants.SIGN_OUT_URL_CLIENT)
    public ResponseEntity<?> logoutClient() {
        String accessToken = (String) SecurityContextHolder.getContext( ).getAuthentication( ).getCredentials( );
        redisRepository.delete(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "This to send an OTP to the user")
    @GetMapping(SecurityAuthConstants.SEND_OTP)
    public ResponseEntity<?> sendOtp() {
        if ( userService.isUserFullyAuthorized( ) ) {
            try {
                UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
                String res = userService.sendOTP(userDetails.getUsername( ));
                return new ResponseEntity<>(res, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage( )), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "YOU ARE NOT AUTHORIZED ! TRY TO LOGIN FIRST ."), HttpStatus.UNAUTHORIZED);
        }
    }

    @ApiOperation(value = "This to verify the OTP")
    @PostMapping(SecurityAuthConstants.VERIFY_OTP)
    public ResponseEntity<?> verifyOtp( @RequestBody OtpRequest request) {
        if ( userService.isUserFullyAuthorized( ) ) {
            try {
                UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
                boolean res = userService.verifyOTP(userDetails.getUsername( ), request.getOtp( ));
                if ( res ) {
                    return new ResponseEntity<>(request.getOtp(),HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Otp is no valid"), HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage( )), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "YOU ARE NOT AUTHORIZED ! TRY TO LOGIN FIRST ."), HttpStatus.UNAUTHORIZED);
        }
    }


    @ApiOperation(value = "Update password")
    @PostMapping(SecurityAuthConstants.UPDATE_PASSWORD)
    public ResponseEntity<?> updatePassword( @RequestBody OtpRequest otpRequest) {
        if ( userService.isUserFullyAuthorized( ) ) {
            try {
                UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
                userService.updatePassword(userDetails.getUser().getId(),otpRequest.getPassword(),otpRequest.getOtp());
                return new ResponseEntity<>("pass-changed",HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage( )), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "YOU ARE NOT AUTHORIZED ! TRY TO LOGIN FIRST ."), HttpStatus.UNAUTHORIZED);
        }
    }


}
