package com.adria.projetbackend.utils.storage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Date;

@Repository
public class RedisRepositoryImpl implements RedisRepository {

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
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void add(JwtToken object) {
        hashOperations.put(KEY,object.getToken(),object.getUsername());
    }

    @Override
    public void delete(String token) {
        hashOperations.delete(KEY,token);
    }

    @Override
    public JwtToken findToken(String token) {
        return new JwtToken( token,(String) hashOperations.get(KEY,token));
    }

    @Override
    public boolean isFoundToken(String token) {
        return findToken(token) != null;
    }

    @Override
    public void addOtp(Otp object) {
        hashOperations.put(KEY_2,object.getOtp(),object);
    }

    @Override
    public void deleteOtp(String otp) {
        hashOperations.delete(KEY_2,otp);
    }

    @Override
    public Otp findOtp(String otp) {
        return (Otp) hashOperations.get(KEY_2,otp);
    }

    @Override
    public void deleteAllUnusedOtp() {
        hashOperations.entries(KEY_2).forEach((k,v)->{
            Otp otp = (Otp) v;
            System.out.println(otp);
            if (((Otp) v).getDateEnd().before(new Date()) ) deleteOtp(otp.getOtp());
        });
    }


}
