package com.jersson.arrivasplata.agrrisk.api.weather.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "openweathermap")
public class OpenWeatherMapConfig {

    private String apiKey;
    private String baseUrl;
    private Endpoints endpoints;

    public static class Endpoints {
        private String currentWeather;
        private String forecast3Hour;
        private String dailyForecast;
        private String oneCall;
        private String historicalWeather;
        private String airPollution;

        // Getters and Setters
        public String getCurrentWeather() { return currentWeather; }
        public void setCurrentWeather(String currentWeather) { this.currentWeather = currentWeather; }

        public String getForecast3Hour() { return forecast3Hour; }
        public void setForecast3Hour(String forecast3Hour) { this.forecast3Hour = forecast3Hour; }

        public String getDailyForecast() { return dailyForecast; }
        public void setDailyForecast(String dailyForecast) { this.dailyForecast = dailyForecast; }

        public String getOneCall() { return oneCall; }
        public void setOneCall(String oneCall) { this.oneCall = oneCall; }

        public String getHistoricalWeather() { return historicalWeather; }
        public void setHistoricalWeather(String historicalWeather) { this.historicalWeather = historicalWeather; }

        public String getAirPollution() { return airPollution; }
        public void setAirPollution(String airPollution) { this.airPollution = airPollution; }
    }

    // Getters and Setters
    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

    public Endpoints getEndpoints() { return endpoints; }
    public void setEndpoints(Endpoints endpoints) { this.endpoints = endpoints; }

    // Método para construir rutas dinámicas
    public String getWeatherByCityUrl(String cityName) {
        return baseUrl + endpoints.getCurrentWeather() + "?q=" + cityName + "&appid=" + apiKey;
    }

    public String getOneCallUrl(double latitude, double longitude) {
        return baseUrl + endpoints.getOneCall() + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;
    }

    public String getHistoricalWeatherUrl(double latitude, double longitude, String date) {
        return baseUrl + endpoints.getHistoricalWeather() + "?lat=" + latitude + "&lon=" + longitude + "&dt=" + date + "&appid=" + apiKey;
    }

    public String getAirPollutionUrl(double latitude, double longitude) {
        return baseUrl + endpoints.getAirPollution() + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;
    }
}
