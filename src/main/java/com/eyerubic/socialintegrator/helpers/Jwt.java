package com.eyerubic.socialintegrator.helpers;

import java.util.HashMap;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Jwt {

    @Value("${jwt.secret}")
    private String secret;

    private static final String EMAIL = "email";
    private static final String USERID = "userId";
    private static final String AUTH0 = "auth0";
    
    public String getToken(String email, int userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer(AUTH0)
                .withClaim(EMAIL, email)
                .withClaim(USERID, userId)
                .sign(algorithm);

        } catch (JWTCreationException exception) {
            return "";
        }
    }

    public HashMap<String, String> verifyToken(String token, String secret) { //NOSONAR
        HashMap<String, String> userInfo = new HashMap<>();

        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(AUTH0)
            .build(); 
        DecodedJWT dJwt = verifier.verify(token);
        userInfo.put(EMAIL, String.valueOf(dJwt.getClaim(EMAIL)));
        userInfo.put(USERID, String.valueOf(dJwt.getClaim(USERID)));

        return userInfo;
    }
}
