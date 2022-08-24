package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Banque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanqueRepository extends JpaRepository<Banque, Long> {

    Banque findByRaisonSocialeContains(String raisonSociale);

    boolean existsByRaisonSocialeContains(String raisonSociale);
}