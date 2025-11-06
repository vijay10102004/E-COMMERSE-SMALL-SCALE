package e_Commerse.Application.model.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Use a strong secret (32+ characters). Can be moved to application.properties
    private final String SECRET = "MY_SUPER_SECRET_KEY_FOR_JWT_1234567890";
    private final long EXPIRATION = 86400000; // 1 day in ms

    // Convert secret string to Key
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Generate JWT token
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract claims
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract email (subject)
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // Check if token expired
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
