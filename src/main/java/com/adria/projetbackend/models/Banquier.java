package com.adria.projetbackend.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@PrimaryKeyJoinColumn(name = "banquier_id")
@Getter
@Setter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class Banquier extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banquier")
    private Long id;
    private String identifiantBanquier;
    private String position;
    private String status;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateEmbauche;


    @ManyToOne
    @JoinColumn(name = "agence_id")
    private Agence agence;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || Hibernate.getClass(this) != Hibernate.getClass(o) ) return false;
        Banquier banquier = (Banquier) o;
        return id != null && Objects.equals(id, banquier.id);
    }

    @Override
    public int hashCode() {
        return getClass( ).hashCode( );
    }
}
