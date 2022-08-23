package com.adria.projetbackend.services;

import com.adria.projetbackend.models.Benificiaire;
import com.adria.projetbackend.repositories.BenificiaireRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BenificiaireService {
    @Autowired
    private BenificiaireRepository benificiaireRepository;

    public Benificiaire ajouterBenificiaire(Benificiaire benificiaire){
        return benificiaireRepository.save(benificiaire);
    }

    public Benificiaire modifierBenificiaire(Long idBenificiaireAModifie,Benificiaire benificiaire){
        Benificiaire benificiaire1 = benificiaireRepository.findById(idBenificiaireAModifie).get();
        if (benificiaire1.equals(null)) throw new RuntimeException("benificiaire non existant");
        benificiaire1.setIntituleVirement(benificiaire.getIntituleVirement());
        benificiaire1.setNature(benificiaire.getNature());
        benificiaire1.setNom(benificiaire.getNom());
        benificiaire1.setRib(benificiaire.getRib());
        benificiaire1.setListVirements(benificiaire.getListVirements());

        return benificiaireRepository.save(benificiaire1);
    }

    public void supprimerBenificiaire(Long idBenificiaire){
        benificiaireRepository.deleteById(idBenificiaire);
    }

    public Benificiaire consulterBenificiaire(Long idBenificiaire){
        Benificiaire benificiaire = benificiaireRepository.findById(idBenificiaire).get();
        if (benificiaire.equals(null)) throw new RuntimeException("benificiaire non existant");
        return benificiaire;
    }

    public List<Benificiaire> consulterBenificiaires(){
        return benificiaireRepository.findAll();
    }

}
