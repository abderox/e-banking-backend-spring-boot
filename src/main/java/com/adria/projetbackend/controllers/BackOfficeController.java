package com.adria.projetbackend.controllers;


import com.adria.projetbackend.dtos.ClientRegistration;
import com.adria.projetbackend.models.Address;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.models.Role;
import com.adria.projetbackend.models.UserE;
import com.adria.projetbackend.services.AddressService;
import com.adria.projetbackend.services.AgenceService;
import com.adria.projetbackend.services.BackOffice.IBackOfficeServices;
import com.adria.projetbackend.services.Client.ClientService;
import com.adria.projetbackend.services.RoleService;
import com.adria.projetbackend.services.User.IUserService;
import com.adria.projetbackend.utils.UtilsMethods.UtilsMethods;
import com.adria.projetbackend.utils.constants.SecurityAuthConstants;
import com.adria.projetbackend.utils.enums.RolesE;
import com.adria.projetbackend.utils.enums.TypePieceID;
import com.adria.projetbackend.utils.enums.TypeSituationFam;
import com.adria.projetbackend.utils.enums.TypeStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Api(tags = "Add-new-CLient")
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(SecurityAuthConstants.SIGN_UP_URL_CLIENT)
    @ApiOperation(value = "This method is used to register a new client")
    public ResponseEntity<UserE> registerUser(@Valid ClientRegistration clientRegistration) throws ParseException {

        return new ResponseEntity<>(backOfficeService.ajouterNouveauClient(clientRegistration), HttpStatus.OK);

    }

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @GetMapping("/getAllClients")
    @ApiOperation(value = "This method is used to get all clients")
    public ResponseEntity<List<Client>> getAllClients() {
        return new ResponseEntity<>(backOfficeService.consulterTousClients(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    @ApiOperation(value = "This method is used to get all clients")
    public ResponseEntity<List<Client>> getAll() {
        return new ResponseEntity<>(backOfficeService.consulterTousClients(), HttpStatus.OK);
    }







}
