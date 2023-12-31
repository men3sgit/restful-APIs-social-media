package com.rse.mobile.webservice.security.jwt.impl;

import com.rse.mobile.webservice.security.jwt.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtServiceImpl implements JwtService {
    public static final Logger LOGGER = LoggerFactory.getLogger(JwtServiceImpl.class);
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    @Override
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty");
        } catch (SignatureException e) {
            LOGGER.error("there is an error with the signature of you token ");
        }
        return false;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public String generateToken(Map<String, Object> extractClaims,
                                UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuer(new Date().toString())
                .setExpiration(calculateExpirationDate())
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    private Date calculateExpirationDate() {
        final int minutesOfExpiry = 30;
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        // Add expirationHours to the current date and time
        calendar.add(Calendar.MINUTE, minutesOfExpiry);
        // Get the updated date and time
        return calendar.getTime();
    }

    // TODO get All properties Claims
    @Override
    public <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }



    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}