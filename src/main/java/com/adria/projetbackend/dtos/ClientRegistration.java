package com.adria.projetbackend.dtos;

import com.adria.projetbackend.utils.enums.TypePieceID;
import com.adria.projetbackend.utils.enums.TypeSituationFam;
import com.adria.projetbackend.utils.enums.TypeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegistration {

    private String username;

    private String firstName;

    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String telephone;

    private String rue;

    @NotBlank
    private String identifiantClient;

    private String numPieceIdentiteClient;

    private String typePieceIDClient;

    private String situationFamilialClient;

    private String metierClient;

    private Date dateNaissanceClient;

    private String statusClient;

    private String codeAgence;

    private String villeAddress;

    private String regionAddress;

    private String provincAddress;

    private String[] roles;

}
