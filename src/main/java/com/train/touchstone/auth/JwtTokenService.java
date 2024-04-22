package com.train.touchstone.auth;

import com.train.touchstone.user.domain.AuthUser;
import com.train.touchstone.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Service
public class JwtTokenService implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;
    public static final String EMAIL_KEY = "email";
    public static final long JWT_TOKEN_VALIDITY = 60;
    @Value("${jwt.secret}")
    private String secret;



    public String generateToken(Map<String, Object> claims, String subject) {
        claims.put(EMAIL_KEY, subject);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 30))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}
