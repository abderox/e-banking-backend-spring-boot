package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Benificiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenificiaireRepository extends JpaRepository<Benificiaire, Long> {
    boolean existsByRib(String rib);
}