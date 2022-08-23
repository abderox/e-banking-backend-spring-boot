package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Agence;
import com.adria.projetbackend.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

<<<<<<< HEAD
    List<Client> findClientsByAgence_Id(Long agenceId);
    List<Client> findClientsByAgence_Code(Integer agenceCode);
=======
    List<Client> findClientsByAgence(Agence agence);

    Client findByEmail(String email);

    Client findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByIdentifiantClient(String identifiantClient);

    Client findByIdentifiantClient(String identifiantClient);

    void deleteByIdentifiantClient(String identifiantClient);
>>>>>>> a7855b02191916a0a7f8e6b1588bde4bc046bd8c
}