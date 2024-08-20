package com.jersson.arrivasplata.agrrisk.api.weather.services;

import reactor.core.publisher.Mono;

public interface WeatherService {
	 Mono<String> getWeatherByCity(String city);
}
