package com.jersson.arrivasplata.agrrisk.api.weather.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.jersson.arrivasplata.agrrisk.api.weather.services.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();

            if (result instanceof Mono) {
                return ((Mono<?>) result)
                        .doOnSuccess(res -> {
                            long timeTaken = System.currentTimeMillis() - startTime;
                            logger.info("Exiting method: {} with result: {} in {} ms", joinPoint.getSignature().toShortString(), res, timeTaken);
                        })
                        .doOnError(e -> {
                            long timeTaken = System.currentTimeMillis() - startTime;
                            logger.error("Method: {} threw exception: {} in {} ms", joinPoint.getSignature().toShortString(), e.getMessage(), timeTaken);
                        });
            } else {
                long timeTaken = System.currentTimeMillis() - startTime;
                logger.info("Exiting method: {} with result: {} in {} ms", joinPoint.getSignature().toShortString(), result, timeTaken);
                return result;
            }
        } catch (Throwable e) {
            long timeTaken = System.currentTimeMillis() - startTime;
            logger.error("Method: {} threw exception: {} in {} ms", joinPoint.getSignature().toShortString(), e.getMessage(), timeTaken);
            throw e;
        }
    }
}
