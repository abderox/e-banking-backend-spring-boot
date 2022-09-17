package com.adria.projetbackend.utils.storage;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class RedisRepositoryImpl implements RedisRepository {


    // ? redis-cli FLUSHALL to delete all keys in redis database

    private static final String KEY = "UserToken";
    private static final String KEY_2 = "updatePassword";

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;

    }

    // ! I love this annotation
    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash( );
    }

    @Override
    public void add(JwtToken object) {
        hashOperations.put(KEY, object.getToken( ), object);
    }

    @Override
    public void delete(String token) {
        hashOperations.delete(KEY, token);
    }

    @Override
    public JwtToken findToken(String token) {
        return (JwtToken) hashOperations.get(KEY, token);
    }

    @Override
    public boolean isFoundToken(String token) {
        return findToken(token) != null;
    }

    @Override
    public List<String> userSessions(String username) {
        List<String> list = new ArrayList<>( );
        hashOperations.entries(KEY).forEach((k, v) -> {
            JwtToken jwtToken = (JwtToken) v;
            if ( jwtToken.getUsername( ).equals(username) ) {
                list.add(jwtToken.getAgent( ));
            }
        });
        return list;

    }

    @Override
    public void addOtp(Otp object) {
        hashOperations.put(KEY_2, object.getOtp( ), object);
    }

    @Override
    public void deleteOtp(String otp) {
        hashOperations.delete(KEY_2, otp);
    }

    @Override
    public Otp findOtp(String otp) {
        return (Otp) hashOperations.get(KEY_2, otp);
    }

    @Override
    public void deleteAllUnusedOtp() {
        hashOperations.entries(KEY_2).forEach((k, v) -> {
            Otp otp = (Otp) v;
            System.out.println(otp);
            if ( ((Otp) v).getDateEnd( ).before(new Date( )) ) deleteOtp(otp.getOtp( ));
        });
    }

    @Override
    public void deleteAllUnusedJwts() {
        hashOperations.entries(KEY).forEach((k, v) -> {
            delete(k.toString( ));
        });
    }

    // TODO : dev purpose only
    public void checkAllJwts() {
        hashOperations.entries(KEY).forEach((k, v) -> {
            System.out.println(v);
            System.out.println(k);
        });
    }

    @Override
    public void checkValidJwts() {
        hashOperations.entries(KEY).forEach((k, v) -> {
            JwtToken jwtToken = (JwtToken) v;
            System.out.println("jwtToken : " + jwtToken);
            if ( jwtToken.getExpirationDate( ).before(new Date( )) ) delete(k.toString( ));
        });
    }

    @Override
    public String getAllActiveSessions(String username) throws JsonProcessingException {
        List<Map<String, String>> list = new ArrayList<>( );

        hashOperations.entries(KEY).forEach((k, v) -> {
            JwtToken jwtToken = (JwtToken) v;
            if ( jwtToken.getUsername( ).equals(username) ) {
                Map<String, String> map = new HashMap<>( );
                map.put("agent", jwtToken.getAgent( ));
                map.put("token", jwtToken.getToken( ));
                map.put("expirationDate", jwtToken.getExpirationDate( ).toString( ));
                list.add(map);
            }
        });
            return new ObjectMapper( ).writeValueAsString(list);
    }


}
