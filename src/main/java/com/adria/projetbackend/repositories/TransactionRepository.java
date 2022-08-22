package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}