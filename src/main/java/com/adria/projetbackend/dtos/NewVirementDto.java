package com.adria.projetbackend.dtos;


import lombok.*;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewVirementDto {


    private String ribBenificiaire;
    private String ribEmetteur;
    private String dateExecution;
    private double montant;
    private String typeVirement;
    private boolean applyPeriodicity;
    private String referenceTransaction;


}
