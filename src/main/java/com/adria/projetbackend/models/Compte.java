package com.adria.projetbackend.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor @NoArgsConstructor
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

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "compte")
    private List<Transaction> transactions = new ArrayList<>( );



    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || Hibernate.getClass(this) != Hibernate.getClass(o) ) return false;
        Compte compte = (Compte) o;
        return id != null && Objects.equals(id, compte.id);
    }

    @Override
    public int hashCode() {
        return getClass( ).hashCode( );
    }
}
