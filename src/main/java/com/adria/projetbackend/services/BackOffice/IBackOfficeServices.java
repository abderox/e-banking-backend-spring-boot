package com.adria.projetbackend.services.BackOffice;

import com.adria.projetbackend.dtos.*;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.models.Compte;
import com.adria.projetbackend.models.Transaction;

import java.text.ParseException;
import java.util.List;

public interface IBackOfficeServices {



    boolean emailExists(String email);
    boolean usernameExists(String username);
    boolean idExists(Long id);
    boolean identifiantClientExists(String identifiantClient);
    List<ClientsDto> consulterTousClients(String agenceCode);
    List<ClientsDto> consulterTousNouveauxClients(String agenceCode);
    void  majCompte(CompteClientDto compteClientDto, String agenceCode);
    ClientRegistration ajouterNouveauClient(ClientRegistration clientRegistration,String codeAgence) throws ParseException;
    Client consulterClientByIdentifiant(String clientIdentity);
    Compte addFirstAccount(NewCompteDto newCompteDto,String agenceCode);
    Compte addAccount(NewCompteDto newCompteDto,String agenceCode);
    List<Transaction> consulterToutesLesTransactions(String identity , String codeAgence);
    List<CompteClientDto> consulterToutesLesComptes(String telephone,String identity, String  agenceCode);
    void editClient(EditClient editClient,String agenceCode);


}
