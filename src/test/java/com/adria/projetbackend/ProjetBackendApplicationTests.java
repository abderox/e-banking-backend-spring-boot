package com.adria.projetbackend;

import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.models.User;
import com.adria.projetbackend.repositories.ClientRepository;
import com.adria.projetbackend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
class ProjetBackendApplicationTests {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {


//        Client client = new Client();
//        client.setUsername("client");
//        client.setPassword("client");
//        client.setEmail("client");
//        client.setDateNaissance(new Date());
//        client.setMetier("client");
//
//        Client cl = clientRepository.findClientByUsername("ade");
//        System.out.println(cl.getUsername());
//        System.out.println(cl.getPassword());
//        System.out.println(cl.getEmail());
//        System.out.println(cl.getCreatedAt());
//
//        User User = userRepository.findUserByUsername("ade");
        String lUUID = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", "").substring(1,15), 16)).replace("0","");
        Long uuid = Long.parseLong(lUUID);
        System.out.println(uuid);
    }

}
