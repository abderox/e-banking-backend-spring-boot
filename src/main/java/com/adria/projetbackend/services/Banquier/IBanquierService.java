package com.adria.projetbackend.services.Banquier;

import com.adria.projetbackend.models.Banquier;

public interface IBanquierService {

    Banquier registerBanquier(Banquier banquier);
    Banquier getBanquier(String identifiant);
    boolean banquierExists(String identifiant);

}
