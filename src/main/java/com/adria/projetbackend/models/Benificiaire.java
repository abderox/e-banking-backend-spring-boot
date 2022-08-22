package com.adria.projetbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Benificiaire {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_benificiare")
    private Long id;
    private String nature;
    private String nom;
    @Column(length = 24 , unique = true, nullable = false)
    private String rib;
    private String intituleVirement;
}
