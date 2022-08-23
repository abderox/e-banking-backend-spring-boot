package com.adria.projetbackend.services;

import com.adria.projetbackend.models.Compte;
import com.adria.projetbackend.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompteService {
    @Autowired
    private CompteRepository compteRepository;

    public Compte ajouterCompte(Compte compte){
        if (compteRepository.existsById(compte.getId())) throw new RuntimeException("Compte exist déja");
        return compteRepository.save(compte);
    }

    public void majSolde(Compte compte, double montant){
        compte.setSolde(compte.getSolde()+montant);
        compteRepository.save(compte);
    }

    public Compte consulterCompte(Long id){
        Compte compte = compteRepository.findById(id).get();
        if (compte.equals(null)) throw new RuntimeException("Compte non existe");
        return compte;
    }

    public List<Compte> consultercomptes(){
        return compteRepository.findAll();
    }
}
