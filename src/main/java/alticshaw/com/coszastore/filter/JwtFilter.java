package alticshaw.com.coszastore.filter;

import alticshaw.com.coszastore.exception.JwtCustomException;
import alticshaw.com.coszastore.utils.KeyUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
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
    final
    KeyUtil keyUtil;

    @Autowired
    public JwtFilter(KeyUtil keyUtil) {
        this.keyUtil = keyUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (token != null) {
            try {
                Claims claims = parseJwtToken(token);
                if (isTokenExpired(claims)) {
                    throw new JwtCustomException();
                }

                List<GrantedAuthority> authorities = getAuthoritiesFromClaims(claims);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (JwtCustomException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{ \"message \": \"Token invalid \", \"statusCode \": 401 }");
                return;
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

    private Claims parseJwtToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(keyUtil.getAccessTokenPublicKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new JwtCustomException("Token invalid", 401);
        }
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