package org.vitalii.fedyk.bibliotopia.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.vitalii.fedyk.bibliotopia.repository.TokenRepository;
import org.vitalii.fedyk.bibliotopia.service.JwtService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${variables.security.jwt.secret-key}")
    private String secretKey;
    @Value("${variables.security.jwt.access-token-expiration}")
    private long accessTokenExpirationMs;
    @Value("${variables.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpirationMs;
    private final TokenRepository tokenRepository;

    @Override
    public String generateAccessToken(final UserDetails userDetails) {
        return generateToken(userDetails, accessTokenExpirationMs);
    }

    @Override
    public String generateRefreshToken(final UserDetails userDetails) {
        return generateToken(userDetails, refreshTokenExpirationMs);
    }

    @Override
    public <T> T extractClaim(final String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    @Override
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isValidAccessToken(final String accessToken, final UserDetails userDetails) {
        final String username = extractUsername(accessToken);

        final boolean isAvailable = tokenRepository.findByAccessToken(accessToken)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return username.equals(userDetails.getUsername()) && isAvailable && !isTokenExpired(accessToken);
    }

    @Override
    public boolean isValidRefreshToken(final String refreshToken, final UserDetails userDetails) {
        final String username = extractUsername(refreshToken);

        final boolean isAvailable = tokenRepository.findByRefreshToken(refreshToken)
                .map(t ->{
                    System.out.println(t.isLoggedOut());return !t.isLoggedOut();})
                .orElse(false);

        return username.equals(userDetails.getUsername()) && isAvailable && !isTokenExpired(refreshToken);
    }

    private Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    private String generateToken(final UserDetails userDetails, final long expirationMs) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

