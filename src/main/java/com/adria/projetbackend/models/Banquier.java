package com.adria.projetbackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BANQUIER")
@Data @AllArgsConstructor @NoArgsConstructor
public class Banquier extends User{
    private String identifiantBanquier;
    private String position;
    private String status;
}
