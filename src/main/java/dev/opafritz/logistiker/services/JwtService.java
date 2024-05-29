package dev.opafritz.logistiker.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.Password;

@Service
public class JwtService {


    private String secretKey;
    Password key;
    MacAlgorithm alg; // Können wir auch weglassen und es wird automatisch der beste Algorithmus
                      // verwendet

    @Value("${security.jwt.expiration-time:10000}")
    private long jwtExpirationTime;

    public JwtService() {
        alg = Jwts.SIG.HS256;
        this.secretKey="f06b02777272e390e8043e8d7c329eecde074eb650c13c8368ede35a0a178f6b";
        key = Keys.password(secretKey.toCharArray());
    }

    public String buildToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails);
    }

    public Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token);
        return claimsResolver.apply(claims);
    }

    // @TODO try und catch block
    public Claims parseToken(String token) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
