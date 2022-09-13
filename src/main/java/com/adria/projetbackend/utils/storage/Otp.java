package com.adria.projetbackend.utils.storage;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@RedisHash("updatePassword")
@AllArgsConstructor
@NoArgsConstructor
public class Otp implements Serializable {

    private String otp;
    private String email;
    private Date dateEnd;


}
