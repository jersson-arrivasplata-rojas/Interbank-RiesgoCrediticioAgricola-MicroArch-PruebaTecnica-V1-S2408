package com.jersson.arrivasplata.agrrisk.api.weather.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RedisConnectionTest implements CommandLineRunner {

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Override
    public void run(String... args) throws Exception {
        Mono<String> pingResult = reactiveRedisTemplate.getConnectionFactory().getReactiveConnection().ping();
        pingResult.subscribe(result -> {
            if ("PONG".equals(result)) {
                System.out.println("Redis connection successful: " + result);
            } else {
                System.out.println("Redis connection failed: " + result);
            }
        });
    }
}