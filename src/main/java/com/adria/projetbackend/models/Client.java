package com.adria.projetbackend.models;

import com.adria.projetbackend.utils.enums.TypePieceID;
import com.adria.projetbackend.utils.enums.TypeSituationFam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//<<<<<<< HEAD
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
//=======
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
//>>>>>>> defcf7048060055280a33b23b7deb72322ab875f
import java.util.Date;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
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
    private List<Compte> comptes = new ArrayList<>( );


}
