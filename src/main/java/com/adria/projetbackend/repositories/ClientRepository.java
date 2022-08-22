package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}