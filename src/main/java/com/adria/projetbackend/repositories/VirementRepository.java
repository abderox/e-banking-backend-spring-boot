package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Virement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirementRepository extends JpaRepository<Virement, Long> {
}