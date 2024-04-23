package com.train.touchstone.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.train.touchstone.user.domain.AuthUser;
import com.train.touchstone.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtTokenService implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;
    public static final String EMAIL_KEY = "email";
    public static final String AUTHORITIES_KEY = "EMAIL";
    public static final long JWT_TOKEN_VALIDITY = 60000 * 30;
    @Value("${jwt.secret}")
    private String secret;


    public String generateToken(Map<String, Object> claims, String subject) {
        claims.put(EMAIL_KEY, subject);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String validateChangeToken(String changeToken) {
        Map<String, Object> claims = getCustomClaimFromToken(changeToken);
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new BadCredentialsException("wrong token");
        }
        DecodedJWT jwt = JWT.decode(changeToken);
        if (jwt.getExpiresAt().before(new Date())) {
            throw new BadCredentialsException("expire token");
        }
        return String.valueOf(claims.get("sub"));
    }

    public Map<String, Object> getCustomClaimFromToken(String token)
            throws IllegalArgumentException, ExpiredJwtException {
        DecodedJWT jwt = JWT.decode(token);
        Map<String, Claim> objectMap = jwt.getClaims();
        String email = objectMap.get("sub").asString();
        Date expireDate = jwt.getExpiresAt();
        Map<String, Object> claims = new HashMap<>();
        String customClaim = objectMap.get(AUTHORITIES_KEY).asString();
        claims.put(AUTHORITIES_KEY, customClaim);
        claims.put("sub", email);
        claims.put("exp", expireDate);
        return claims;
    }
}
