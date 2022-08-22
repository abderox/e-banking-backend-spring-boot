package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}