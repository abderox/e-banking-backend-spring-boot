package com.adria.projetbackend.services.Banquier;

import com.adria.projetbackend.dtos.BanquierDetailsDto;
import com.adria.projetbackend.models.Banquier;

public interface IBanquierService {

    Banquier registerBanquier(Banquier banquier);
    Banquier getBanquier(String identifiant);
    BanquierDetailsDto getBanquierDto(Long id);
    boolean banquierExists(String identifiant);

}
