package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Banquier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanquierRepository extends JpaRepository<Banquier, Long> {
}