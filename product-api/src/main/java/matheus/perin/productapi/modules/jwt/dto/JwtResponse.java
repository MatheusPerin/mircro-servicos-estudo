package matheus.perin.productapi.modules.jwt.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    private Long id;
    private String name;
    private String email;

    public static JwtResponse create(Claims claims) {
        return new ObjectMapper().convertValue(claims.get("authUser"), JwtResponse.class);
    }

}
