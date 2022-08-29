package com.adria.projetbackend.security.jwt;

import com.adria.projetbackend.models.Banquier;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.models.UserE;
import com.adria.projetbackend.services.User.IUserService;
import com.adria.projetbackend.services.User.UserDetailsImpl;
import com.adria.projetbackend.utils.storage.RedisRepository;
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
public class JwtTokenFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    IUserService userService;

    @Autowired
    RedisRepository redisRepository;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "*");
//        response.setHeader("Access-Control-Allow-Headers", "*");
//        response.setHeader("Accept","*/*");


        if ( !hasAuthorizationBearer(request) ) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getAccessToken(request);
        if ( !userService.validateToken(token) ) {
            redisRepository.delete(token);
            filterChain.doFilter(request, response);
            return;
        }


        setAuthenticationCoontext(request, token);
        System.out.println("end filter : "+token);
        filterChain.doFilter(request, response);

    }

    private void setAuthenticationCoontext(HttpServletRequest request, String token) throws IOException {
        System.out.println("setAuthenticationCoontext");
        UserDetailsImpl userDetails = getUserDetails(token);
        System.out.println("userDetails : "+userDetails.getAuthorities());
        logger.debug("userDetails : {}", userDetails);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities( ));
        authentication.setDetails(new WebAuthenticationDetailsSource( ).buildDetails(request));
        System.out.println("authentication : " + authentication.getAuthorities());
        SecurityContextHolder.getContext( ).setAuthentication(authentication);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities() );

    }

    private UserDetailsImpl getUserDetails(String token) throws IOException {
        logger.debug("token : {}", token);
        UserE user;
        Set<String> roles = userService.getAuthorities(token);
//        if ( roles.contains("ROLE_ADMIN") ) {
//            System.out.println( "here banquier");
//            user = new Banquier( );
//        } else {
//            user = new Client( );
//            System.out.println( "here client");
//
//        }
        String[] splitSuubject = userService.getDetailsFromSubject(token).split(",");

        if(splitSuubject[1].contains("banq")){
            user = new Banquier( );
        }else{
            user = new Client( );
        }

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
