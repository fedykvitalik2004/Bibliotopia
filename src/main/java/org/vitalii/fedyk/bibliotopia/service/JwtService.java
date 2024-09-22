package org.vitalii.fedyk.bibliotopia.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {
    String generateAccessToken(UserDetails user);

    String generateRefreshToken(UserDetails user);

    <T> T extractClaim(String token, Function<Claims, T> resolver);

    String extractUsername(String token);

    boolean isValidAccessToken(String accessToken, UserDetails userDetails);

    boolean isValidRefreshToken(String refreshToken, UserDetails userDetails);
}