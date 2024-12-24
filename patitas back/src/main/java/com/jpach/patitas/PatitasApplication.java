package com.jpach.patitas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PatitasApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatitasApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
                // Aplica CORS a todas las rutas
                registry.addMapping("/**")
                        // Permite solicitudes desde este origen
                        .allowedOrigins("http://localhost:4200")
                        // Métodos permitidos
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        // Duración de la configuración en caché
                        .maxAge(3600)
                        // Permite el uso de cookies, si es necesario
                        .allowCredentials(true);
            }
        };
    }
}
