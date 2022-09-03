package com.adria.projetbackend.services.Benificiare;

import com.adria.projetbackend.models.Benificiaire;

public interface IBenificiareService {

    Benificiaire ajouterBenificiaire(Benificiaire benificiaire);
    boolean existsByRibAndClientId(String rib,Long id);
    Benificiaire consulterBenificiaireByRib(String rib,Long idClient);

}
