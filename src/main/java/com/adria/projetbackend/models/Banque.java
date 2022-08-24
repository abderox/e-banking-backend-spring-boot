package com.adria.projetbackend.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Objects;


@Table(name = "Banque", uniqueConstraints = {
        @UniqueConstraint(name = "uc_banque_telephone", columnNames = {"telephone", "raisonSociale", "email"})
})
@Entity
@Getter
@Setter
@ToString
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
    @Column( length = 40)
    private String rue;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @JsonBackReference
    @ToString.Exclude
    @OneToMany(mappedBy = "banque",fetch = FetchType.EAGER)
    private List<Agence> agences;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || Hibernate.getClass(this) != Hibernate.getClass(o) ) return false;
        Banque banque = (Banque) o;
        return id != null && Objects.equals(id, banque.id);
    }

    @Override
    public int hashCode() {
        return getClass( ).hashCode( );
    }
}
