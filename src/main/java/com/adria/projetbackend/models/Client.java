package com.adria.projetbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Client extends User{
    private String identiiantClient;
    private String typePieceIdentite;
    private String numPieceIdentite;
    private String situationFamilial;
    private String metier;
    private Date dateNaissance;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Benificiaire> benificiaires = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<Compte> comptes = new ArrayList<>();
}
