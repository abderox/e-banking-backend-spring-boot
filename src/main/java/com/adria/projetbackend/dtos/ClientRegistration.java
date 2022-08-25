package com.adria.projetbackend.dtos;

import com.adria.projetbackend.utils.enums.TypePieceID;
import com.adria.projetbackend.utils.enums.TypeSituationFam;
import com.adria.projetbackend.utils.enums.TypeStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
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
    private String numPieceIdentiteClient;

    @NotBlank
    private String typepiece;

    private String familystatus;

    private String metierClient;

    @NotBlank
    private String date_birth;

    private String statusProfile;

    private String codeAgence;

    @NotBlank
    private String provincAddress;

    private String[] roles;

}
