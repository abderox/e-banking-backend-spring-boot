package com.adria.projetbackend.services.Banquier;

import com.adria.projetbackend.models.Banquier;
import com.adria.projetbackend.repositories.BanquierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BanqierService implements IBanquierService {

    @Autowired
    private BanquierRepository banquierRepository;


    public Banquier registerBanquier(Banquier banquier){
        if ( !banquierExists(banquier.getIdentifiantBanquier()) )
            return banquierRepository.save(banquier);
        else
        return getBanquier(banquier.getIdentifiantBanquier());

    }
    public Banquier getBanquier(String identifiant){
        return banquierRepository.findByIdentifiantBanquier(identifiant);
    }

    public boolean banquierExists(String identifiant){
        return banquierRepository.existsByIdentifiantBanquier(identifiant);
    }

}
