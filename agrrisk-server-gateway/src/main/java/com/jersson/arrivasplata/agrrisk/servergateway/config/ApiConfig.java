package com.jersson.arrivasplata.agrrisk.servergateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class ApiConfig {

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				  // Ruta para "/api-weather/v3/api-docs"
		        .route("api-weather-docs", r -> r.path("/api-weather/v3/api-docs").and().method(HttpMethod.GET).uri("lb://API-WEATHER"))
		        // Ruta para "/api/v1/weathers/**"
		        .route("api-weather", r -> r.path("/api/v1/weathers/**").uri("lb://API-WEATHER"))
				 // Ruta para "/api-auth/v3/api-docs"
		        .route("api-auth-docs", r -> r.path("/api-auth/v3/api-docs").and().method(HttpMethod.GET).uri("lb://API-AUTH"))
		        // Ruta para "/api/auth/**"
		        .route("api-auth", r -> r.path("/api/v1/auth/**").uri("lb://API-AUTH"))
		        .build();
	}

}
