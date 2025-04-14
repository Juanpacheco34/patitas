package com.jpach.patitas.config.filters;

import com.jpach.patitas.config.jwt.JWTUtils;
import com.jpach.patitas.services.UserServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Se ejecutara una sola vez por cada peticion http
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    // Inyeccion de dependencias
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserServices userServices;

    // Validaremos al usuario y al token
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Extraemos el token del header
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            // Eliminamos (Bearer) del token
            String token = tokenHeader.substring(7);

            // Validamos que el token sea valido
            if (jwtUtils.isTokenValid(token)) {
                // Extraemos el username del token
                String username = jwtUtils.claimUsernameFromToken(token);

                // Validamos en la base de datos el username que sacamos del token
                UserDetails userDetails = userServices.loadUserByUsername(username);

                // creamos un token de tipo UsernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

                // Indicamos que la autenticacion ah salido bien y se puede continuar con la solicitud
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // Pasada la autenticacion, indicamos que podemos seguir con los demas filtros
        filterChain.doFilter(request, response);

    }
}
