package com.adria.projetbackend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteClient {

    private String ribCompte;
    private String intituleCompte;
    private double solde;

}
