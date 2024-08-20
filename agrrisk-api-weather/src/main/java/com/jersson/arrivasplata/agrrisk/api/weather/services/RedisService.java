package com.jersson.arrivasplata.agrrisk.api.weather.services;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RedisService {

    String generateKey(String entity, String identifier);

    Mono<Void> setValue(String key, Object value);

    Mono<Void> setValue(String key, Object value, long timeout);

    Mono<Object> getValue(String key);

    Mono<Boolean> deleteValue(String key);

    Mono<Boolean> hasKey(String key);

    Mono<Boolean> expireKey(String key, long timeout, TimeUnit unit);

    Mono<Duration> getExpire(String key);

    Flux<String> getKeys(String pattern);

    Flux<String> getAllKeys();

    Mono<Long> deleteKeysByPattern(String pattern);

    Mono<Void> updateTTLForKeys(String pattern, long timeout, TimeUnit unit);
}
