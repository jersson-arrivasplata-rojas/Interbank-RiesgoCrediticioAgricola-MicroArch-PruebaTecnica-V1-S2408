package com.jersson.arrivasplata.agrrisk.api.weather.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "weather")
@Data
@Builder
public class Weather {
    @Id
    private String id;
    private String city;
    private String data;
    private long timestamp;
}
