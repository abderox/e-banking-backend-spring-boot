package com.adria.projetbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCompteDto {



    private String identifiantClient;

    private double solde;

    private boolean inclusVirement;

    private String intituleCompte;

    private String statusClient;


}
