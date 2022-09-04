package com.adria.projetbackend.services.Compte;

import com.adria.projetbackend.models.Compte;
import com.adria.projetbackend.utils.enums.TypeTransaction;

import java.util.List;

public interface ICompteService {

    Compte ajouterCompteV2(Compte compte);
    Long getLatestRow();
    boolean existsByRib(String rib);
    Compte consulterCompteByRib(String rib);
    double updateCompte(Compte compte, double amount, TypeTransaction type);
    Compte consulterCompteByRibAndClientId(String rib, Long id);

    void majSolde(Compte compte, double montant);
    Compte consulterCompte(Long id);
    List<Compte> consultercomptes();


}
