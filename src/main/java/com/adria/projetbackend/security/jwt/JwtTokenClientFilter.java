package com.adria.projetbackend.security.jwt;

import com.adria.projetbackend._dev.OnStartEventListner;
import com.adria.projetbackend.models.Banquier;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.models.UserE;
import com.adria.projetbackend.services.User.IUserService;
import com.adria.projetbackend.services.User.UserDetailsImpl;
import com.adria.projetbackend.utils.enums.RolesE;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;


@Component
public class JwtTokenClientFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(JwtTokenClientFilter.class);

    @Autowired
    IUserService userService;


    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {


        if ( !hasAuthorizationBearer(request) ) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getAccessToken(request);
        if ( !userService.validateToken(token) ) {
            filterChain.doFilter(request, response);
            return;
        }


        setAuthenticationCoontext(request, token);
        filterChain.doFilter(request, response);

    }

    private void setAuthenticationCoontext(HttpServletRequest request, String token) throws IOException {
        System.out.println("setAuthenticationCoontext");
        UserDetailsImpl userDetails = getUserDetails(token);
        logger.debug("userDetails : {}", userDetails);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities( ));
        authentication.setDetails(new WebAuthenticationDetailsSource( ).buildDetails(request));
        System.out.println("authentication : " + authentication.getPrincipal( ).toString( ));
        SecurityContextHolder.getContext( ).setAuthentication(authentication);
    }

    private UserDetailsImpl getUserDetails(String token) throws IOException {
        logger.debug("token : {}", token);
        UserE user = new Banquier(  );
        Set<String> roles = userService.getAuthorities(token);
        if ( roles.contains(RolesE.ROLE_ADMIN) ) {
            user = new Banquier( );
        } else {
            user = new Client( );
        }


        String[] splitSuubject = userService.getDetailsFromSubject(token).split(",");
        user.setId(Long.parseLong(splitSuubject[0]));
        user.setEmail(splitSuubject[1]);
        return new UserDetailsImpl(true, user, new ArrayList<>( ), roles);
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request) {

        String header = request.getHeader("Authorization");
        if ( ObjectUtils.isEmpty(header) || !header.startsWith("Bearer") ) {
            return false;
        }
        return true;
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim( );
        return token;
    }


}