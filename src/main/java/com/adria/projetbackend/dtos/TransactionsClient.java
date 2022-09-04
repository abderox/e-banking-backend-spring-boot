package com.adria.projetbackend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsClient {

    private String ribCompte;
    private double montant;
    private String typeTransaction;
    private String dateExecution;
    private String referenceTransaction;
    private boolean executed;

}
