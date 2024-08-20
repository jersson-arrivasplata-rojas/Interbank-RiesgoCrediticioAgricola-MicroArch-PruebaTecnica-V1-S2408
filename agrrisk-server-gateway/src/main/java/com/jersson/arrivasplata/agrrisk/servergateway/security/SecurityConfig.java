package com.jersson.arrivasplata.agrrisk.servergateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${jwt.issuer-uri}")
    private String issuerUri;

    private static final String[] SECURITY_MATCHER_LIST = {
    		"/api/v1/**",
            "/public/**",
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/actuator/**",
            "/api/v1/auth/login"
    };
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers(SECURITY_MATCHER_LIST).permitAll()
                .anyExchange().authenticated()
            )
            //.oauth2Login(Customizer.withDefaults())
            .oauth2ResourceServer(oauth2ResourceServer ->
                oauth2ResourceServer.jwt(Customizer.withDefaults())
            );


        return httpSecurity.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
    }
    
    @Bean
    public ServerAuthenticationSuccessHandler authenticationSuccessHandler() {
        return (webFilterExchange, authentication) -> {
            ServerWebExchange exchange = webFilterExchange.getExchange();
            String token = "";

            if (authentication instanceof JwtAuthenticationToken) {
                Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
                token = jwt.getTokenValue();
            }

            exchange.getResponse().getHeaders().add("Content-Type", "application/json");
            return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse().bufferFactory().wrap(("{\"token\": \"" + token + "\"}").getBytes()))
            );
        };
    }
}