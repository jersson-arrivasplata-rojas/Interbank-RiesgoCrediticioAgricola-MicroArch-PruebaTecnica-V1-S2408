package com.jersson.arrivasplata.agrrisk.api.weather.model;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "risk_assessment")
@Data
@Builder
public class RiskAssessment {
    @Id
    private String id;
    private String city;
    private String assessmentResult;
    private long timestamp;
}