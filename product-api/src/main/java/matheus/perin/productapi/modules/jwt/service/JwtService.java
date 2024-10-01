package matheus.perin.productapi.modules.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import matheus.perin.productapi.config.exception.AuthenticationException;
import matheus.perin.productapi.modules.jwt.dto.JwtResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class JwtService {

    @Value("${app.secrets.jwt}")
    private String secret;

    public void validate(String token) {
        token = extractToken(token);

        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

            JwtResponse response = JwtResponse.create(claims);

            if (Optional.ofNullable(response).map(JwtResponse::getId).isEmpty())
                throw new AuthenticationException("The user is not valid");
        } catch (Exception e) {
            throw new AuthenticationException("The user is not valid");
        }
    }

    private String extractToken(String token) {
        if (ObjectUtils.isEmpty(token))
            throw new AuthenticationException("The token was not informed");

        if (token.toLowerCase().contains("bearer "))
            return token.replace("bearer ", "");

        return token;
    }

}
