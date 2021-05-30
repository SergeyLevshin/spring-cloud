package com.levshin.OrderService.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LoggingAspect {

    @After("execution(* com.levshin.OrderService.OrderController.*(..))")
    public void log(JoinPoint jp) {
        log.info(jp.getSignature().getName() + " was called");
    }
}
