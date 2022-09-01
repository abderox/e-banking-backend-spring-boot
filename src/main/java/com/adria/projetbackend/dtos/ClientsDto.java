package com.adria.projetbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientsDto {

    private Long id ;

    private String firstName;

    private String lastName;

    @NotBlank
    @Email
    private String email;

    private String identifiantClient;

    @NotBlank
    private String telephone;

    private String status;

    private String codeAgence;

    @NotBlank
    private String provinceAddress;

    private Date createdAt;

}
