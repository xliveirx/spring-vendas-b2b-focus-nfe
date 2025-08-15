package br.com.joao.app.service;

import br.com.joao.app.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {

    public String generateToken(User user){

        Algorithm algorithm = Algorithm.HMAC256("secret");
        try {
            return JWT.create()
                    .withSubject(user.getUsername())
                    .withIssuer("joao")
                    .withExpiresAt(expiresAt(30))
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public String validateToken(String token){

        DecodedJWT decodedJWT;

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("joao")
                    .build();

            decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();

        } catch (JWTVerificationException e) {
            throw new RuntimeException(e);
        }
    }

    private Instant expiresAt(int min) {
        return Instant.now().plus(Duration.ofMinutes(min));
    }
}
