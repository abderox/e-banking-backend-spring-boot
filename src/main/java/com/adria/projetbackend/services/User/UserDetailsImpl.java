package com.adria.projetbackend.services.User;


import com.adria.projetbackend.models.UserE;
import com.adria.projetbackend.utils.enums.RolesE;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {

    private final String username;
    private final String password;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;
    private static final long serialVersionUID = 1L;
    private UserE user;
    private Set<String> roles;

    public UserDetailsImpl(UserE user , String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }


    public UserDetailsImpl(boolean enabled, UserE user, Collection<? extends GrantedAuthority> authorities, Set<String> roles) {
        this.enabled = enabled;
        this.user = user;
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.authorities = authorities;
        this.roles = roles;
    }


    public UserE getUser() {
        return user;
    }

    public void setUser(UserE user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if ( roles != null ) {
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
                System.out.println("roles working : "+role);
            }
        }
        else {
            authorities.add(new SimpleGrantedAuthority(RolesE.ToString.ROLE_USER));
            System.out.println("roles empty  : "+ RolesE.ToString.ROLE_USER);
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
