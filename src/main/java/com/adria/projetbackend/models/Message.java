package com.adria.projetbackend.models;


import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || Hibernate.getClass(this) != Hibernate.getClass(o) ) return false;
        Message message = (Message) o;
        return id != null && Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return getClass( ).hashCode( );
    }
}
