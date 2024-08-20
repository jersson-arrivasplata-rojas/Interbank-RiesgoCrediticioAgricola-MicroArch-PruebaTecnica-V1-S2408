package com.jersson.arrivasplata.agrrisk.servergateway.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String[] SECURITY_MATCHER_LIST = {
    		"/api/v1/**",
            "/public/**",
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/actuator/**"
    };
    
    @Bean
    @ConditionalOnBean(ReactiveClientRegistrationRepository.class)
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity, 
                                                         ReactiveClientRegistrationRepository clientRegistrationRepository) {
        httpSecurity
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers(SECURITY_MATCHER_LIST).permitAll()
                .anyExchange().authenticated()
            )
            .oauth2Login(Customizer.withDefaults())
            .oauth2ResourceServer(oauth2ResourceServer ->
                oauth2ResourceServer.jwt(Customizer.withDefaults())
            );


        return httpSecurity.build();
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