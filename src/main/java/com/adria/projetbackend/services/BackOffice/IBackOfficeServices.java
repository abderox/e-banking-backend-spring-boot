package com.adria.projetbackend.services.BackOffice;

import com.adria.projetbackend.models.Client;

import java.util.List;

public interface IBackOfficeServices {

    boolean emailExists(String email);
    boolean usernameExists(String username);
    boolean idExists(Long id);
    boolean identifiantClientExists(String identifiantClient);
    List<Client> ConsulterTousClients();
    void supprimerClient(String clientIdentity);
    Client modifierClient(Client client);
    Client consulterClientByEmail(String email);
    Client consulterClientByUsername(String username);
    Client consulterClientById(Long id);
    Client ajouterNouveauClient(Client client);
    public Client consulterClientByIdentifiant(String clientIdentity);

}
