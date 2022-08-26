package com.adria.projetbackend.utils.storage;



public interface RedisRepository {

    void add(JwtToken object);

    void delete(String token);

    JwtToken findToken(String token);

    boolean isFoundToken(String token);




}
