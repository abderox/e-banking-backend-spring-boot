package com.adria.projetbackend.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor @NoArgsConstructor
public class Benificiaire {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_benificiare")
    private Long id;
    private String nature;
    private String nom;
    @Column(length = 24 , unique = true, nullable = false)
    private String rib;
    private String intituleVirement;

    @OneToMany(mappedBy = "benificiaire")
    @ToString.Exclude
    private List<Virement> listVirements = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || Hibernate.getClass(this) != Hibernate.getClass(o) ) return false;
        Benificiaire that = (Benificiaire) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass( ).hashCode( );
    }
}
