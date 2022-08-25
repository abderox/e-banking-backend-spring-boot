package com.adria.projetbackend.services.BackOffice;

import com.adria.projetbackend.dtos.ClientRegistration;
import com.adria.projetbackend.models.Client;

import java.text.ParseException;
import java.util.List;

public interface IBackOfficeServices {

    boolean emailExists(String email);
    boolean usernameExists(String username);
    boolean idExists(Long id);
    boolean identifiantClientExists(String identifiantClient);
    List<Client> consulterTousClients();
    void supprimerClient(String clientIdentity);
    Client modifierClient(Client client);
    Client consulterClientByEmail(String email);
    Client consulterClientByUsername(String username);
    Client consulterClientById(Long id);
    Client ajouterNouveauClient(ClientRegistration clientRegistration) throws ParseException;
    public Client consulterClientByIdentifiant(String clientIdentity);

}
