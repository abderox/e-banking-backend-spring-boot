package com.adria.projetbackend.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UpdateTimestamp;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.*;


@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uc_user_username_email", columnNames = {"username", "email"})
})
@NoArgsConstructor
public class User {

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
    @Email
    @Column(name="email"  )
    private String email;
    @NotNull
    @Column(name="password" , nullable = false )
    private String password;
    @Column(name = "telephone", unique = true, length = 15)
    private String telephone;
    @Column( length = 40)
    private String region;
    @Column( length = 40)
    private String address;
    @Column( length = 40)
    private String province;
    // timestamps : creation date
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="created_at" , nullable = false, updatable = false)
    private Date createdAt;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )

    private Set<Role> roles = new HashSet<>( );

    public User(String username, String password) {
        this.username = username;
        this.password = encoder.encode(password);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }








}
