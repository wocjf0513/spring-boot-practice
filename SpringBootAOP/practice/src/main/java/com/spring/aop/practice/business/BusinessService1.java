package com.spring.aop.practice.business;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.aop.practice.annotaions.AOPService;
import com.spring.aop.practice.repository.DataService1;

@Service
public class BusinessService1 {

	private DataService1 dataService1;
	Logger logger=LoggerFactory.getLogger(getClass());
	
	public BusinessService1(DataService1 dataService1) {
		this.dataService1=dataService1;
	}
	
	@AOPService
	public int calculateMax() {
		int[] data=dataService1.retrieveData();
		return Arrays.stream(data).max().getAsInt();
	}
}
