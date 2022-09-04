package com.adria.projetbackend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenificiareDto {

    private String ribBenificiaire;
    private String nomBenificiaire;
    private String intituleVirementBenificiaire;
    private String nature;

}
