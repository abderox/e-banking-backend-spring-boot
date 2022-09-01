package com.adria.projetbackend.models;

import com.adria.projetbackend.utils.enums.TypePieceID;
import com.adria.projetbackend.utils.enums.TypeSituationFam;
import com.adria.projetbackend.utils.enums.TypeStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor @NoArgsConstructor
@Table(name = "Client", uniqueConstraints = {
        @UniqueConstraint(name = "uc_client_numpieceidentite", columnNames = {"numPieceIdentite","identifiantClient"})
})
@PrimaryKeyJoinColumn(name = "client_id")
public class Client extends UserE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long id;

    private String identifiantClient;
    private String numPieceIdentite;

    @Enumerated(EnumType.STRING)
    private TypePieceID typePieceID;

    @Enumerated(EnumType.STRING)
    private TypeSituationFam situationFamilial;

    private String metier;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;

    @Enumerated(EnumType.STRING)
    private TypeStatus status;

    @OneToMany(fetch = FetchType.EAGER , mappedBy = "client")
    private List<Benificiaire> benificiaires = new ArrayList<>();



    @ManyToOne
    @JoinColumn(name = "agence_agence_id")
    private Agence agence;

    @JsonBackReference
    @ToString.Exclude
    @OneToMany(mappedBy = "client", orphanRemoval = true)
    private List<Compte> comptes = new ArrayList<>( );



    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || Hibernate.getClass(this) != Hibernate.getClass(o) ) return false;
        Client client = (Client) o;
        return id != null && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return getClass( ).hashCode( );
    }
}
