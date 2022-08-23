package com.adria.projetbackend;

import com.adria.projetbackend.models.Address;
import com.adria.projetbackend.models.Agence;
import com.adria.projetbackend.models.Banque;
import com.adria.projetbackend.repositories.AgenceRepository;
import com.adria.projetbackend.services.AgenceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjetBackendApplication{

    public static void main(String[] args) {
        SpringApplication.run(ProjetBackendApplication.class, args);
    }

}
