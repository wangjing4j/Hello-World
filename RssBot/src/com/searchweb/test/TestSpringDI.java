package com.searchweb.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TestSpringDI {
	@Resource
	DI di;
	
	@Test
	public void testDi() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestSpringDI t = context.getBean(TestSpringDI.class);
		t.say();
		
	}
	
	public void say() {
		di.add();
	}

	
}
