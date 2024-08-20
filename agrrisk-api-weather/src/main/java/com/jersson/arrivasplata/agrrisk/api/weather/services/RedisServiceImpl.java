package com.jersson.arrivasplata.agrrisk.api.weather.services;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import com.jersson.arrivasplata.agrrisk.api.weather.config.ResiliencePropertiesConfig;

import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.time.Duration;

@Service
public class RedisServiceImpl implements RedisService {

	private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;
    private final Retry retry;

	public RedisServiceImpl(ReactiveRedisTemplate<String, Object> reactiveRedisTemplate, ResiliencePropertiesConfig propertiesConfig) {
		this.reactiveRedisTemplate = reactiveRedisTemplate;

        // Configuración del retry
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(propertiesConfig.getRedis().getMaxAttempts())
                .waitDuration(propertiesConfig.getRedis().getWaitDuration())
                .build();

        this.retry = RetryRegistry.of(retryConfig).retry("redisService");
	}

	// Método para generar una clave robusta
	@Override
	public String generateKey(String entity, String identifier) {
		return String.format("agrrisk:%s:%s:%s", entity, identifier, UUID.randomUUID().toString());
	}

	// Establecer un valor con un TTL (tiempo de vida)
	@Override
	public Mono<Void> setValue(String key, Object value) {
		return reactiveRedisTemplate.opsForValue().set(key, value, Duration.ofHours(24))
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    System.err.println("Error al establecer el valor en Redis: " + throwable.getMessage());
                })
                .then();
	}

	// Establecer un valor con un TTL (tiempo de vida)
	@Override
	public Mono<Void> setValue(String key, Object value, long timeout) {
		return reactiveRedisTemplate.opsForValue().set(key, value, Duration.ofHours(timeout))
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    System.err.println("Error al establecer el valor en Redis: " + throwable.getMessage());
                })
                .then();
	}

	// Obtener un valor por clave
	@Override
	public Mono<Object> getValue(String key) {
		return reactiveRedisTemplate.opsForValue().get(key)
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    System.err.println("Error al eliminar el valor en Redis: " + throwable.getMessage());
                });
	}

	// Eliminar un valor por clave
	@Override
	public Mono<Boolean> deleteValue(String key) {
		return reactiveRedisTemplate.opsForValue().delete(key)
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    System.err.println("Error al eliminar el valor en Redis: " + throwable.getMessage());
                });
	}

	// Comprobar si una clave existe
	@Override
	public Mono<Boolean> hasKey(String key) {
		return reactiveRedisTemplate.hasKey(key)
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    System.err.println("Error al eliminar el valor en Redis: " + throwable.getMessage());
                });
	}

	// Establecer un TTL para una clave existente
	@Override
	public Mono<Boolean> expireKey(String key, long timeout, TimeUnit unit) {
	    return reactiveRedisTemplate.expire(key, Duration.ofMillis(unit.toMillis(timeout)))
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    System.err.println("Error al eliminar el valor en Redis: " + throwable.getMessage());
                });
	}
	// Obtener el TTL de una clave
	@Override
	public Mono<Duration> getExpire(String key) {
	    return reactiveRedisTemplate.getExpire(key)
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    System.err.println("Error al eliminar el valor en Redis: " + throwable.getMessage());
                });
	}
	
	// Obtener todas las claves que coinciden con un patrón
	@Override
	public Flux<String> getKeys(String pattern) {
		return reactiveRedisTemplate.keys(pattern)
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    System.err.println("Error al eliminar el valor en Redis: " + throwable.getMessage());
                });
	}

	// Obtener todas las claves en Redis
	@Override
	public Flux<String> getAllKeys() {
	    return reactiveRedisTemplate.keys("*")
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    System.err.println("Error al eliminar el valor en Redis: " + throwable.getMessage());
                });
	}

	// Eliminar múltiples claves por patrón
	@Override
	public Mono<Long> deleteKeysByPattern(String pattern) {
		return getKeys(pattern)
				.collectList()
				.flatMap(keys -> reactiveRedisTemplate.delete(Flux.fromIterable(keys)))
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    System.err.println("Error al eliminar el valor en Redis: " + throwable.getMessage());
                });
	}

	// Actualizar el TTL de múltiples claves
	@Override
	public Mono<Void> updateTTLForKeys(String pattern, long timeout, TimeUnit unit) {
		return getKeys(pattern) // Convertir el Set<String> en un Flux<String>
				.flatMap(key -> reactiveRedisTemplate.expire(key, Duration.ofMillis(unit.toMillis(timeout))))
                .transformDeferred(RetryOperator.of(retry))
                .doOnError(throwable -> {
                    System.err.println("Error al establecer el valor en Redis: " + throwable.getMessage());
                })
                .then();
	}
}
