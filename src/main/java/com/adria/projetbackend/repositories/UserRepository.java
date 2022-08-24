package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.UserE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserE, Long> {

    UserE findByUsername(String username);

    UserE findByEmail(String email);
}