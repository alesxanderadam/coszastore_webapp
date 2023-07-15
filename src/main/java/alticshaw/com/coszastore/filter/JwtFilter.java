package alticshaw.com.coszastore.filter;

import alticshaw.com.coszastore.exception.JwtCustomException;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import alticshaw.com.coszastore.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Resource
    JwtHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if(token != null){
            Claims claims = jwtHelper.decodeToken(token);
            if (isTokenExpired(claims)) {
                throw new JwtCustomException(new MessageResponse().unthorization("Token"), 401);
            }
            List<GrantedAuthority> authorities = getAuthoritiesFromClaims(claims);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
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
        Date expirationDate = claims.getExpiration();
        return expirationDate != null && expirationDate.before(new Date());
    }
}
