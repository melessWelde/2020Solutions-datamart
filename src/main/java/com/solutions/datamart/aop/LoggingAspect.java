package com.solutions.datamart.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

    private final Environment env;

    public LoggingAspect(Environment env) {
        this.env = env;
    }

    @Pointcut("within(com.solutions.datamart..*)")
    public void applicationPackagePointcut() {
        log.info("Pointcut class all controllers");
    }


    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }


    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles(Profiles.of("DEV"))) {
            logger(joinPoint)
                    .error(
                            "Exception in {}() with cause = '{}' and exception = '{}'",
                            joinPoint.getSignature().getName(),
                            e.getCause() != null ? e.getCause() : "NULL",
                            e.getMessage(),
                            e
                    );
        } else {
            logger(joinPoint)
                    .error(
                            "Exception in {}() with cause = {}",
                            joinPoint.getSignature().getName(),
                            e.getCause() != null ? e.getCause() : "NULL"
                    );
        }
    }

    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = logger(joinPoint);
        long startTime = System.currentTimeMillis();

        log.info("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
        try {
            Object result = joinPoint.proceed();
            log.info("Exit: {}() with  ResponseTime {}", joinPoint.getSignature().getName(), System.currentTimeMillis() - startTime);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName());
            throw e;
        }
    }
}
