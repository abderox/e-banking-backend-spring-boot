package com.adria.projetbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Compte {
    @Column(name = "id_compte")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 24)
    private String rib;
    private double solde;
    private String intituleCompte;
    private boolean inclusVirement;
    private boolean bloqued;
    private boolean activated;
    private boolean suspended;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Client client;



    @OneToMany(mappedBy = "compte")
    private List<Transaction> listTransactions = new ArrayList<>();




}
