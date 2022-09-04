package com.adria.projetbackend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VirementClient {

        private String ribEmetteur;
        private String ribBenificiaire;
        private double montant;
        private String typeVirement;
        private String nomBenificiaire;
}
