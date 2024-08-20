package com.jersson.arrivasplata.agrrisk.api.weather.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "retry")
public class ResiliencePropertiesConfig {

    private Redis redis;
    
    public static class Redis {
        private Integer maxAttempts;
        private Duration waitDuration;
        
		public Integer getMaxAttempts() {
			return maxAttempts;
		}
		public void setMaxAttempts(Integer maxAttempts) {
			this.maxAttempts = maxAttempts;
		}
		public Duration getWaitDuration() {
			return waitDuration;
		}
		public void setWaitDuration(Duration waitDuration) {
			this.waitDuration = waitDuration;
		}
    }

	public Redis getRedis() {
		return redis;
	}

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

}
