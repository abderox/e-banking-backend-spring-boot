package com.adria.projetbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Benificiaire {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_benificiare")
    private Long id;
    private String nature;
    private String nom;
    @Column(length = 24 , unique = true, nullable = false)
    private String rib;
    private String intituleVirement;

    @OneToMany(mappedBy = "benificiaire")
    private List<Virement> listVirements = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
