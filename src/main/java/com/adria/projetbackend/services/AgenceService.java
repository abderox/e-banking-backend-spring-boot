package com.adria.projetbackend.services;

import com.adria.projetbackend.models.Agence;
import com.adria.projetbackend.models.Banquier;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.repositories.AgenceRepository;
import com.adria.projetbackend.repositories.BanquierRepository;
import com.adria.projetbackend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class AgenceService {
    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private BanquierRepository banquierRepository;

    @Autowired
    private ClientRepository clientRepository;


    public List<Banquier> getBanquiersOfAgence(Agence agence){
        return banquierRepository.findBanquiersByAgence(agence);
    }

    public List<Client> getClientsOfAgence(Agence agence){
        return clientRepository.findClientsByAgence(agence);
    }


}
