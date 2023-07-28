package com.spring.aop.practice.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcutConfig {
	
	@Pointcut("execution(* com.spring.aop.practice.business.*.*(..))")
	public void businessPackageConfig() {}
	
	@Pointcut("bean (*Service*)")
	public void allPackageService() {}
	
	@Pointcut("@annotation(com.spring.aop.practice.annotaions.AOPService)")
	public void aopAnnotation() {}

}
