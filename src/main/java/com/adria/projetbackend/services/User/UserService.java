package com.adria.projetbackend.services.User;


import com.adria.projetbackend.models.UserE;
import com.adria.projetbackend.repositories.UserRepository;
import com.adria.projetbackend.utils.constants.SecurityAuthConstants;
import com.adria.projetbackend.utils.storage.RedisRepository;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    Logger logger = LoggerFactory.getLogger(getClass( ));

    // ! in seconds !
    @Value("${app.jwt.expire}")
    private int EXPIRE_TOKEN_TIME;
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    RedisRepository redisRepository;


    public UserE findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String genAccessToken(UserE user) {
        return Jwts.builder( )
                .setSubject(user.getId( ) + "," + user.getEmail( ))
                .claim("roles", user.getRoles( ).toString( ))
                .setIssuer("adria-bt-stage-pfa")
                .setIssuedAt(new Date( ))
                .setExpiration(new Date(System.currentTimeMillis( ) + EXPIRE_TOKEN_TIME * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact( );
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser( ).setSigningKey(SECRET_KEY).parseClaimsJws(token.replace(SecurityAuthConstants.TOKEN_PREFIX, ""));
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("ExpiredJwtException: {}", e.getMessage( ));
        } catch (IllegalArgumentException e) {
            logger.error("Exception: {}", e.getMessage( ));
        } catch (MalformedJwtException e) {
            logger.error("MalformedJwtException: {}", e.getMessage( ));
        } catch (UnsupportedJwtException e) {
            logger.error("UnsupportedJwtException: {}", e.getMessage( ));
        } catch (SignatureException e) {
            logger.error("SignatureException: {}", e.getMessage( ));
        }
        return false;
    }

    @Override
    public String getDetailsFromSubject(String token) {
        return Jwts.parser( ).setSigningKey(SECRET_KEY).parseClaimsJws(token.replace(SecurityAuthConstants.TOKEN_PREFIX, "")).getBody( ).getSubject( );
    }

    @Override
    public Claims parseClaims(String token) {
        return Jwts.parser( ).setSigningKey(SECRET_KEY).parseClaimsJws(token.replace(SecurityAuthConstants.TOKEN_PREFIX, "")).getBody( );
    }

    @Override
    public Set<String> getAuthorities(String token) throws IOException {
        // TODO THIS METHOD IS FOR TESTING PURPOSE ONLY
        // ! c'est la façon de récupérer les roles dans le token
        Claims claims = parseClaims(token);
        String auth = (String) claims.get("roles");
        System.out.println("auth : " + auth);
        auth = auth.replace("[", "").replace("]", "");
        List<String> auth__ = Arrays.asList(auth.split(","));
        auth__ = auth__.stream( ).filter(role -> role.contains("name") && role.contains("ROLE")).map(role -> role.replace("name=", "").replace(")", "")).collect(Collectors.toList( ));
        return new HashSet<>(auth__);

    }

    @Override
    public UserE findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public boolean isUserFullyAuthorized()
    {
        String username ="";
        if ( SecurityContextHolder.getContext().getAuthentication()==null ) return false;
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        boolean isFoundToken =redisRepository.isFoundToken(credentials);

        if ( isFoundToken )  username = redisRepository.findToken(credentials).getUsername();
        if(username  == null || username.equals("")) return false ;
        return username.equals(userDetails.getUsername());
    }
}
