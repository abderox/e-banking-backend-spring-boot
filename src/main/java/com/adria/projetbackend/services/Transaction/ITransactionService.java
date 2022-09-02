package com.adria.projetbackend.services.Transaction;

import com.adria.projetbackend.models.Transaction;

import java.util.List;

public interface ITransactionService {

     void effectuerTransaction(Transaction transaction);
    Transaction consulterTransaction(Long id);
    List<Transaction> consulterTransactions(String rib);
    Long getLatestRow();

}
