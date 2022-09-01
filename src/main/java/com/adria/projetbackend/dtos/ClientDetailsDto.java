package com.adria.projetbackend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDetailsDto {

    private String[] roles;
    private Long id;
    private String identifiantClient;
    private String lastName;
    private String firstName;
    private String username;
    private String emailUser;
    private String accessToken;
    private String bankName;

}
