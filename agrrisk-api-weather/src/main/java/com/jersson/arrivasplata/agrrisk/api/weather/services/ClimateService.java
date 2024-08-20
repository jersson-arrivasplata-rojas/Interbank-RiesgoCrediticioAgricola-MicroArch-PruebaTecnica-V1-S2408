package com.jersson.arrivasplata.agrrisk.api.weather.services;

import reactor.core.publisher.Mono;

public interface ClimateService {
	Mono<String> getCurrentWeather(String city);

	Mono<String> getOneCallWeather(double latitude, double longitude);

	Mono<String> getHistoricalWeather(double latitude, double longitude, String date);

	Mono<String> getAirPollutionData(double latitude, double longitude);
}
