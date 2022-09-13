package com.adria.projetbackend.services.User;


import com.adria.projetbackend.models.UserE;
import com.adria.projetbackend.repositories.UserRepository;
import com.adria.projetbackend.services.Email.EmailDetails;
import com.adria.projetbackend.services.Email.EmailService;
import com.adria.projetbackend.utils.UtilsMethods.UtilsMethods;
import com.adria.projetbackend.utils.constants.SecurityAuthConstants;
import com.adria.projetbackend.utils.storage.Otp;
import com.adria.projetbackend.utils.storage.RedisRepository;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private int EXPIRE_OTP_TIME = 600;

    @Autowired
    EmailService emailService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    RedisRepository redisRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


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

    @Override
    public String sendOTP(String email) {
    String generatedOTP = UtilsMethods.OTP( );
    Otp otp = new Otp(generatedOTP,email, new Date(System.currentTimeMillis( ) + EXPIRE_OTP_TIME * 1000));
    UserE user = userRepository.findByEmail(email);
    redisRepository.addOtp(otp);
        String status_
                = emailService.sendSimpleMail(new EmailDetails(email,
                "Hello there , your OTP is : " + generatedOTP
                +"\nExpires within"+EXPIRE_OTP_TIME / 60 + " minutes",
                "Hello from Beta-Bank ," +
                        " " + user.getUsername( ) + "!",
                ""));
        return " Kindly check your email for the OTP";
    }

    @Override
    public boolean verifyOTP(String username, String otp) {
        Otp otp_ = redisRepository.findOtp(otp);
        if (otp_ == null) return false;
        if (otp_.getEmail().equals(username)) {
            return otp_.getDateEnd( ).after(new Date( ));
        } else {
            redisRepository.deleteOtp(otp);
            return false;
        }
    }

    public void updatePassword(Long clientId, String password, String otp) {

        UserE user = userRepository.findById(clientId).get();
        if ( password.length() < 8 ) {
            throw new RuntimeException("Password must be at least 8 characters");
        }
        if (UtilsMethods.isValidPassword(password) ) {
            boolean reCheckOtp = verifyOTP(user.getEmail( ), otp);
            if ( reCheckOtp ) {
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
                redisRepository.deleteOtp(otp);
            } else {
                throw new RuntimeException("OTP is not valid");
            }
        }
        else {
            throw new RuntimeException("Password is not strong enough ! try to use a stronger password");
        }

    }
}
