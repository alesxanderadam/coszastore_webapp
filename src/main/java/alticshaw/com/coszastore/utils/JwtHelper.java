package alticshaw.com.coszastore.utils;

import alticshaw.com.coszastore.exception.JwtCustomException;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import alticshaw.com.coszastore.payload.response.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtHelper {
    @Value("${jwt.secret.key}")
    private String privateKey;

    @Value("${jwt.public.key}")
    private String publicKey;
    private static final long EXPIRATION_TIME = 2 * 24 * 60 * 60 * 1000; // 2 ngày nha

    public String generateToken(UserResponse user) {
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey1 = keyFactory.generatePrivate(spec);

            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

            return Jwts.builder()
                    .setSubject("coszasotre webapp")
                    .claim("infoUser", user)
                    .claim("role", user.getRole_name())
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(privateKey1, SignatureAlgorithm.RS256)
                    .compact();
        } catch (Exception error) {
            throw new JwtCustomException("Lỗi sinh token", 500);
        }
    }

    public Claims decodeToken(String token) {
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new JwtCustomException(new MessageResponse().invalid("Token"), 401);
        }
    }
}
