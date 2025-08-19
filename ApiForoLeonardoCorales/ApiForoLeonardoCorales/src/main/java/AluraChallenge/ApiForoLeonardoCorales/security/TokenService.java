package AluraChallenge.ApiForoLeonardoCorales.security;


import AluraChallenge.ApiForoLeonardoCorales.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.Instant;


@Service
public class TokenService {
    @Value("${app.jwt.secret}")
    private String secret;


    @Value("${app.jwt.expiration}")
    private long expirationMs;


    public String generateToken(User user) {
        Algorithm alg = Algorithm.HMAC256(secret);
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("uid", user.getId())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(expirationMs))
                .sign(alg);
    }


    public String validateAndGetSubject(String token) {
        Algorithm alg = Algorithm.HMAC256(secret);
        return JWT.require(alg).build().verify(token).getSubject();
    }
}