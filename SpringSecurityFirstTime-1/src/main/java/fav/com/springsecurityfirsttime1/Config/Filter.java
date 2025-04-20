package fav.com.springsecurityfirsttime1.Config;

import fav.com.springsecurityfirsttime1.Entitys.DTOClaim;
import fav.com.springsecurityfirsttime1.Repository.UserRepository;
import fav.com.springsecurityfirsttime1.Service.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {
    @Autowired
    Jwt jwt;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RequestContextFilter requestContextFilter;

    //Creo un filter sirve para ingresar al contexto de spring security la info del usuario para asi ocuparla en otros metodos
    //sin tener que estar validandolo en cada controller
    //Toma el token que viene en el header antes de llegar al controller y revisa con los service de jwt
    //y si estan correctos los sube al contexto y los ocupo en mi controller
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");

            if (!jwt.isTokenValid(token)) throw new ResponseStatusException(HttpStatus.CONFLICT,"Token invalido");
            DTOClaim claims = jwt.getClaims(token);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(claims.getUserName(),null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
