package com.adria.projetbackend.controllers;


import com.adria.projetbackend.dtos.ClientRegistration;
import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.models.UserE;
import com.adria.projetbackend.security.aspects.RolesAllowed;
import com.adria.projetbackend.services.AddressService;
import com.adria.projetbackend.services.AgenceService;
import com.adria.projetbackend.services.BackOffice.IBackOfficeServices;
import com.adria.projetbackend.services.Client.ClientService;
import com.adria.projetbackend.services.RoleService;
import com.adria.projetbackend.services.User.IUserService;
import com.adria.projetbackend.utils.constants.SecurityAuthConstants;
import com.adria.projetbackend.utils.enums.RolesE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@Api(tags = "Back-office-operations")
@RequestMapping(SecurityAuthConstants.API_URL_V2)
public class BackOfficeController {

    @Autowired
    private IUserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AgenceService agenceService;

    @Autowired
    ClientService clientService;

    @Autowired
    AddressService addressService;

    @Autowired
    IBackOfficeServices backOfficeService;


    @RolesAllowed(roles = {RolesE.ToString.ROLE_ADMIN})
    @PostMapping(SecurityAuthConstants.SIGN_UP_URL_CLIENT)
    @ApiOperation(value = "This method is used to register a new client")
    public ResponseEntity<UserE> registerUser(@Valid ClientRegistration clientRegistration) throws ParseException {

        return new ResponseEntity<>(backOfficeService.ajouterNouveauClient(clientRegistration), HttpStatus.OK);

    }


    //TODO delete these after

    @GetMapping("/getAllClients")
    @ApiOperation(value = "This method is used to get all clients")
    public ResponseEntity<List<Client>> getAllClients() {
        return new ResponseEntity<>(backOfficeService.consulterTousClients( ), HttpStatus.OK);
    }


    @RolesAllowed(roles = {RolesE.ToString.ROLE_ADMIN})
    @GetMapping("/getAll")
    @ApiOperation(value = "This method is used to get all clients")
    public ResponseEntity<?> getAll() {

        if ( userService.isUserFullyAuthorized( ) )
            return new ResponseEntity<>(backOfficeService.consulterTousClients( ), HttpStatus.OK);
        else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Token expired try sign in once again !"), HttpStatus.UNAUTHORIZED);

    }


}
