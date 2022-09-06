package com.adria.projetbackend.services.Benificiare;

import com.adria.projetbackend.models.Benificiaire;

import java.util.List;

public interface IBenificiareService {

    Benificiaire ajouterBenificiaire(Benificiaire benificiaire);
    boolean existsByRibAndClientId(String rib,Long id);
    Benificiaire consulterBenificiaireByRib(String rib,Long idClient);
    List<Benificiaire> consulterTousLesBenificiaires(Long idClient);
    void majBenificiaire(Benificiaire benificiaire,boolean applyPeriodicity);

}
