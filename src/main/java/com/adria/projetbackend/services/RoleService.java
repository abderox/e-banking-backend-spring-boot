package com.adria.projetbackend.services;


import com.adria.projetbackend.models.Role;
import com.adria.projetbackend.repositories.RoleRepository;
import com.adria.projetbackend.utils.enums.RolesE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public Role getRole(RolesE roleName) {
        if ( roleExists(roleName) ) {
            return roleRepository.findByName(roleName);
        }
        throw new RuntimeException("Role not found");
    }

    public Role save(RolesE name) {
        if ( !roleExists(name) ) {
            return roleRepository.save(new Role(name));
        } else {
            return roleRepository.findByName(name);
        }

    }

    public Set<Role> stringToEntityRoles(String[] roles) {

        List<Role> rolesList = new ArrayList<>( );
        if ( roles != null ) {
            for (String role : roles) {
                switch (role.toUpperCase( )) {
                    case RolesE.ToString.ROLE_ADMIN:
                        rolesList.add(getRole(RolesE.ROLE_ADMIN));
                    case RolesE.ToString.ROLE_USER:
                        rolesList.add(getRole(RolesE.ROLE_USER));
                    default:
                        rolesList.add(getRole(RolesE.ROLE_USER));
                }
            }
        } else {
            rolesList.add(getRole(RolesE.ROLE_CLIENT));
        }

        return new HashSet<>(rolesList);
    }





    boolean roleExists(RolesE roleName) {
        return roleName != null && roleRepository.existsByName(roleName);
    }


}
