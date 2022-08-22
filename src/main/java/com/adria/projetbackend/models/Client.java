package com.adria.projetbackend.models;

import com.adria.projetbackend.utils.enums.TypePieceID;
import com.adria.projetbackend.utils.enums.TypeSituationFam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("CLIENT")
@Data @AllArgsConstructor @NoArgsConstructor
public class Client extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long id;

    private String identiiantClient;
    private String numPieceIdentite;

    @Enumerated(EnumType.STRING)
    private TypePieceID typePieceID;

    @Enumerated(EnumType.STRING)
    private TypeSituationFam situationFamilial;

    private String metier;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;
}
