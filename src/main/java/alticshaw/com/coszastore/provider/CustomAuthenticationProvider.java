package alticshaw.com.coszastore.provider;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.exception.AuthCustomException;
import alticshaw.com.coszastore.payload.response.UserResponse;
import alticshaw.com.coszastore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepository.findByEmail(username));
        //Avoid handling null when the entity for the given email is not found.
        return userOptional.map(user -> {
            if (passwordEncoder.matches(password, user.getPassword())) {
                GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(authority);
                return new UsernamePasswordAuthenticationToken(new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getAddress(), user.getPhone_number(), user.getAvatar(), user.getStatus(), user.getRole().getName()), null, authorities);
            } else {
                throw new AuthCustomException("Invalid Email or Password! Try again", 401);
            }
        }).orElse(null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
