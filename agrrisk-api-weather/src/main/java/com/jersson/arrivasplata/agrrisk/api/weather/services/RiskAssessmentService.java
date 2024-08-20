package com.jersson.arrivasplata.agrrisk.api.weather.services;

import reactor.core.publisher.Mono;

public interface RiskAssessmentService {
	Mono<String> assessRiskByCity(String city);
}
