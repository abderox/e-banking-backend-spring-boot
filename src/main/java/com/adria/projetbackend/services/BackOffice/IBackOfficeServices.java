package com.adria.projetbackend.services.BackOffice;

import com.adria.projetbackend.dtos.ClientRegistration;
import com.adria.projetbackend.dtos.ClientsDto;
import com.adria.projetbackend.dtos.NewCompteDto;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.models.Compte;

import java.text.ParseException;
import java.util.List;

public interface IBackOfficeServices {

    boolean emailExists(String email);
    boolean usernameExists(String username);
    boolean idExists(Long id);
    boolean identifiantClientExists(String identifiantClient);
    List<ClientsDto> consulterTousClients(String agenceCode);
    void supprimerClient(String clientIdentity);
    Client modifierClient(Client client);
    Client consulterClientByEmail(String email);
    Client consulterClientByUsername(String username);
    Client consulterClientById(Long id);
    ClientRegistration ajouterNouveauClient(ClientRegistration clientRegistration) throws ParseException;
    Client consulterClientByIdentifiant(String clientIdentity);
    Compte addFirstAccount(NewCompteDto newCompteDto);



}
