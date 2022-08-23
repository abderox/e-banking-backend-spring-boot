package com.adria.projetbackend.services;


import com.adria.projetbackend.exceptions.AlreadyUsedEmail;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.repositories.ClientRepository;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BackOfficeService {

    Logger LOGGER = LoggerFactory.getLogger(BackOfficeService.class);
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    Client ajouterNouveauClient(Client client) {

        if ( emailExists(client.getEmail( )) ) {
            throw new AlreadyUsedEmail("There is an account with that email address: " + client.getEmail( ));
        }
        client.setPassword(passwordEncoder.encode(client.getPassword( )));
        LOGGER.debug(client.toString( ));
        return clientRepository.save(client);
    }


    Client consulterClientById(Long id) {
        if ( idExists(id) ) {
            return clientRepository.findById(id).get( );
        } else throw new RuntimeException("CUSTOMER WITH THAT ID DOES NOT EXIST");

    }

    Client consulterClientByUsername(String username) {
        if ( usernameExists(username) ) {
            return clientRepository.findByUsername(username);
        } else throw new RuntimeException("CUSTOMER WITH THAT USERNAME DOES NOT EXIST");
    }

    Client consulterClientByEmail(String email) {
        if ( emailExists(email) ) {
            return clientRepository.findByEmail(email);
        } else {
            throw new RuntimeException("CUSTOMER WITH THAT EMAIL DOES NOT EXIST");
        }
    }


    public Client modifierClient(Client client) {

        if ( !idExists(client.getId( )) && !usernameExists(client.getUsername( )) ) {
            throw new RuntimeException("CUSTOMER  DOES NOT EXIST");
        }
        return clientRepository.save(client);
    }


    public void supprimerClient(String clientIdentity) {
        if ( identifiantClientExists(clientIdentity) ) {
            clientRepository.deleteByIdentifiantClient(clientIdentity);
        } else {
            throw new RuntimeException("CAN NOT DELETE NOTIONAL CUSTOMER");
        }
    }


    public List<Client> ConsulterTousClients() {
        return clientRepository.findAll( );
    }

    public Client consulterClientByIdentifiant(String clientIdentity) {
        if ( identifiantClientExists(clientIdentity) ) {
            return clientRepository.findByIdentifiantClient(clientIdentity);
        } else throw new RuntimeException("CUSTOMER WITH THAT IDENTIFIER DOES NOT EXIST");
    }

    public boolean identifiantClientExists(String identifiantClient) {
        return clientRepository.existsByIdentifiantClient(identifiantClient);
    }

    public boolean idExists(Long id) {
        return clientRepository.existsById(id);
    }

    public boolean usernameExists(String username) {
        return clientRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return clientRepository.existsByEmail(email);
    }


}
