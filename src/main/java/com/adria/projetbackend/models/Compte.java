package com.adria.projetbackend.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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

    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Transaction> transactions = new LinkedHashSet<>( );

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
