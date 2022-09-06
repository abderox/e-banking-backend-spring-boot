package com.adria.projetbackend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteClientDto {

    private String ribCompte;
    private String intituleCompte;
    private double solde;
    private String lastName;
    private String firstName;
    private boolean  bloqued;
    private double montant;

}
