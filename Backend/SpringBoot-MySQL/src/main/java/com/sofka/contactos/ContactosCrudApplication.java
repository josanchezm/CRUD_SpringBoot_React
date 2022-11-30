package com.sofka.contactos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase inicial de la aplicación de contactos telefónicos
 *
 */
@SpringBootApplication
public class ContactosCrudApplication {

    /**
     * Método principal con el que inicia el sistema
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ContactosCrudApplication.class, args);
    }

    /**
     * Permitiendo hacer requests desde el frontend (puerto 3000)
     *
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("*").allowedHeaders("*");
            }
        };
    }

}
