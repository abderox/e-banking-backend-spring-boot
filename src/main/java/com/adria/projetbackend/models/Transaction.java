package com.adria.projetbackend.models;

import com.adria.projetbackend.utils.enums.TypeTransaction;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Long id;

    @CreationTimestamp
    private Date dateCreation;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateExecution;

    @Enumerated(EnumType.STRING)
    private TypeTransaction type;

    private double montant;
    private String referenceTransaction;

    @ToString.Exclude
    @OneToMany(mappedBy = "transaction")
    private Collection<Virement> virements;


    @ManyToOne
    @JoinColumn(name = "id_compte")
    private Compte compte;



    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || Hibernate.getClass(this) != Hibernate.getClass(o) ) return false;
        Transaction that = (Transaction) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass( ).hashCode( );
    }
}
