package com.jpach.patitas.config.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.jpach.patitas.dto.LoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpach.patitas.config.jwt.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JWTUtils jwtUtils = new JWTUtils();

    // Inyeccion de dependecia mediante el constructor
    public JWTAuthenticationFilter(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    // Validamos al usuario que se esta logeando
    @Override
    @SuppressWarnings("UseSpecificCatch")
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {

        // DTO para mapear los datos que nos envian del login
        LoginDTO user = null;


        String email = "";
        String password = "";

        try {

            // Mapeamos de objeto json a java
            user = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);

            email = user.getUsername();
            password = user.getPassword();

        } catch (Exception e) {
            log.error("Error en la clase JWTAuthenticationFilter: {}", e.getMessage());
        }

        // Encapsulamos las credenciales
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // Verificamos las credenciales que nos pasan con las que tenemos en la base de datos
        return getAuthenticationManager().authenticate(authenticationToken);

    }

    // Si el usuario validado es correcto.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        // Casteamos al usuaro en el objeto User de spring
        User user = (User) authResult.getPrincipal();

        // Generamos el token con el username del usuario
        String token = jwtUtils.generateToken(user.getUsername());

        // Pasamos el token en el header
        response.addHeader("Authorization", token);

        // Creamos un objeto map que enviaremos como respuesta al front
        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);
        httpResponse.put("message", "Autenticacion Correcta");
        httpResponse.put("Usuario", user.getUsername());

        // Convetimos el map en un json y enviamos la respuesta al cliente
        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.getWriter().flush();

        // Completamos el manejo del flujo de autenticacion de spring
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
