package com.adria.projetbackend.services;

import com.adria.projetbackend.models.Agence;
import com.adria.projetbackend.models.Banquier;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.repositories.AgenceRepository;
import com.adria.projetbackend.repositories.BanquierRepository;
import com.adria.projetbackend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AgenceService {
    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private BanquierRepository banquierRepository;

    @Autowired
    private ClientRepository clientRepository;


    public List<Banquier> getBanquiersOfAgence(Long agenceId){
        return banquierRepository.findBanquiersByAgence_Id(agenceId);
    }

    public List<Client> getClientsOfAgence(Long agenceId){
        return clientRepository.findClientsByAgence_Id(agenceId);
    }
    public List<Client> getClientsOfAgence(Integer agenceCode){
        return clientRepository.findClientsByAgence_Code(agenceCode);
    }


}
