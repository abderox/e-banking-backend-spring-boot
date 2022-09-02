package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findTopByOrderByIdDesc();
    List<Transaction> findByCompte_RibOrderByDateCreationDesc(String rib);
}