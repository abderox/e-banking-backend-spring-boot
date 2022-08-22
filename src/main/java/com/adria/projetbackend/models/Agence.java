package com.adria.projetbackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data @AllArgsConstructor
@NoArgsConstructor
public class Agence {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agence_id")
    private Long id;
    @Column(length = 10)
    private Integer code;
    private String email;
    @Column(length = 15)
    private String telephone;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "banque_id")
    private Banque banque;

    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Client> clients = new ArrayList<>( );

}
