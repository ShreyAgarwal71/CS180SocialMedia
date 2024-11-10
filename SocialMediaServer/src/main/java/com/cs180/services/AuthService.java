package com.cs180.services;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import com.cs180.db.UserCollection;
import com.cs180.db.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class AuthService implements Service {
    // JWT Key is intentionally hardcoded for the sake of this assignment
    private static final SecretKey JWT_SECRET_KEY = generateKeyFromString(
            "57e9f031aec6089a1560f260886959d8307ae7b04971dd276f76e4984dcd403414953bdc7727cc880113fa33dbcbf77ec8645872dae1b016c996db75cec7bf09cac58aba6f9eb913bf1ea9e14249d0ceea0e897885c0994728cb0bdb07117dc0335d90b4ce15a9eae5dc662fd9f8966062bcfa5bb3bb04525a9af21d1dbc97878bed2179101574a30a6a5bc59770e682ca2dd148a51155902fe94c4a94189e09ef3c670f7e8d74112a55345db46de2f59d1e4840aec46ae294785762eec7615b9a8c3f60b5ff71dff291f85deb1e5a3fc3ac49db083ef2a52a2534644aeaeac0ac5f7075dfab696a7246f5a0744652ad009bcfe189ad2c30a63115eed2aee494");
    private static final long JWT_ACCESS_TOKEN_EXPIRATION = 1000 * 60; // 1 minute

    private static final UserCollection users = db.getUserCollection();

    public static User signInWithEmailAndPassword(String email, String password) {
        String hashedPassword = getHashedPassword(password);

        return users.findOne(user -> user.getEmail().equals(email));
    }

    // TODO: Implement this method
    private static String getHashedPassword(String password) {
        return password;
    }

    public static String generateAccessToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .claim("token_type", "access")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_EXPIRATION))
                .signWith(JWT_SECRET_KEY)
                .compact();
    }

    public UUID validateAccessToken(String accessToken) {
        JwtParser parser = Jwts.parser()
                .verifyWith(JWT_SECRET_KEY)
                .build();

        Claims claims = parser.parseSignedClaims(accessToken).getPayload();

        if (claims.get("token_type").equals("access")) {
            return UUID.fromString(claims.getSubject());
        }

        return null;
    }

    private static SecretKey generateKeyFromString(String keyString) {
        byte[] keyBytes = keyString.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
