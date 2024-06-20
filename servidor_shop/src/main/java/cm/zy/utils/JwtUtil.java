package cm.zy.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

/**
 * Utility class for generating and parsing JSON Web Tokens (JWT).
 */
public class JwtUtil {

    private static final String KEY = "yizco";

    /**
     * Generates a JWT token with the provided claims.
     *
     * @param claims the claims to include in the token
     * @return the generated JWT token
     */
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .sign(Algorithm.HMAC256(KEY));
    }

    /**
     * Parses the provided JWT token and returns the claims as a map.
     *
     * @param token the JWT token to parse
     * @return the claims extracted from the token
     */
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

}
