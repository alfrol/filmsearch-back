package ee.taltech.iti0203backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.durationMin}")
    private Integer durationMin;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        return generateTokenHelper(new HashMap<>(), userDetails.getUsername());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return getUsernameFromToken(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String createTokenForTests(String username) {
        return generateTokenHelper(new HashMap<>(), username);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(getAllClaimsFromToken(token));
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    private String generateTokenHelper(Map<String, Object> claims, String subject) {
        final long CURRENT_TIME = System.currentTimeMillis();
        final long TOKEN_EXPIRATION = durationMin * 60 * 1000;
        final long EXPIRATION = CURRENT_TIME + TOKEN_EXPIRATION;

        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(CURRENT_TIME))
                .setExpiration(new Date(EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
