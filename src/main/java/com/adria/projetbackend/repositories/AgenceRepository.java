package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Agence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenceRepository extends JpaRepository<Agence, Long> {
}