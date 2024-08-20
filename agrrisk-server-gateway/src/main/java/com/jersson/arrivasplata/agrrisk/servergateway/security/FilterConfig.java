package com.jersson.arrivasplata.agrrisk.servergateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class FilterConfig implements WebFluxConfigurer {
    // Lista blanca de dominios permitidos
    private static final String[] ALLOWED_ORIGINS = {
        "http://localhost:3000",
        "https://example.com"
    };

    /*
     *  Configura CORS a nivel global usando WebFluxConfigurer, ideal para configuraciones simples y comunes.
     * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins(ALLOWED_ORIGINS); // Usar el array de dominios permitidos
    }

    /*
     * Configura un filtro CORS explícito que permite configuraciones más detalladas y personalizadas.
     * */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowedOrigins(java.util.Arrays.asList(ALLOWED_ORIGINS)); // Usar el array de dominios permitidos

        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(corsConfigurationSource);
    }
}
