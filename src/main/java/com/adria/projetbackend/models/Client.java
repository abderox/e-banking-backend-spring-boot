package com.adria.projetbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Client extends User{
    private String identiiantClient;
    private String typePieceIdentite;
    private String numPieceIdentite;
    private String situationFamilial;
    private String metier;
    private Date dateNaissance;
}
