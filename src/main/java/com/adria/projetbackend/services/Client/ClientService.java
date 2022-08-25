package com.adria.projetbackend.services.Client;

import com.adria.projetbackend.repositories.ClientRepository;
import com.adria.projetbackend.utils.enums.TypeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements IClientServices {

    @Autowired
    private ClientRepository clientRepository;



}
