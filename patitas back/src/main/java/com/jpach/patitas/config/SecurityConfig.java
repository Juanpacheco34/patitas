package com.jpach.patitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jpach.patitas.config.filters.JWTAuthenticationFilter;
import com.jpach.patitas.config.jwt.JWTUtils;
import com.jpach.patitas.services.UserServices;
import java.beans.Customizer;

@Configuration
public class SecurityConfig {
    
    @Autowired
    private UserServices US;
    
    @Autowired
    private JWTUtils JU;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager)
            throws Exception {

        /*
     * Creamos el objeto JWTAuthenticationFilter pasamos el el parametro JWTUtils y
     * le configuramos la proiedad setAuthenticationManager que se encarga de
     * administrar la autenticación de los usuarios
         */
        JWTAuthenticationFilter JAF = new JWTAuthenticationFilter(JU);
        JAF.setAuthenticationManager(authenticationManager);
        
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Deshabilitado por JWT
                .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/login", "/register").permitAll()
                .anyRequest().authenticated())
                .sessionManagement(session -> session
                // No vamos a guardar informacion del usuario ya que vamos a trabajar con JWT
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // Migramos la session cambiando el Id de la session cuando haya fixation de
                // session
                .sessionFixation().migrateSession()
                .invalidSessionUrl("/login")// Sesion no valida
                .maximumSessions(1).expiredUrl("/login")// Expirada la session
                /*
             * SessionRegistry es una interfaz que proporciona métodos para registrar y
             * obtener información
             * sobre las sesiones de usuario en una aplicación. Permite realizar acciones
             * como:
             * Registrar sesiones cuando un usuario inicia sesión.
             * Eliminar sesiones cuando un usuario cierra sesión.
             * Recuperar información sobre las sesiones activas, lo que permite, por
             * ejemplo,
             * verificar cuántas sesiones tiene un usuario.
                 */.sessionRegistry(sessionRegistry()))
                // Indicamos cual va ser el filtro a usar para la autenticacion de inicio
                .addFilter(JAF)
                .build();
    }

    // * Se utiliza para almacenar las sesiones de forma efectivaa para gestionar el
    // estado de las sesiones.
    @Bean
    public SessionRegistry sessionRegistry() {
        
        return new SessionRegistryImpl();
    }

    // Se encarga de encriptar las contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        
        return new BCryptPasswordEncoder();
    }

    // Se encarga de administrar la autenticación de los usuarios
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder)
            throws Exception {

        // Creamos un objeto AuthenticationManagerBuilder para construir nuestro
        // AuthenticationManager para poder autenticar a los usuarios
        AuthenticationManagerBuilder auth = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

        // El parametro ingresado es la inyeccion de el UserService que es una clase que
        // implementa y configura el metodo loadUserByUsername
        // donde configuramos como va a verifcar a los usuarios
        auth.userDetailsService(US).passwordEncoder(passwordEncoder);
        
        return auth.build();
    }
    
}
