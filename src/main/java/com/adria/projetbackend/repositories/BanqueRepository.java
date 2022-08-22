package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Banque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanqueRepository extends JpaRepository<Banque, Long> {
}