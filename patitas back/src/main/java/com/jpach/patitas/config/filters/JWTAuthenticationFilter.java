package com.jpach.patitas.config.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpach.patitas.config.jwt.JWTUtils;
import com.jpach.patitas.models.Users;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * Realizamos la autenticacion del usuario mediante el usuario y contraseña
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private JWTUtils JU = new JWTUtils();

  // Intanciamos la clase JWTUtils por medio del constructor
  public JWTAuthenticationFilter(JWTUtils jwtUtils) {
    this.JU = jwtUtils;
  }

  // Intento de autenticacion
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {

    Users user = null;
    String email = "";
    String password = "";

    try {

      // Mapeamos del json a objeto java
      user = new ObjectMapper().readValue(request.getInputStream(), Users.class);

      email = user.getEmail();
      password = user.getPassword();
      
        System.out.println(email + "Aqui deberia estar el email");

    } catch (Exception e) {

    }

    // se crea el token con estas datos
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
        password);

    // Verificamos si las credenciales son correctas
    return getAuthenticationManager().authenticate(authenticationToken);

  }

  // Autenticacion exitosa
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {

    // Obtenemos el objeto con los detalles del usuario 
    // Objeto user de Spring
    User user = (User) authResult.getPrincipal();

    // Aqui pasamos el email que es nuestro username solo que el objeto de User de spring
    // Solo nos da parametro de login
    String token = JU.generateToken(user.getUsername());

    // En el header enviamos el token
    response.addHeader("Authorization", token);

    Map<String, Object> httpResponse = new HashMap<>();
    httpResponse.put("token", token);
    httpResponse.put("message", "Autenticacion Correcta");
    httpResponse.put("Usuario", user.getUsername());

    // Convetimos en JSON y enviamos
    response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
    response.setStatus(HttpStatus.OK.value());
    response.setContentType("application/json");
    response.getWriter().flush();


    super.successfulAuthentication(request, response, chain, authResult);
  }
}
