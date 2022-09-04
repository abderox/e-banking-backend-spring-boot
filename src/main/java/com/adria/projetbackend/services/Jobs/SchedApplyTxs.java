package com.adria.projetbackend.services.Jobs;


import com.adria.projetbackend.dtos.CompteClient;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NotValidDateExp;
import com.adria.projetbackend.models.Compte;
import com.adria.projetbackend.services.Compte.ICompteService;
import com.adria.projetbackend.services.Transaction.ITransactionService;
import com.adria.projetbackend.utils.enums.TypeTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class SchedApplyTxs implements ISchedOperations {


    @Autowired
    ITransactionService transactionService;

    @Autowired
    ICompteService compteService;


    @Scheduled(cron = "0 30 2 * * ?", zone = "Africa/Casablanca")
    @Transactional
    public void applyTxs() {
        String today = new SimpleDateFormat("MMM-dd-yyyy ").format(new Date( ));

        transactionService.consulterToutesLesTransactionsNonExecuted( ).forEach(transaction -> {

            if ( today.equals(new SimpleDateFormat("MMM-dd-yyyy ").format(transaction.getDateExecution( ))) ) {
                Compte myCompte = transaction.getCompte( );


                if ( transaction.getVirements( ).size( ) > 0 ) {
                    transaction.getVirements( ).forEach(virement -> {

                        Compte compte = compteService.consulterCompteByRib(virement.getBenificiaire( ).getRib( ));
                        compteService.updateCompte(myCompte, transaction.getMontant( ), TypeTransaction.RETRAIT);
                        compteService.updateCompte(compte, transaction.getMontant( ), TypeTransaction.DEPOT);

                    });
                }

                transactionService.updateTransaction(transaction, true);

            }

        });

    }


    //
//    @Scheduled(initialDelay = 2000, fixedRate = 3000)
    public void executeScheduledOperations() {
        String today = new SimpleDateFormat("MMM-dd-yyyy ").format(new Date( ));
        transactionService.consulterToutesLesTransactionsNonExecuted( ).forEach(transaction -> {
            String date_ = new SimpleDateFormat("MMM-dd-yyyy ").format(transaction.getDateExecution( ));

            if ( today.equals(date_) ) {
                transaction.setExecuted(true);
                System.out.println(date_ + " " + transaction.isExecuted( ));
            }


        });
    }

}


