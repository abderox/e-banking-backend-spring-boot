package com.adria.projetbackend.services.Compte;

import com.adria.projetbackend.exceptions.runTimeExpClasses.BalanceMustBePositive;
import com.adria.projetbackend.models.Compte;
import com.adria.projetbackend.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompteService implements ICompteService {
    @Autowired
    private CompteRepository compteRepository;

    // !  logique d ZFT TODO : to be reviewed
    public Compte ajouterCompte(Compte compte){
        if (compteRepository.existsById(compte.getId()) ) throw new RuntimeException("No such account");
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


    // ? from here

    public Long getLatestRow()
    {
        return compteRepository.findTopByOrderByIdDesc( ) != null ? compteRepository.findTopByOrderByIdDesc( ).getId( ) : 0;
    }

    public Compte ajouterCompteV2(Compte compte )
    {
        if(compte.getSolde() < 0)
            throw new BalanceMustBePositive("Solde must be positive");
        return compteRepository.save(compte);
    }

    public boolean existsByRib(String rib)
    {
        return compteRepository.existsByRib(rib);
    }


    boolean compteExists(String identifiant) {
        return compteRepository.existsByClient_IdentifiantClient(identifiant);
    }



}
