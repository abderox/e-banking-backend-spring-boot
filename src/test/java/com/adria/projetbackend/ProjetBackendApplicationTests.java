package com.adria.projetbackend;

import com.adria.projetbackend.models.Address;
import com.adria.projetbackend.models.Agence;
import com.adria.projetbackend.models.Banque;
import com.adria.projetbackend.repositories.AddressRepository;
import com.adria.projetbackend.repositories.AgenceRepository;
import com.adria.projetbackend.repositories.BanqueRepository;
import com.adria.projetbackend.repositories.BanquierRepository;
import com.adria.projetbackend.services.AgenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjetBackendApplicationTests {

    @Autowired
    private AgenceRepository agenceRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private BanqueRepository banqueRepository;
    @Autowired
    private AgenceService agenceService;

    @Test
    void contextLoads() {
        Address adress1 = new Address(null,"cs","casa","cs","cs");
        Banque banque1 = new Banque(null,"123","0645545","at@at.com",adress1,null);
        Address adress2 = new Address(null,"fz","fes","fz","fz");
        Banque banque2 = new Banque(null,"123","0645545","at@at.com",adress2,null);

/*
        addressRepository.save(adress1);
        addressRepository.save(adress2);
        banqueRepository.save(banque1);
        banqueRepository.save(banque2);

 */
/*
        Agence agence1 = new Agence(null,123,"at@at.com","06666666",adress1,
                banque1,null,null);

        Agence agence2 = new Agence(null,256,"at@at.com","065454545",adress1,
                banque2,null,null);
        Agence agence3 = new Agence(null,66,"at@at.com","05231546",adress2,
                banque1,null,null);
        Agence agence4 = new Agence(null,47,"at@at.com","023454616",adress2,
                banque2,null,null);


        agenceRepository.save(agence1);agenceRepository.save(agence2);
        agenceRepository.save(agence3);agenceRepository.save(agence4);

 */

        agenceService.getClientsOfAgence(1L).forEach(client -> System.out.println(client.getId()));
        agenceService.getBanquiersOfAgence(1L).forEach(banquier -> System.out.println(banquier.getUsername()));
    }

}
