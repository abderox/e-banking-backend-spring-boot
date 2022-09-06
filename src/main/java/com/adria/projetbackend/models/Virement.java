package com.adria.projetbackend.models;

import com.adria.projetbackend.utils.enums.TypeVirement;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class Virement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_virement")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeVirement type;

    @ManyToOne
    @JoinColumn(name = "id_transaction")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "id_benificiare")
    private Benificiaire benificiaire;


    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || Hibernate.getClass(this) != Hibernate.getClass(o) ) return false;
        Virement virement = (Virement) o;
        return id != null && Objects.equals(id, virement.id);
    }

    @Override
    public int hashCode() {
        return getClass( ).hashCode( );
    }
}
