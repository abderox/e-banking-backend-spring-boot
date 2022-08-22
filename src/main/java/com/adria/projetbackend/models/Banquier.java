package com.adria.projetbackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@DiscriminatorValue("banquier")
public class Banquier extends User{
    private String identifiantBanquier;
    private String position;
    private String status;
}
