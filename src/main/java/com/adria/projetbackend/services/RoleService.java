package com.adria.projetbackend.services;


import com.adria.projetbackend.models.Role;
import com.adria.projetbackend.repositories.RoleRepository;
import com.adria.projetbackend.utils.enums.RolesE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public Role getRole(RolesE roleName) {
        if (roleExists(roleName)) {
            return roleRepository.findByName(roleName);
        }
        throw new RuntimeException("Role not found");
    }

    public Role save(RolesE name) {
        if (!roleExists(name)) {
            return roleRepository.save(new Role(name));
        }
        else {
            return roleRepository.findByName(name);
        }

    }

    boolean roleExists(RolesE roleName) {
        return  roleName!=null && roleRepository.existsByName(roleName);
    }


}
