package com.adria.projetbackend.services;


import com.adria.projetbackend.exceptions.runTimeExpClasses.NotValidDateExp;
import com.adria.projetbackend.services.Transaction.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class SchedulingOperations {


    @Autowired
    ITransactionService transactionService;

//
//    @Scheduled(initialDelay = 2000, fixedRate = 3000)
    public void executeScheduledOperations() {
        String today = new SimpleDateFormat("MMM-dd-yyyy ").format(new Date( ));
        transactionService.consulterToutesLesTransactionsNonExecuted( ).forEach(transaction -> {
            String date_ = new SimpleDateFormat("MMM-dd-yyyy ").format(transaction.getDateExecution( ));

           if(today.equals(date_)){
            transaction.setExecuted(true);
            System.out.println(date_ + " " + transaction.isExecuted());
            }


        });
    }

}


