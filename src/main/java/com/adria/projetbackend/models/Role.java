package com.adria.projetbackend.models;



import com.adria.projetbackend.utils.enums.RolesE;
import com.adria.projetbackend.utils.enums.TypeUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private RolesE name;


    public Role(RolesE name) {
        this.name = name;
    }


}
