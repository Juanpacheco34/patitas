package com.jpach.patitas.config;

import com.jpach.patitas.config.filters.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.jpach.patitas.config.filters.JWTAuthenticationFilter;
import com.jpach.patitas.config.jwt.JWTUtils;
import com.jpach.patitas.services.UserServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    // Inyecciones de dependencias
    @Autowired
    private UserServices userServices;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private JWTAuthorizationFilter jwtAuthorizationFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                           AuthenticationManager authenticationManager) throws Exception {

        // Instanciamos JWTAuthenticationFilter el cual vamos a utilizar para el filtro de autenticacion en el objeto httpSecurity
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(jwtUtils);
        // Seteamos el objeto authenticationManager del objeto JWTAuthenticationFilter con nuestro parametro de tipo authenticationManager
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);

        return httpSecurity
            // Configuramos el CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // Deshabilitamos el csrf para evitar conflictos con nuestra configuracion de JWT
            .csrf(AbstractHttpConfigurer::disable)
            // Configuramos las rutas que van a estar disponibles y las que van a estar bloqueadas por falta de autenticacion
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/register").permitAll()
                .anyRequest().authenticated())
            // No vamos a guardar informacion del usuario ya que vamos a trabajar con JWT
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Indicamos cual va ser el filtro a usar para la autenticacion de inicio
            .addFilter(jwtAuthenticationFilter)
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    // Metodo para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuramos el authenticationManager con el que vamos a validar a los usuarios, en la clase JWTAuthenticationFilter
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       PasswordEncoder passwordEncoder) throws Exception {

        AuthenticationManagerBuilder auth = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userServices).passwordEncoder(passwordEncoder);
        return auth.build();
    }


    //    Configuracion del CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration corsConfig = new CorsConfiguration();
        // Como solo permitimos una url usamos List.of() y no Arrays.asList()
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Aplica la configuracion a todas las rutas.
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

}
