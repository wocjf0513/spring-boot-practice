package com.spring.aop.practice.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

//Configuration
//AOP

@Configuration
@Aspect
public class LoggingAspect {
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	//Pointcut - When?
	//execution(* package.*.*(..))
	//@Before("execution(* com.spring.aop.practice.business.*.*(..))")
	public void logMethodCall(JoinPoint joinPoint) {
		//Logic - What?
		logger.info("Before Aspect - Method is called -{}",joinPoint);
		
	}
}
