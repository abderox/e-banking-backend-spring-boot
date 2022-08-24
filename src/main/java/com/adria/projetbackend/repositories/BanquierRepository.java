package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Agence;
import com.adria.projetbackend.models.Banquier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BanquierRepository extends JpaRepository<Banquier, Long> {

    List<Banquier> findBanquiersByAgence(Agence agence);

    Banquier findByIdentifiantBanquier(String identifiantBanquier);

    boolean existsByIdentifiantBanquier(String identifiant);
}