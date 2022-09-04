package com.adria.projetbackend.services.Compte;

import com.adria.projetbackend.exceptions.runTimeExpClasses.BalanceMustBePositive;
import com.adria.projetbackend.models.Compte;
import com.adria.projetbackend.repositories.CompteRepository;
import com.adria.projetbackend.services.Transaction.ITransactionService;
import com.adria.projetbackend.utils.enums.TypeTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompteService implements ICompteService {

    @Autowired
    private CompteRepository compteRepository;


    // !  logique d ZFT TODO : to be reviewed
    public Compte ajouterCompte(Compte compte) {
        if ( compteRepository.existsById(compte.getId( )) ) throw new RuntimeException("No such account");
        return compteRepository.save(compte);
    }

    public void majSolde(Compte compte, double montant) {
        compte.setSolde(compte.getSolde( ) + montant);
        compteRepository.save(compte);
    }

    public Compte consulterCompte(Long id) {
        Compte compte = compteRepository.findById(id).get( );
        if ( compte.equals(null) ) throw new RuntimeException("Compte non existe");
        return compte;
    }


    public List<Compte> consultercomptes() {
        return compteRepository.findAll( );
    }

    boolean compteExists(String identifiant) {
        return compteRepository.existsByClient_IdentifiantClient(identifiant);
    }


    // ? from here

    public Long getLatestRow() {
        return compteRepository.findTopByOrderByIdDesc( ) != null ? compteRepository.findTopByOrderByIdDesc( ).getId( ) : 0;
    }

    public Compte consulterCompteByRib(String rib) {
        return compteRepository.findByRib(rib);
    }

    public Compte consulterCompteByRibAndClientId(String rib, Long id) {
        return compteRepository.findByRibAndClient_Id(rib, id);
    }

    public Compte ajouterCompteV2(Compte compte) {
        if ( compte.getSolde( ) < 0 )
            throw new BalanceMustBePositive("Solde must be positive");
        return compteRepository.save(compte);
    }


    public void updateCompte(Compte compte, double amount, TypeTransaction type) {
        if ( amount < 0 )
            throw new BalanceMustBePositive("WARNING : trying to update balance with a non positive value");
        if(compte.getSolde()<amount && type.equals(TypeTransaction.RETRAIT))
            throw new BalanceMustBePositive("WARNING : Withdrawal amount is greater than the balance");

        switch (type) {
            case DEPOT:
                compte.setSolde(compte.getSolde( ) + amount);
                break;
            case RETRAIT:
                compte.setSolde(compte.getSolde( ) - amount);
                break;
        }


        compteRepository.save(compte);
    }


    public boolean existsByRib(String rib) {
        return compteRepository.existsByRib(rib);
    }


}
