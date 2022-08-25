package com.adria.projetbackend.security;

import com.adria.projetbackend.security.jwt.JwtTokenClientFilter;
import com.adria.projetbackend.utils.constants.SecurityAuthConstants;
import com.adria.projetbackend.utils.enums.RolesE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(
        prePostEnabled = false, securedEnabled = false, jsr250Enabled = true
)
public class ApplicationSecurity {

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CustomUserDetails userDetailsService;

    @Autowired
    JwtTokenClientFilter jwtTokenFilter;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf( ).disable( );
        http.sessionManagement( ).sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests((authorize) -> authorize
                .antMatchers(SecurityAuthConstants.ANY_URL_V1).permitAll( )
                .antMatchers(
                        "/v2/api-docs/**",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/webjars/**").permitAll()
                .expressionHandler(webSecurityExpressionHandlerCustom())
                .anyRequest( ).authenticated( )

        );
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build( );

    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    //* role hierarchy:

    @Bean
    public org.springframework.security.access.hierarchicalroles.RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy =   RolesE.ROLE_SUPER_ADMIN+">"+RolesE.ROLE_ADMIN +"\n"+RolesE.ROLE_ADMIN +">" + RolesE.ROLE_USER ;
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }


    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandlerCustom() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }


}