package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    Compte findTopByOrderByIdDesc();
}