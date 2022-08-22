package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}