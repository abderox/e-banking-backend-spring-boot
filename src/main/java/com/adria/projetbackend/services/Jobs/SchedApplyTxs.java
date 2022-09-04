package com.adria.projetbackend.services.Jobs;


import com.adria.projetbackend.dtos.CompteClient;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NotValidDateExp;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.models.Compte;
import com.adria.projetbackend.services.Compte.ICompteService;
import com.adria.projetbackend.services.Email.EmailDetails;
import com.adria.projetbackend.services.Email.EmailService;
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

    @Autowired
    private EmailService emailService;


    @Scheduled(cron = "0 50 6 * * ?", zone = "Africa/Casablanca")
    @Transactional
    public void applyTxs() {
        String today = new SimpleDateFormat("MMM-dd-yyyy ").format(new Date( ));

        transactionService.consulterToutesLesTransactionsNonExecuted( ).forEach(transaction -> {

            if ( today.equals(new SimpleDateFormat("MMM-dd-yyyy ").format(transaction.getDateExecution( ))) ) {
                Compte myCompte = transaction.getCompte( );
                final double soldeInitial = myCompte.getSolde( );


                if ( transaction.getVirements( ).size( ) > 0 ) {
                    transaction.getVirements( ).forEach(virement -> {

                        Compte compte = compteService.consulterCompteByRib(virement.getBenificiaire( ).getRib( ));
                        double mySolde = compteService.updateCompte(myCompte, transaction.getMontant( ), TypeTransaction.RETRAIT);
                        compteService.updateCompte(compte, transaction.getMontant( ), TypeTransaction.DEPOT);

                        Client client = myCompte.getClient( );
                        String bankName = client.getAgence( ).getBanque( ).getRaisonSociale( );
                        String status
                                = emailService.sendSimpleMail(new EmailDetails(client.getEmail( ),
                                "Hello again , we are just letting you know , that the operation of transferring is completed successfully  .\n\nFrom your account labelled with : "
                                        + myCompte.getIntituleCompte( ) + "\nIdentified with : " + myCompte.getRib( ) + "\nTo : " + virement.getBenificiaire( ).getNom( )
                                        + virement.getBenificiaire( ).getRib( ) + "\nAmount : -" + transaction.getMontant( ) + " MAD\n\n " +
                                        "Created on : " + new SimpleDateFormat("MMM-dd-yyyy ").format(transaction.getDateCreation( )) + "\n"
                                        + "Executed on : "
                                        + new SimpleDateFormat("MMM-dd-yyyy ").format(new Date( )) + "\nBalance before the operation :" + soldeInitial +
                                        " MAD\nBalance after update : " + mySolde + " MAD"
                                        + "\n\nBest regards , \nBeta-"
                                        + bankName,
                                "Hello from Beta-" + bankName + " ," +
                                        " " + client.getUsername( ) + "!",
                                ""));

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


