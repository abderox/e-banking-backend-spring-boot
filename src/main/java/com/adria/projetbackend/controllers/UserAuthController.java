package com.adria.projetbackend.controllers;


import com.adria.projetbackend.dtos.ClientRegistration;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.models.UserE;
import com.adria.projetbackend.services.Client.ClientService;
import com.adria.projetbackend.services.RoleService;
import com.adria.projetbackend.services.User.IUserService;
import com.adria.projetbackend.utils.constants.SecurityAuthConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@Api( tags = "Add-new-CLient")
@RequestMapping(SecurityAuthConstants.API_URL_V1)
public class UserAuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;


    @PostMapping(SecurityAuthConstants.SIGN_UP_URL_CLIENT)
    @ApiOperation(value = "This method is used to register a new client")
    public ResponseEntity<UserE> registerUser(ClientRegistration clientRegistration) {
        UserE user = convertToModel(clientRegistration);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private Client convertToModel(ClientRegistration clientRegistration) {
        return modelMapper.map(clientRegistration, Client.class);
    }


}
