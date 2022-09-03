package com.adria.projetbackend.services.Transaction;
/**
 * @autor abderox
 */

import com.adria.projetbackend.exceptions.runTimeExpClasses.BalanceMustBePositive;
import com.adria.projetbackend.exceptions.runTimeExpClasses.TransactionExp;
import com.adria.projetbackend.models.Transaction;
import com.adria.projetbackend.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    TransactionRepository transactionRepository;



    @Transactional
    public void effectuerTransaction(Transaction transaction) {
        if ( transaction.getMontant( ) < 0 ) {
            throw new BalanceMustBePositive("THE AMOUNT MUST BE POSITIVE");
        }
        try {
            transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new TransactionExp("ERROR WHILE SAVING TRANSACTION");
        }

    }


    @Override
    public Transaction consulterTransaction(Long id) {
        return null;
    }

    @Override
    public List<Transaction> consulterTransactions(String rib) {
        return transactionRepository.findByCompte_RibOrderByDateCreationDesc(rib);
    }


    public List<Transaction> consulterToutesLesTransactions(){
        return transactionRepository.findAll();
    }

    public List<Transaction> consulterToutesLesTransactionsNonExecuted(){
        return transactionRepository.findAllByExecutedIsFalse();
    }

    @Override
    public Long getLatestRow() {
        return transactionRepository.findTopByOrderByIdDesc( ) != null ? transactionRepository.findTopByOrderByIdDesc( ).getId( ) : 0;
    }
}

