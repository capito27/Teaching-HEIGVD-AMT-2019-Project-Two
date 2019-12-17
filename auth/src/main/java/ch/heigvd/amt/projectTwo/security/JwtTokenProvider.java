package ch.heigvd.amt.projectTwo.security;


import ch.heigvd.amt.projectTwo.api.model.UserLogin;
import ch.heigvd.amt.projectTwo.entities.UserEntity;
import ch.heigvd.amt.projectTwo.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    /**
     * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
     * microservices environment, this key would be kept on a config-server.
     */
    @Value("${security.jwt.token.secret-key:s3cr3t-k3y}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 60 * 60 * 1000; // 1h

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserLogin userLogin) {
        UserEntity user = userRepository.findByEmail(userLogin.getEmail());

        if (user == null) {
            throw new IllegalArgumentException("User '" + userLogin.getEmail() + "' not found");
        }

        if(!user.getPassword().equals(DigestUtils.sha256Hex(userLogin.getPassword()))){
            throw new IllegalArgumentException("Password illegal");
        }

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("admin", user.isAdmin());
        claims.put(claims.ID, user.getId());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean isAdmin(String token) {
        return (Boolean) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("admin");
    }

    public Integer getId(String token) {
        return Integer.parseInt(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getId());
    }


    public String extractToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ignored) {
        }
        return false;
    }

}