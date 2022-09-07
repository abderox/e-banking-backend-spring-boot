package com.adria.projetbackend.controllers;


import com.adria.projetbackend.dtos.ClientRegistration;
import com.adria.projetbackend.dtos.CompteClientDto;
import com.adria.projetbackend.dtos.NewCompteDto;
import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.models.UserE;
import com.adria.projetbackend.security.aspects.RolesAllowed;
import com.adria.projetbackend.services.AddressService;
import com.adria.projetbackend.services.AgenceService;
import com.adria.projetbackend.services.BackOffice.IBackOfficeServices;
import com.adria.projetbackend.services.Banquier.IBanquierService;
import com.adria.projetbackend.services.Client.ClientService;
import com.adria.projetbackend.services.RoleService;
import com.adria.projetbackend.services.User.IUserService;
import com.adria.projetbackend.services.User.UserDetailsImpl;
import com.adria.projetbackend.utils.constants.SecurityAuthConstants;
import com.adria.projetbackend.utils.enums.RolesE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@Api(tags = "Back-office-operations")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
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

    @Autowired
    IBanquierService banquierService;


    @RolesAllowed(roles = {RolesE.ToString.ROLE_ADMIN})
    @PostMapping(SecurityAuthConstants.SIGN_UP_URL_CLIENT)
    @ApiOperation(value = "This method is used to register a new client")
    public ResponseEntity<?> registerUser(@RequestBody @Valid ClientRegistration clientRegistration) throws ParseException {

        try {
            if ( userService.isUserFullyAuthorized( ) ) {
                UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
                String agenceCode = banquierService.getBanquier(myUserDetails.getUser( ).getId( )).getAgence( ).getCode( );
                return new ResponseEntity<>(backOfficeService.ajouterNouveauClient(clientRegistration, agenceCode), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage( )), HttpStatus.BAD_REQUEST);
        }

    }

    @RolesAllowed(roles = {RolesE.ToString.ROLE_ADMIN})
    @GetMapping("/get-clients-agence")
    public ResponseEntity<?> getClientsAgence() {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            String agenceCode = banquierService.getBanquier(myUserDetails.getUser( ).getId( )).getAgence( ).getCode( );
            return new ResponseEntity<>(backOfficeService.consulterTousClients(agenceCode), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);


    }

    @RolesAllowed(roles = {RolesE.ToString.ROLE_ADMIN})
    @GetMapping("/get-recent-clients")
    public ResponseEntity<?> showRecentClients() {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            String agenceCode = banquierService.getBanquier(myUserDetails.getUser( ).getId( )).getAgence( ).getCode( );
            return new ResponseEntity<>(backOfficeService.consulterTousNouveauxClients(agenceCode), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);
    }


    @RolesAllowed(roles = {RolesE.ToString.ROLE_ADMIN})
    @GetMapping("/get-transactions-client/{identity}")
    public ResponseEntity<?> getTransactionsClient(@PathVariable String identity) {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            String agenceCode = banquierService.getBanquier(myUserDetails.getUser( ).getId( )).getAgence( ).getCode( );
            return new ResponseEntity<>(backOfficeService.consulterToutesLesTransactions(identity, agenceCode), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);
    }


    @GetMapping("/get-accounts-per-client/")
    public ResponseEntity<?> getAccountsClient( @RequestParam(required = false)  String identity, @RequestParam(required = false) String telephone) {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            String agenceCode = banquierService.getBanquier(myUserDetails.getUser( ).getId( )).getAgence( ).getCode( );
            return new ResponseEntity<>(backOfficeService.consulterToutesLesComptes(telephone,identity, agenceCode), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);
    }

    @RolesAllowed(roles = {RolesE.ToString.ROLE_ADMIN})
    @PostMapping("/add-client-first-account")
    public ResponseEntity<?> addClientFirstAccount(@RequestBody NewCompteDto newCompteDto) {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            String agenceCode = banquierService.getBanquier(myUserDetails.getUser( ).getId( )).getAgence( ).getCode( );
            return new ResponseEntity<>(backOfficeService.addFirstAccount(newCompteDto, agenceCode), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);

    }

    @RolesAllowed(roles = {RolesE.ToString.ROLE_ADMIN})
    @PostMapping("/add-client-account")
    public ResponseEntity<?> addClientAccount(@RequestBody NewCompteDto newCompteDto) {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            String agenceCode = banquierService.getBanquier(myUserDetails.getUser( ).getId( )).getAgence( ).getCode( );
            return new ResponseEntity<>(backOfficeService.addAccount(newCompteDto, agenceCode), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);

    }

    @RolesAllowed(roles = {RolesE.ToString.ROLE_ADMIN})
    @PostMapping("/update-account")
    public ResponseEntity<?> updateAccount(@RequestBody CompteClientDto compteClientDto) {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            String agenceCode = banquierService.getBanquier(myUserDetails.getUser( ).getId( )).getAgence( ).getCode( );
            backOfficeService.majCompte(compteClientDto, agenceCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);

    }


}
