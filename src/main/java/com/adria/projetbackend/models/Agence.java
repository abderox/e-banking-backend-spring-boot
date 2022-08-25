package com.adria.projetbackend.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Table(name = "Agence", uniqueConstraints = {
        @UniqueConstraint(name = "uc_agence_code_email", columnNames = {"code", "email", "telephone"})
})
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Agence {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agence_id")
    private Long id;
    @Column(length = 6 )
    private String code;
    private String email;
    @Column(length = 15 )
    private String telephone;
    @Column( length = 40)
    private String rue;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;


    @ManyToOne
    @JoinColumn(name = "banque_id")
    private Banque banque;

    @ToString.Exclude
    @JsonBackReference
    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Client> clients = new ArrayList<>( );

    @JsonBackReference
    @ToString.Exclude
    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL)
    private List<Banquier> banquiers = new ArrayList<>( );

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || Hibernate.getClass(this) != Hibernate.getClass(o) ) return false;
        Agence agence = (Agence) o;
        return id != null && Objects.equals(id, agence.id);
    }

    @Override
    public int hashCode() {
        return getClass( ).hashCode( );
    }
}
