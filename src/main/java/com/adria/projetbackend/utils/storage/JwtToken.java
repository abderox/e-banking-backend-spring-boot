package com.adria.projetbackend.utils.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;


@Data
@RedisHash("jwtToken")
@AllArgsConstructor @NoArgsConstructor
public class JwtToken {

    private String token;
    private String username;


}
