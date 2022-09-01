package com.adria.projetbackend.services;

import com.adria.projetbackend.exceptions.runTimeExpClasses.CodeAgencyMustBeUnique;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchAgenceException;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchCustomerException;
import com.adria.projetbackend.models.Agence;
import com.adria.projetbackend.models.Banquier;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.repositories.AddressRepository;
import com.adria.projetbackend.repositories.AgenceRepository;
import com.adria.projetbackend.repositories.BanquierRepository;
import com.adria.projetbackend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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


    public List<Banquier> getBanquiersOfAgence(Long agenceId) {
        return banquierRepository.findBanquiersByAgence_Id(agenceId);
    }

    public List<Client> getClientsOfAgence(Long agenceId) {
        return clientRepository.findClientsByAgence_Id(agenceId);
    }

    public List<Client> getClientsOfAgence(String agenceCode) {
        return clientRepository.findClientsByAgence_Code(agenceCode, Sort.by(Sort.Direction.DESC, "createdAt"));
    }


    public Agence addAgenceIfNotFound(Agence agence) {
        if ( !agenceExists(agence.getCode( )) ) {
            return agenceRepository.save(agence);
        }
        return getAgence(agence.getCode( ));
    }

    public Agence addAgence(Agence agence) {
        if ( !agenceExists(agence.getCode( )) ) {
            return agenceRepository.save(agence);
        }
        throw new CodeAgencyMustBeUnique( "Agency code must be unique " );
    }

    public Agence getAgence(String code) {
        if ( agenceExists(code) ) {
            return agenceRepository.findByCode(code);
        }
        throw new NoSuchAgenceException( "No such agency found" );

    }



    public boolean agenceExists(String code) {
        return agenceRepository.existsByCode(code);
    }


    public void update(Agence agence) {
        if ( agenceExists(agence.getCode( )) ) {
            agenceRepository.save(agence);  // update
        }
    }
}
