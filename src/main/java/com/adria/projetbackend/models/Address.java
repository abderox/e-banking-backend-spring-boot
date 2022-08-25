package com.adria.projetbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Address {
    @Column(name = "address_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( length = 40)
    private String region;
    @Column( length = 40)
    private String ville;
    @Column( length = 40)
    private String province;

    public Address(String provincAddress ) {

        this.province = provincAddress;
    }
}
