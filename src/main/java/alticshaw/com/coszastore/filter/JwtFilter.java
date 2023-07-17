package alticshaw.com.coszastore.filter;

import alticshaw.com.coszastore.exception.CustomException;
import alticshaw.com.coszastore.exception.JwtCustomException;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import alticshaw.com.coszastore.utils.KeyUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    KeyUtil keyUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (token != null) {
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(keyUtil.getAccessTokenPublicKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();


                if (isTokenExpired(claims)) {
                    throw new JwtCustomException("Invalid Token", 401);
                }

                List<GrantedAuthority> authorities = getAuthoritiesFromClaims(claims);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (JwtCustomException e){
                throw new JwtCustomException("Token  Expired", 401);
            } catch (Exception ex) {
                throw new CustomException("Token invalid");
            }
        }
        filterChain.doFilter(request, response);
    }

    private List<GrantedAuthority> getAuthoritiesFromClaims(Claims claims) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String role = claims.get("role", String.class);
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isTokenExpired(Claims claims) {
        Instant expirationInstant = claims.getExpiration().toInstant();
        Instant now = Instant.now();
        return expirationInstant != null && expirationInstant.isBefore(now);
    }
}
