package com.jersson.arrivasplata.agrrisk.api.weather.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.jersson.arrivasplata.agrrisk.api.weather.config.OpenWeatherMapConfig;
import com.jersson.arrivasplata.agrrisk.api.weather.config.ResiliencePropertiesConfig;

import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import reactor.core.publisher.Mono;

@Service
public class ClimateServiceImpl implements ClimateService {

    private final WebClient webClient;
    private final OpenWeatherMapConfig config;
    private final Retry retry;

    public ClimateServiceImpl(WebClient.Builder webClientBuilder, OpenWeatherMapConfig config, ResiliencePropertiesConfig propertiesConfig) {
        this.webClient = webClientBuilder.baseUrl(config.getBaseUrl()).build();
        this.config = config;
        // Configuración del retry
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(propertiesConfig.getRedis().getMaxAttempts())
                .waitDuration(propertiesConfig.getRedis().getWaitDuration())
                .build();

        this.retry = RetryRegistry.of(retryConfig).retry("climateService");

    }

    @Override
    public Mono<String> getCurrentWeather(String city) {
        String url = config.getWeatherByCityUrl(city);
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    // Manejo de errores
                    System.err.println("Error al obtener el clima: " + throwable.getMessage());
                });
    }

    @Override
    public Mono<String> getOneCallWeather(double latitude, double longitude) {
        String url = config.getOneCallUrl(latitude, longitude);
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    // Manejo de errores
                    System.err.println("Error al obtener el clima: " + throwable.getMessage());
                });
    }

    @Override
    public Mono<String> getHistoricalWeather(double latitude, double longitude, String date) {
        String url = config.getHistoricalWeatherUrl(latitude, longitude, date);
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    // Manejo de errores
                    System.err.println("Error al obtener el clima: " + throwable.getMessage());
                });
    }

    @Override
    public Mono<String> getAirPollutionData(double latitude, double longitude) {
        String url = config.getAirPollutionUrl(latitude, longitude);
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    // Manejo de errores
                    System.err.println("Error al obtener el clima: " + throwable.getMessage());
                });
    }

}
