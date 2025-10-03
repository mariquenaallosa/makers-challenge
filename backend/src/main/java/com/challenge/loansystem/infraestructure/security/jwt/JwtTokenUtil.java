package com.challenge.loansystem.infraestructure.security.jwt;


import com.challenge.loansystem.domain.model.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    JwtProperties jwtProperties;



    public String generateToken(String email, List<String> role){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getExpiration()*1000L);


        return Jwts.builder()
                .setSubject(email)
                .claim("authorities", role)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();


    }

}
