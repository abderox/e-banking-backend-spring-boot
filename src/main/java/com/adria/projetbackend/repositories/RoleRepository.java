package com.adria.projetbackend.repositories;

import com.adria.projetbackend.models.Role;
import com.adria.projetbackend.utils.enums.RolesE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(RolesE name);
    Role findByName(RolesE name);
}