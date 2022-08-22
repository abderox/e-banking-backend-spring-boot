package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Benificiaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BenificiaireRepository extends JpaRepository<Benificiaire, Long> {
}