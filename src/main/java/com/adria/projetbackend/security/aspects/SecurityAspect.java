package com.adria.projetbackend.security.aspects;

import com.adria.projetbackend.exceptions.runTimeExpClasses.RolesNotAllowedExceptions;
import com.adria.projetbackend.services.User.UserDetailsImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Aspect
@EnableAspectJAutoProxy

public class SecurityAspect {

    private String username = "";
    private String password = "";
    private Collection<? extends GrantedAuthority> roles_;
    private boolean authorized = false;


    @Around(value = "@annotation(sba)", argNames = "pjp,sba")
    public Object secure(ProceedingJoinPoint pjp, RolesAllowed sba) throws Throwable {
        String[] roles = sba.roles( );
        System.out.println("roles : " + Arrays.toString(roles));


        if ( SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( ) != null ) {

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext( ).getAuthentication( ).getPrincipal( );
            username = userDetails.getUsername( );
            password = userDetails.getPassword( );
            roles_ = userDetails.getAuthorities( );
            System.out.println("roles auth  : " + roles_);
        } else throw new RuntimeException("Access denied");


        for (String r : roles) {

            for (GrantedAuthority grantedAuthority : roles_) {
                if ( grantedAuthority.getAuthority( ).trim( ).equals(r.trim( )) ) {
                    authorized = true;
                    break;
                }

            }
            System.out.println("authorized : done" + authorized);

        }

        if ( authorized ) {
            return pjp.proceed( );
        } else throw new RolesNotAllowedExceptions("Permissions are not permitted for this roles");


    }


}
