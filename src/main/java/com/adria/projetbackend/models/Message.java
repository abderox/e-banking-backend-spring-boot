package com.adria.projetbackend.models;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private Long id;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String body;
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateEnvoie;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "id_sender")
    private User sender;

   @ManyToOne
    @JoinColumn(name = "id_reciever")
    private User receiver;

}
