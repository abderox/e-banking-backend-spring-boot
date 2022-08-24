package com.adria.projetbackend.services;

import com.adria.projetbackend.models.Banque;
import com.adria.projetbackend.repositories.BanqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    @Autowired
    private BanqueRepository bankRepository;


    public Banque addBank(Banque bank) {
        if ( !bankExists(bank.getRaisonSociale( )) ) {
            return bankRepository.save(bank);
        }
        return getBank(bank.getRaisonSociale( ));
    }

    public Banque getBank(String raisonSociale) {
        return bankRepository.findByRaisonSocialeContains(raisonSociale);
    }

    public boolean bankExists(String raisonSociale) {
        return bankRepository.existsByRaisonSocialeContains(raisonSociale);
    }

}
