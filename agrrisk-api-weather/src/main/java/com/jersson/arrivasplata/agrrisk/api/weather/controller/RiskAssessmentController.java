package com.jersson.arrivasplata.agrrisk.api.weather.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jersson.arrivasplata.agrrisk.api.weather.services.RiskAssessmentService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/risks")
public class RiskAssessmentController {

    private final RiskAssessmentService riskAssessmentService;

    public RiskAssessmentController(RiskAssessmentService riskAssessmentService) {
        this.riskAssessmentService = riskAssessmentService;
    }

    @GetMapping("/assess-risk")
    public Mono<String> assessRisk(@RequestParam String city) {
        return riskAssessmentService.assessRiskByCity(city);
    }
}
