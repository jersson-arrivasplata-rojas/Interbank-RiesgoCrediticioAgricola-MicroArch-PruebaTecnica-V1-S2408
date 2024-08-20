package com.jersson.arrivasplata.agrrisk.api.weather.services;

import org.springframework.stereotype.Service;

import com.jersson.arrivasplata.agrrisk.api.weather.config.ResiliencePropertiesConfig;
import com.jersson.arrivasplata.agrrisk.api.weather.model.Weather;
import com.jersson.arrivasplata.agrrisk.api.weather.repository.WeatherRepository;
import com.jersson.arrivasplata.agrrisk.api.weather.util.RedisKeys;

import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import reactor.core.publisher.Mono;

@Service
public class WeatherServiceImpl implements WeatherService {

	final private WeatherRepository weatherRepository;

	final private RedisService redisService;

	final private ClimateService climateService;
	
    private final Retry retry;

	public WeatherServiceImpl(WeatherRepository weatherRepository, RedisService redisService,
			ClimateService climateService, ResiliencePropertiesConfig propertiesConfig) {
		this.weatherRepository = weatherRepository;
		this.redisService = redisService;
		this.climateService = climateService;
		
	      // Configuración del retry usando los valores de la configuración
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(propertiesConfig.getRedis().getMaxAttempts())
                .waitDuration(propertiesConfig.getRedis().getWaitDuration())
                .build();

        this.retry = RetryRegistry.of(retryConfig).retry("weatherService");
	}

	@Override
	public Mono<String> getWeatherByCity(String city) {
		String cacheKey = RedisKeys.WEATHER_KEY.getKey(city);
		
		return redisService.getValue(cacheKey)
				.cast(String.class)
				.switchIfEmpty(
				    weatherRepository.findByCity(city)
	                .flatMap(weather -> {
	                	return redisService.setValue(cacheKey, weather.getData())
	                    .thenReturn(weather.getData());
	                })
                )
				.switchIfEmpty(
                    climateService.getCurrentWeather(city)
                    .flatMap(apiResponse -> {
                    	
                        Weather weather = Weather.builder()
                            .city(city)
                            .data(apiResponse)
                            .timestamp(System.currentTimeMillis())
                            .build();

                    return weatherRepository.save(weather)
                		 .flatMap(savedWeather -> 
                	        redisService.setValue(cacheKey, apiResponse)
                	            .thenReturn(apiResponse)
                	    );
            })
		)
        .transformDeferred(RetryOperator.of(retry)) // Aplicar el retry
        .doOnError(throwable -> {
            // Manejo de errores
            System.err.println("Error al obtener el clima: " + throwable.getMessage());
        });
	}

}