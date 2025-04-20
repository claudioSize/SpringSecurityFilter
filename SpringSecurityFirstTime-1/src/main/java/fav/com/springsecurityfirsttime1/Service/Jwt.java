package fav.com.springsecurityfirsttime1.Service;

import fav.com.springsecurityfirsttime1.Entitys.DTOClaim;
import fav.com.springsecurityfirsttime1.Entitys.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class Jwt {

    private final String secretKet = "FH5g7HeVQU1fRHe0POvG6G8MLqDAJFLuMY4ySDdpEB0=";
    private final long jwtExpiration = 360000;

    public String buildToken(final UserEntity userEntity){
        return Jwts.builder()
                .setClaims(Map.of("name",userEntity.getUsername()))
                .setSubject(userEntity.getRol())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration) )
                .signWith(getSingInKey())
                .compact();
    }
    private SecretKey getSingInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKet);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parserBuilder()  // Usamos el nuevo parser
                    .setSigningKey(getSingInKey())  // Verifica la firma del token
                    .build()
                    .parseClaimsJws(token)  // Parseamos el token JWT
                    .getBody();  // Obtenemos los claims del cuerpo del token

            Date expiration = claims.getExpiration();
            return expiration.after(new Date()); // Verificamos si ha expirado
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Si el token es inválido o está mal formado
        }
    }
    public DTOClaim getClaims(String token) {
        Claims claims = Jwts.parserBuilder()  // Usamos el nuevo parser
                .setSigningKey(getSingInKey())  // Verifica la firma del token
                .build()
                .parseClaimsJws(token)  // Parseamos el token JWT
                .getBody();


        return DTOClaim.builder().userName(claims.get("name",String.class)).rol(claims.getSubject()).build();

    }

}
