package com.adria.projetbackend.services.Compte;

import com.adria.projetbackend.models.Compte;

import java.util.List;

public interface ICompteService {

    Compte ajouterCompteV2(Compte compte);
    void majSolde(Compte compte, double montant);
    Compte consulterCompte(Long id);
    List<Compte> consultercomptes();
    Long getLatestRow();
    boolean existsByRib(String rib);

}
