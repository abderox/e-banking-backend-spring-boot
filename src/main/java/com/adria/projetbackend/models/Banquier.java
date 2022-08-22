package com.adria.projetbackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@DiscriminatorValue("BANQUIER")
@Data @AllArgsConstructor @NoArgsConstructor
public class Banquier extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banquier")
    private Long id;
    private String identifiantBanquier;
    private String position;
    private String status;
}
