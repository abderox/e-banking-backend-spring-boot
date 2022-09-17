package com.adria.projetbackend.security.jwt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @autor abderox
 */




@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogoutSession implements Serializable {

    private String accessToken;

}
