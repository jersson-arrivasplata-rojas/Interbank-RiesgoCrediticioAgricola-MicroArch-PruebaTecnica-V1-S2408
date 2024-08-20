package com.jersson.arrivasplata.agrrisk.api.weather.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jersson.arrivasplata.agrrisk.api.weather.services.WeatherService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/weathers")
public class WeatherController {

	private final WeatherService weatherService;

	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@GetMapping(value = "/city/{city}", produces = "application/json")
	public Mono<String> getWeather(@PathVariable String city) {
		return weatherService.getWeatherByCity(city);
	}
}
