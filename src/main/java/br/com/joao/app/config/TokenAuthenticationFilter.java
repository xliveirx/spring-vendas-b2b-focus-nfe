package br.com.joao.app.config;

import br.com.joao.app.domain.User;
import br.com.joao.app.repository.UserRepository;
import br.com.joao.app.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public TokenAuthenticationFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = this.recoverToken(request);

        if(token != null) {
            try {
                String email = tokenService.validateToken(token);
                User user = userRepository.findByEmailAndActiveTrue(email).orElseThrow();

                if(user != null) {
                    Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            } catch(Exception ignored) {

            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {

        var authHeader = request.getHeader("Authorization");

        if(authHeader != null) {

            return authHeader.replace("Bearer ", "");

        }

        return null;

    }
}
