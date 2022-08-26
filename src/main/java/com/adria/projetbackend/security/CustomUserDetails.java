package com.adria.projetbackend.security;


import com.adria.projetbackend.models.Role;
import com.adria.projetbackend.models.UserE;
import com.adria.projetbackend.services.User.IUserService;
import com.adria.projetbackend.services.User.UserDetailsImpl;
import com.adria.projetbackend.services.User.UserService;
import com.adria.projetbackend.utils.enums.RolesE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class CustomUserDetails implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(getClass( ));

    @Autowired
    private IUserService userService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserE user = userService.findUserByEmail(email);
        if ( user == null ) {

            throw new UsernameNotFoundException("No user found with email: " + email);

        }


        return new UserDetailsImpl(user, user.getEmail( ), user.getPassword( ), true, getAuthorities(user.getRoles( )));

    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        logger.debug("authorities : {}", roles);
        System.out.println("ROLES CUSTOM"+roles);
        return getGrantedAuthorities(roles);
    }


    private List<GrantedAuthority> getGrantedAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>( );
        for (Role role : roles) {
            logger.debug("role : {}", role);
            System.out.println("role CUSTOM" + role);
            authorities.add(new SimpleGrantedAuthority(role.getName( ).name( )));
        }
        return authorities;
    }
}
