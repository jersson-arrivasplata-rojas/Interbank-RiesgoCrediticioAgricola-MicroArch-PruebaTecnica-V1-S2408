package com.jersson.arrivasplata.agrrisk.api.weather.services;

import org.springframework.stereotype.Service;

import com.jersson.arrivasplata.agrrisk.api.weather.model.RiskAssessment;
import com.jersson.arrivasplata.agrrisk.api.weather.repository.RiskAssessmentRepository;
import com.jersson.arrivasplata.agrrisk.api.weather.repository.WeatherRepository;

import reactor.core.publisher.Mono;

@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    final private WeatherRepository weatherRepository;

	final private RiskAssessmentRepository riskAssessmentRepository;

    public RiskAssessmentServiceImpl(WeatherRepository weatherRepository, RiskAssessmentRepository riskAssessmentRepository) {
        this.weatherRepository = weatherRepository;
        this.riskAssessmentRepository = riskAssessmentRepository;
    }

	@Override
    public Mono<String> assessRiskByCity(String city) {
        return weatherRepository.findByCity(city)
            .map(weatherData -> {
                
                String riskAssessment = "Evaluación del riesgo basada en el clima";

                RiskAssessment.builder()
                        .city(city)
                        .assessmentResult(riskAssessment)
                        .timestamp(System.currentTimeMillis())
                        .build();

                return riskAssessment;
            })
            .defaultIfEmpty("No hay datos de clima disponibles para evaluar el riesgo.");
    }
}
