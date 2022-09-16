package com.adria.projetbackend.utils.storage;


import java.util.List;

public interface RedisRepository {

    void add(JwtToken object);

    void delete(String token);

    JwtToken findToken(String token);

    boolean isFoundToken(String token);

    List<String> userSessions(String username);

    void addOtp(Otp object);

    void deleteOtp(String otp);

    Otp findOtp(String otp);

    void deleteAllUnusedOtp();

    void deleteAllUnusedJwts();

    void checkAllJwts();

    void checkValidJwts();




}
