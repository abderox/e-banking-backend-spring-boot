package com.adria.projetbackend.utils.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;


@Data
@RedisHash("jwtToken")
@AllArgsConstructor @NoArgsConstructor
public class JwtToken implements Serializable {

    private static final long serialVersionUID = -5728723552278952171L;
    private String token;
    private String username;
    private String agent;
    private Date expirationDate;


}
