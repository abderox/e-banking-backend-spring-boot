package com.adria.projetbackend.models;


import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uc_user_username_email", columnNames = {"username", "email"})
})

@NoArgsConstructor @AllArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column( nullable = false  , length = 25)
    private String username;

    @Column(length = 25)
    private String firstName;

    @Column(length = 25)
    private String lastName;

    @NotNull
    @Column(name="email"  )
    private String email;

    @NotNull
    @Column(name="password" , nullable = false )
    private String password;

    @Column(name = "telephone", unique = true, length = 15)
    private String telephone;

    @Column( length = 40)
    private String rue;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="created_at" , nullable = false, updatable = false)
    private Date createdAt;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id" )
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )

    private Set<Role> roles = new HashSet<>( );

    @OneToMany(mappedBy = "sender", orphanRemoval = true)
    @ToString.Exclude
    private Collection<Message> messages = new ArrayList<>( );

    @OneToMany(mappedBy = "receiver", orphanRemoval = true)
    @ToString.Exclude
    private Collection<Message> recievedMsgs = new ArrayList<>( );


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || Hibernate.getClass(this) != Hibernate.getClass(o) ) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass( ).hashCode( );
    }
}
