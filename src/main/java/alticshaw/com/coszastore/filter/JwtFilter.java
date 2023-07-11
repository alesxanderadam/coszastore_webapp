package alticshaw.com.coszastore.filter;

import alticshaw.com.coszastore.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer")) {
            String token = header.substring(7);
            Claims claims = jwtHelper.decodeToken(token);

            if (claims != null) {
                SecurityContext context = SecurityContextHolder.getContext();
                System.out.println(claims.getSubject());
                UsernamePasswordAuthenticationToken userAuthentication =
                        new UsernamePasswordAuthenticationToken("", "", new ArrayList<>());
                context.setAuthentication(userAuthentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
