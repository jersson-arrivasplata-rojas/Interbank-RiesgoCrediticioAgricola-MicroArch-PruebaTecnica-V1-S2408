package com.jersson.arrivasplata.agrrisk.api.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
public class RedisConfig {

	@Bean
	public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(
			ReactiveRedisConnectionFactory connectionFactory) {

		// Use StringRedisSerializer for key and Jackson2JsonRedisSerializer for value
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);

		RedisSerializationContext<String, Object> serializationContext = RedisSerializationContext
				.<String, Object>newSerializationContext(stringRedisSerializer)
				.key(SerializationPair.fromSerializer(stringRedisSerializer))
				.value(SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
				.hashKey(SerializationPair.fromSerializer(stringRedisSerializer))
				.hashValue(SerializationPair.fromSerializer(jackson2JsonRedisSerializer)).build();

		return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
	}
}
