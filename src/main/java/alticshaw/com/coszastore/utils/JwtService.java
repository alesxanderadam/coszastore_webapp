package alticshaw.com.coszastore.utils;

import alticshaw.com.coszastore.dto.TokenDto;
import alticshaw.com.coszastore.exception.JwtCustomException;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import alticshaw.com.coszastore.payload.response.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Component
public class JwtService {
    final
    JwtEncoder accessTokenEncoder;

    final
    JwtEncoder refreshTokenEncoder;

    @Value("${jwt.public.key}")
    private String publicKey;

    public JwtService(JwtEncoder accessTokenEncoder, @Qualifier("jwtRefreshTokenEncoder") JwtEncoder refreshTokenEncoder) {
        this.accessTokenEncoder = accessTokenEncoder;
        this.refreshTokenEncoder = refreshTokenEncoder;
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

    public String generateAccessToken(Authentication authentication) {
        try {
            UserResponse user = (UserResponse) authentication.getPrincipal();
            Instant now = Instant.now();

            JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                    .issuer("alesxander adam")
                    .subject("coszasotre webapp")
                    .claim("infoUser", user)
                    .claim("role", user.getRole_name())
                    .issuedAt(now)
                    .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                    .build();
            return accessTokenEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        } catch (Exception error) {
            throw new JwtCustomException("Error while creating token", 500);
        }
    }

    public String generateRefreshToken(Authentication authentication) {
        try {
            UserResponse user = (UserResponse) authentication.getPrincipal();
            Instant now = Instant.now();

            JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                    .issuer("alesxander adam")
                    .subject("coszastore webapp")
                    .claim("infoUser", user)
                    .claim("role", user.getRole_name())
                    .issuedAt(now)
                    .expiresAt(now.plus(30, ChronoUnit.DAYS))
                    .build();
            return accessTokenEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        } catch (Exception error) {
            throw new JwtCustomException("Lỗi sinh token", 500);
        }
    }

    public TokenDto generateToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserResponse)) {
            throw new BadCredentialsException(
                    MessageFormat.format("Principal {0} is not of UserResponse Type", principal.getClass())
            );
        }

        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(generateAccessToken(authentication));
        String refreshToken;
        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            Instant now = Instant.now();
            Instant expiresAt = jwt.getExpiresAt();
            Duration duration = Duration.between(now, expiresAt);
            long daysUntilExpired = duration.toDays();
            if (daysUntilExpired < 7) {
                refreshToken = generateRefreshToken(authentication);
            } else {
                refreshToken = jwt.getTokenValue();
            }
        } else {
            refreshToken = generateRefreshToken(authentication);
        }
        tokenDto.setRefreshToken(refreshToken);
        return tokenDto;
    }

}
