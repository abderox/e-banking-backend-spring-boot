package com.adria.projetbackend.models;

import com.adria.projetbackend.utils.enums.TypeTransaction;
import com.adria.projetbackend.utils.enums.TypeVirement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Virement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_virement")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeVirement type;

    @ManyToOne
    @JoinColumn(name = "id_transaction")
    private Transaction transaction;




}
