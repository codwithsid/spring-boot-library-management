package com.librarymanagement.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(LoggingAspect.class);

    // Log method entry
    @Before("execution(* com.librarymanagement.demo.controller..*.*(..)) || execution(* com.librarymanagement.demo.service.impl..*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Entering method: {}", methodName);
    }

    // Log method exit
    @AfterReturning(pointcut = "execution(* com.librarymanagement.demo.controller..*.*(..)) || execution(* com.librarymanagement.demo.service.impl..*.*(..))",
            returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Exiting method: {} with result: {}", methodName, result);
    }

    // Log exceptions
    @AfterThrowing(pointcut = "execution(* com.librarymanagement.demo.controller..*.*(..)) || execution(* com.librarymanagement.demo.service.impl..*.*(..))",
            throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.error("Exception in method: {} with message: {}", methodName, exception.getMessage(), exception);
    }
}
