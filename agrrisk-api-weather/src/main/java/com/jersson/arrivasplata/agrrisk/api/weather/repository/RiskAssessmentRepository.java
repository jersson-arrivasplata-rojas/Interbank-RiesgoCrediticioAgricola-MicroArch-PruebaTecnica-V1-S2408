package com.jersson.arrivasplata.agrrisk.api.weather.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jersson.arrivasplata.agrrisk.api.weather.model.RiskAssessment;

import reactor.core.publisher.Mono;

public interface RiskAssessmentRepository extends ReactiveMongoRepository<RiskAssessment, String> {
    Mono<RiskAssessment> findByCity(String city);
}
