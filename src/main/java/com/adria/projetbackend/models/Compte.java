package com.adria.projetbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Compte {
    @Column(name = "id_compte")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 24)
    private String rib;
    private String intituleCompte;
    private boolean inclusVirement;
}
