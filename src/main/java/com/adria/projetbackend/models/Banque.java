package com.adria.projetbackend.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;


@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Banque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banque_id")
    private Long id;
    @Column(length = 40)
    private String raisonSociale;
    @Column(length = 15)
    private String telephone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @JsonBackReference
    @OneToMany(mappedBy = "banque",fetch = FetchType.EAGER)
    private List<Agence> agences;


}
