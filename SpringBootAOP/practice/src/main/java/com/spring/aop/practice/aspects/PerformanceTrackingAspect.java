package com.spring.aop.practice.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class PerformanceTrackingAspect {
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Around("com.spring.aop.practice.aspects.CommonPointcutConfig.aopAnnotation()")
	public Object findExecutionTime( ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		//Start a timer
		long startTimeMillis= System.currentTimeMillis();
		//Execute the method
		Object object=proceedingJoinPoint.proceed();
		logger.info("Around Aspect - Method is called -{}",object);
		//Stop the timer
		long stopTimeMillis=System.currentTimeMillis();
		
		logger.info("the execution time is "+(stopTimeMillis-startTimeMillis));
		
		return object;
	}

}