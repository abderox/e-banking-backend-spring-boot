package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte, Long> {
}