package com.spring.aop.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.aop.practice.business.BusinessService1;

@SpringBootApplication
public class PracticeApplication implements CommandLineRunner{

	@Autowired
	private BusinessService1 businessService1;
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(PracticeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		
		int result=businessService1.calculateMax();
		logger.info("value is "+result);
	}

}
