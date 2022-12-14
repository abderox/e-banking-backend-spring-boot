package com.adria.projetbackend.controllers;

import com.adria.projetbackend.dtos.NewBenificiare;
import com.adria.projetbackend.dtos.NewCompteDto;
import com.adria.projetbackend.dtos.NewVirementDto;
import com.adria.projetbackend.exceptions.ApiError;
import com.adria.projetbackend.security.aspects.RolesAllowed;
import com.adria.projetbackend.services.AddressService;
import com.adria.projetbackend.services.AgenceService;
import com.adria.projetbackend.services.BackOffice.IBackOfficeServices;
import com.adria.projetbackend.services.Banquier.IBanquierService;
import com.adria.projetbackend.services.Client.ClientService;
import com.adria.projetbackend.services.RoleService;
import com.adria.projetbackend.services.User.IUserService;
import com.adria.projetbackend.services.User.UserDetailsImpl;
import com.adria.projetbackend.services.Virement.IVirementService;
import com.adria.projetbackend.utils.constants.SecurityAuthConstants;
import com.adria.projetbackend.utils.enums.RolesE;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@Api(tags = "Front-office-operations")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
@RequestMapping(SecurityAuthConstants.API_URL_V2)
public class ClientConsoleController {



    @Autowired
    private IUserService userService;

    @Autowired
    ClientService clientService;

    @Autowired
    IVirementService virementService;






    @RolesAllowed(roles = {RolesE.ToString.ROLE_ACTIVE_CLIENT})
    @GetMapping("/get-accounts-client")
    public ResponseEntity<?> getAccounts() {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );

            return new ResponseEntity<>(clientService.consulterToutesLesComptes(myUserDetails.getUser().getId()), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);
    }

    @RolesAllowed(roles = {RolesE.ToString.ROLE_ACTIVE_CLIENT})
    @GetMapping("/get-transactions-client")
    public ResponseEntity<?> getTransactionsClient() {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            return new ResponseEntity<>(clientService.consulterToutesLesTransactions(myUserDetails.getUser().getId()), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);
    }


    @RolesAllowed(roles = {RolesE.ToString.ROLE_ACTIVE_CLIENT})
    @PostMapping("/add-benificiary")
    public ResponseEntity<?> addBenificiary(@RequestBody NewBenificiare newBenificiare) {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            return new ResponseEntity<>(clientService.ajouterBenificiaire(myUserDetails.getUser().getId(), newBenificiare), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);
    }


    @RolesAllowed(roles = {RolesE.ToString.ROLE_ACTIVE_CLIENT})
    @PostMapping("/make-transfer")
    public ResponseEntity<?> makeTransfer(@RequestBody NewVirementDto newVirementDto) throws ParseException {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            return new ResponseEntity<>(virementService.effectuerVirement(newVirementDto,myUserDetails.getUser().getId( ),myUserDetails.getUsername()), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);
    }

    @RolesAllowed(roles = {RolesE.ToString.ROLE_ACTIVE_CLIENT})
    @GetMapping("/get-benificiares-client")
    public ResponseEntity<?> getBenificiaries() {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            return new ResponseEntity<>(clientService.consulterTousLesBenificiaires(myUserDetails.getUser().getId()), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);
    }


    @RolesAllowed(roles = {RolesE.ToString.ROLE_ACTIVE_CLIENT})
    @PostMapping("/edit-benificiary")
    public ResponseEntity<?> editBenificiary(@RequestBody NewBenificiare newBenificiare) {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            clientService.modifierBenificiaire(myUserDetails.getUser().getId(), newBenificiare);
            return new ResponseEntity<>( HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);
    }


    @RolesAllowed(roles = {RolesE.ToString.ROLE_ACTIVE_CLIENT})
    @PutMapping("/edit-tx-transfer")
    public ResponseEntity<?> editTransfer(@RequestBody NewVirementDto newVirementDto) throws ParseException {
        if ( userService.isUserFullyAuthorized( ) ) {
            UserDetailsImpl myUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            virementService.modefierVirement(newVirementDto,myUserDetails.getUser().getId( ));
            return new ResponseEntity<>( HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, "Your token may be expired try sign in once again !"), HttpStatus.UNAUTHORIZED);
    }







}
