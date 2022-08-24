package com.adria.projetbackend.models;

import com.adria.projetbackend.utils.enums.TypePieceID;
import com.adria.projetbackend.utils.enums.TypeSituationFam;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//<<<<<<< HEAD
//=======
//>>>>>>> defcf7048060055280a33b23b7deb72322ab875f

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor @NoArgsConstructor
@PrimaryKeyJoinColumn(name = "client_id")
public class Client extends User{

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;

    @OneToMany(fetch = FetchType.EAGER , mappedBy = "client")
    private List<Benificiaire> benificiaires = new ArrayList<>();



    @ManyToOne
    @JoinColumn(name = "agence_agence_id")
    private Agence agence;

    @OneToMany(mappedBy = "client", orphanRemoval = true)
    @ToString.Exclude
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
