package com.jersson.arrivasplata.agrrisk.api.weather.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.jersson.arrivasplata.agrrisk.api.weather.model.Weather;

import reactor.core.publisher.Mono;

@Repository
public interface WeatherRepository extends ReactiveMongoRepository<Weather, String> {
    Mono<Weather> findByCity(String city);
}