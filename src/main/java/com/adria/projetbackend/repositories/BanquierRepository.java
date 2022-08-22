package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Banquier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanquierRepository extends JpaRepository<Banquier, Long> {
}