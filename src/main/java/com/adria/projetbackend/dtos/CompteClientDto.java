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
    private String identifiantClient;
    private double solde;
    private String telephone;
    private boolean  bloqued;
    private double montant;

}
