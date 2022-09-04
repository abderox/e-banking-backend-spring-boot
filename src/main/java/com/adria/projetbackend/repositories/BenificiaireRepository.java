package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Benificiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenificiaireRepository extends JpaRepository<Benificiaire, Long> {

    boolean existsByRibAndClient_Id(String rib, Long id);
    Benificiaire findByRibAndClient_IdentifiantClient(String rib, String identifiantClient);
    Benificiaire findByRibAndClient_Id(String rib, Long id);
    List<Benificiaire> findAllByClient_Id(Long idClient);

}